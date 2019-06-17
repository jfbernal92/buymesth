package com.buymesth.app.services;

import com.buymesth.app.dtos.*;
import com.buymesth.app.models.Role;
import com.buymesth.app.models.User;
import com.buymesth.app.models.UserDetail;
import com.buymesth.app.projections.OperationProjection;
import com.buymesth.app.repositories.OperationRepository;
import com.buymesth.app.repositories.RoleRepository;
import com.buymesth.app.repositories.UserDetailRepository;
import com.buymesth.app.repositories.UserRepository;
import com.buymesth.app.security.jwt.JwtProvider;
import com.buymesth.app.utils.converters.UserConverter;
import com.buymesth.app.utils.converters.UserDetailConverter;
import com.buymesth.app.utils.exceptions.AssertUtil;
import com.buymesth.app.utils.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserDetailRepository userDetailRepository;
    private UserConverter userConverter;
    private UserDetailConverter userDetailConverter;
    private OperationRepository operationRepository;
    private PasswordEncoder encoder;
    private JwtProvider jwt;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(@Lazy UserRepository userRepository, @Lazy UserDetailRepository userDetailRepository, @Lazy UserConverter userConverter,
                       @Lazy UserDetailConverter userDetailConverter, @Lazy PasswordEncoder encoder, @Lazy OperationRepository operationRepository,
                       @Lazy JwtProvider jwt, @Lazy RoleRepository roleRepository) {

        this.userRepository = userRepository;
        this.userDetailRepository = userDetailRepository;
        this.userDetailConverter = userDetailConverter;
        this.userConverter = userConverter;
        this.encoder = encoder;
        this.operationRepository = operationRepository;
        this.jwt = jwt;
        this.roleRepository = roleRepository;
    }


    @Transactional(rollbackOn = CustomException.class)
    public Optional<UserDetailDto> completeYourProfile(Long id, EditUserForm editUserForm) throws CustomException {

        Optional<User> optUser = userRepository.findById(id);

        if (!optUser.isPresent())
            return Optional.empty();

        AssertUtil.checkNull(optUser.get().getUserDetail(), () -> new CustomException("This profile has been completed yet", HttpStatus.CONFLICT));

        UserDetail userDetail = fromEditUserFormToUserDetail(editUserForm);

        User user = optUser.get();
        user.setEnabled(true);
        userDetail.setBank(0.0);
        userDetail.setUser(user);
        userDetail.setActivateDate(new Date());
        return Optional.of(userDetailRepository.save(userDetail)).map(userDetailConverter::fromEntityToDto);

    }

    public Optional<UserDto> getUserProfile(Long id) {
        return userRepository.findById(id).map(userConverter::fromEntityToDto);
    }

    @Transactional(rollbackOn = Exception.class)
    public Optional<UserDto> changeYourPassword(Long id, String password) {
        return userRepository.findById(id).map(user -> {
            user.setPassword(encoder.encode(password));
            return userRepository.save(user);
        }).map(userConverter::fromEntityToDto);
    }

    //TODO: comprobar que no modifica bank, activate and login date
    @Transactional(rollbackOn = CustomException.class)
    public Optional<UserDetailDto> editUserProfile(Long id, EditUserForm editUserForm) {

        if (!userRepository.existsById(id))
            return Optional.empty();

        UserDetail userDetail = fromEditUserFormToUserDetail(editUserForm);
        return userRepository.findById(id).map(u -> {
            userDetail.setId(u.getId());
            userDetail.setBank(u.getUserDetail().getBank());
            userDetail.setActivateDate(u.getUserDetail().getActivateDate());
            userDetail.setLastLogin(u.getUserDetail().getLastLogin());
            u.setUserDetail(userDetail);
            User user = userRepository.save(u);
            userDetail.setUser(user);
            return userDetailRepository.save(userDetail);
        }).flatMap(userDetailConverter::fromEntityToOptionalDto);
    }

    public Page<UserDto> getUsers(Pageable page) {
        Page<User> users = userRepository.findAll(page);
        return new PageImpl<>(users.getContent().stream().map(userConverter::fromEntityToDto).collect(Collectors.toList()), page, users.getTotalElements());
    }


    public boolean deleteUser(Long id) throws CustomException {
        AssertUtil.isTrue(!id.equals(jwt.getIdFromSecurityContext()), () -> new CustomException("You cannot delete yourself.", HttpStatus.FORBIDDEN));
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }

    public List<String> getUserRoles(Long id) {
        return roleRepository.findByUserId(id).stream().map(Role::getNameString).collect(Collectors.toList());
    }

    public UserCount getTotalUsers() {
        return UserCount.builder().enabled(userRepository.countByEnabled(true)).disabled(userRepository.countByEnabled(false)).locked(userRepository.countByLocked(true)).build();
    }

    public Map<String, Integer> countUserBySignupDate() {
        List<Object[]> list = userRepository.countUserRegisteredInCurrentYear();
        Map<String, Integer> map = new HashMap<>();
        for (Object[] o : list) {
            map.put(o[0].toString().trim(), Integer.parseInt(o[1].toString()));
        }
        return map;
    }

    public Map<String, Integer> countUserByCountry() {
        List<Object[]> list = userRepository.countUserByCountry();
        Map<String, Integer> map = new HashMap<>();
        for (Object[] o : list) {
            map.put(o[0].toString().trim(), Integer.parseInt(o[1].toString()));
        }
        return map;
    }


    private OperationDto fromOperationProjectionToOperationDto(OperationProjection projection) {
        return OperationDto.builder().idOperation(projection.getIdOperation()).amount(projection.getAmount())
                .date(projection.getDate()).status(projection.getStatus())
                .type(projection.getType()).idUser(projection.getIdUser()).build();
    }

    private UserDetail fromEditUserFormToUserDetail(EditUserForm editUserForm) {
        return UserDetail.builder()
                .name(editUserForm.getName())
                .firstSurname(editUserForm.getFirstSurname())
                .secondSurname(editUserForm.getSecondSurname())
                .country(editUserForm.getCountry())
                .state(editUserForm.getState())
                .region(editUserForm.getRegion())
                .province(editUserForm.getProvince())
                .postalCode(editUserForm.getPostalCode())
                .street(editUserForm.getStreet())
                .number(editUserForm.getNumber())
                .door(editUserForm.getDoor())
                .build();
    }

}
