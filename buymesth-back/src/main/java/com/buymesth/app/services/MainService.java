package com.buymesth.app.services;

import com.buymesth.app.dtos.EmailAndPwdForm;
import com.buymesth.app.dtos.UserDto;
import com.buymesth.app.mail.EmailSender;
import com.buymesth.app.models.ConfirmationToken;
import com.buymesth.app.models.PasswordRecovery;
import com.buymesth.app.models.Role;
import com.buymesth.app.models.User;
import com.buymesth.app.repositories.ConfirmationTokenRepository;
import com.buymesth.app.repositories.PasswordRecoveryRepository;
import com.buymesth.app.repositories.RoleRepository;
import com.buymesth.app.repositories.UserRepository;
import com.buymesth.app.utils.converters.UserConverter;
import com.buymesth.app.utils.enums.RoleName;
import com.buymesth.app.utils.exceptions.AssertUtil;
import com.buymesth.app.utils.exceptions.CustomException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class MainService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ConfirmationTokenRepository confirmationTokenRepository;
    private UserConverter userConverter;
    private EmailSender emailSender;
    private PasswordEncoder encoder;
    private PasswordRecoveryRepository passwordRecoveryRepository;

    @Autowired
    public MainService(@Lazy UserRepository userRepository, @Lazy
            ConfirmationTokenRepository confirmationTokenRepository, @Lazy UserConverter userConverter, @Lazy EmailSender emailSender, @Lazy PasswordEncoder encoder,
                       @Lazy PasswordRecoveryRepository passwordRecoveryRepository, @Lazy RoleRepository roleRepository) {

        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.emailSender = emailSender;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.encoder = encoder;
        this.passwordRecoveryRepository = passwordRecoveryRepository;
        this.roleRepository = roleRepository;

    }


    @Transactional
    public Optional<UserDto> singup(EmailAndPwdForm userDto) throws CustomException {
        if (userRepository.existsByEmail(userDto.getEmail()))
            return Optional.empty();

        Optional<Role> role = roleRepository.findByName(RoleName.USER);
        AssertUtil.isTrue(role.isPresent(), () -> new CustomException("User role does not exist in database", HttpStatus.INTERNAL_SERVER_ERROR));

        User user = User.builder()
                .email(userDto.getEmail())
                .password(encoder.encode(userDto.getPassword()))
                .enabled(false)
                .locked(false)
                .roles(new HashSet<>(Collections.singletonList(role.get()))).build();

        user = userRepository.saveAndFlush(user);
        ConfirmationToken ct = new ConfirmationToken(user);
        ct = confirmationTokenRepository.saveAndFlush(ct);
        sendConfirmationEmail(user, ct, true);

        return Optional.of(user).flatMap(userConverter::fromEntityToOptionalDto);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with -> email : " + email));
    }


    @Transactional
    public Optional<UserDto> emailConfirmation(String token, Long createDate, Long id) throws CustomException {
        Optional<ConfirmationToken> ct = confirmationTokenRepository.findByTokenAndCreatedDateAndId(token, new Date(createDate), emailSender.decodeId(createDate, id));

        AssertUtil.isTrue(ct.isPresent(), () -> new CustomException("Bad data", HttpStatus.BAD_REQUEST));

        if (ct.get().verify()) {
            return userRepository.findById(emailSender.decodeId(createDate, id)).map(user -> {
                user.setEnabled(true);
                user = userRepository.save(user);
                confirmationTokenRepository.deleteById(user.getId());
                return user;
            }).map(userConverter::fromEntityToDto);
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<ConfirmationToken> sendAnotherLink(String token, String email) throws CustomException {

        Optional<User> optUser = userRepository.findByEmail(email);
        AssertUtil.isTrue(!optUser.isPresent(), () -> new CustomException("Email not found", HttpStatus.NOT_FOUND));

        User user = optUser.get();
        AssertUtil.isTrue(user.checkUser(), () -> new CustomException("Account already activated", HttpStatus.CONFLICT));

        if (!confirmationTokenRepository.existsByIdAndToken(token, user.getId()))
            return Optional.empty();

        confirmationTokenRepository.deleteById(user.getId());
        ConfirmationToken ct = new ConfirmationToken(user);
        ct = confirmationTokenRepository.saveAndFlush(ct);
        sendConfirmationEmail(user, ct, false);

        return Optional.of(ct);
    }

    @Transactional(rollbackOn = CustomException.class)
    public boolean passwordRecovery(String token, Long createDate, Long id, String newPassword) throws CustomException {

        Long ids = emailSender.decodeId(createDate, id);

        Optional<PasswordRecovery> passToken = passwordRecoveryRepository.findByTokenAndId(token, ids);
        AssertUtil.isTrue(passToken.isPresent(), () -> new CustomException("Invalid data", HttpStatus.BAD_REQUEST));
        AssertUtil.isTrue(passToken.get().verify(), () -> new CustomException("Your token has already expired, please request another one. ", HttpStatus.BAD_REQUEST));

        Optional<User> optUser = userRepository.findById(ids);
        AssertUtil.isTrue(optUser.isPresent(), () -> new CustomException("Error", HttpStatus.LOCKED));
        optUser.get().setPassword(encoder.encode(newPassword));

        userRepository.save(optUser.get());

        passwordRecoveryRepository.deleteById(ids);
        return true;
    }

    @Transactional(rollbackOn = CustomException.class)
    public boolean passwordForgotten(String email) throws CustomException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        AssertUtil.isTrue(optionalUser.isPresent(), () -> new CustomException("Incorrect Email", HttpStatus.NOT_FOUND));

        Optional<PasswordRecovery> optPassToken = passwordRecoveryRepository.findById(optionalUser.get().getId());

        if (optPassToken.isPresent()) {
            AssertUtil.isTrue(!optPassToken.get().verify(), () -> new CustomException("You already have an active link into your email to change your password, please use it or wait "
                    + TimeUnit.MILLISECONDS.toMinutes(optPassToken.get().remainingTime()) + " minutes", HttpStatus.LOCKED));

            passwordRecoveryRepository.deleteById(optionalUser.get().getId());
        }

        PasswordRecovery passToken = new PasswordRecovery(optionalUser.get());
        passToken = passwordRecoveryRepository.save(passToken);
        sendPasswordRecoveryEmail(passToken);
        return true;
    }

    private void sendConfirmationEmail(@NotNull User u, ConfirmationToken ct, boolean firstTime) throws CustomException {
        try {
            emailSender.sendConfirmationEmail(u.getId(), ct, u.getEmail(), firstTime);
        } catch (MessagingException e) {
            throw new CustomException("Email could not been sent", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private void sendPasswordRecoveryEmail(PasswordRecovery pwd) throws CustomException {
        try {
            emailSender.sendPasswordRecoveryLinkEmail(pwd);
        } catch (MessagingException e) {
            throw new CustomException("Email could not been sent", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
