package com.buymesth.app.controllers;

import com.buymesth.app.dtos.EmailAndPwdForm;
import com.buymesth.app.dtos.PasswordForm;
import com.buymesth.app.models.User;
import com.buymesth.app.security.jwt.JwtProvider;
import com.buymesth.app.security.jwt.message.JwtResponse;
import com.buymesth.app.services.MainService;
import com.buymesth.app.utils.exceptions.AssertUtil;
import com.buymesth.app.utils.exceptions.CustomException;
import com.buymesth.app.utils.routes.RestRoutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping(RestRoutes.Main.URL)
public class MainController {

    private AuthenticationManager authenticationManager;
    private MainService mainService;
    private JwtProvider jwtProvider;

    @Autowired
    public MainController(@Lazy AuthenticationManager authenticationManager, @Lazy MainService mainService, @Lazy JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.mainService = mainService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping(RestRoutes.Main.LOGIN)
    public ResponseEntity login(@Valid @RequestBody EmailAndPwdForm userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getEmail(),
                        userDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, user));
    }

    @PostMapping(value = RestRoutes.Main.SIGNUP)
    public ResponseEntity signup(@Valid @RequestBody EmailAndPwdForm emailAndPwdForm) {
        try {
            return mainService.singup(emailAndPwdForm).map(ResponseEntity::ok).orElse(ResponseEntity.status(409).build());
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }

    }

    @GetMapping(value = RestRoutes.Main.EMAIL_CONFIRMATION)
    public ResponseEntity emailConfirmation(@PathVariable(value = "token") String token, @PathVariable(value = "d") Long d, @PathVariable(value = "id") Long id) {
        try {
            mainService.emailConfirmation(token, d, id);
            return ResponseEntity.ok().build();
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }

    }

    @PostMapping(value = RestRoutes.Main.SEND_ANOTHER_LINK)
    public ResponseEntity sendAnotherLink(@PathVariable(value = "token") String token, @Valid @RequestBody String email) throws CustomException {
        return mainService.sendAnotherLink(token, email).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());

    }

    @PutMapping(value = RestRoutes.Main.PASSWORD_FORGOTTEN_POST_RECOVER)
    public ResponseEntity<String> passwordRecovery(@PathVariable(value = "token") String token, @PathVariable(value = "d") Long createDate, @PathVariable(value = "id") Long id, @Valid @RequestBody PasswordForm passwordForm) throws CustomException {
        AssertUtil.isTrue(passwordForm.checkMatching(), () -> new CustomException("Passwords must match", HttpStatus.BAD_REQUEST));
        return mainService.passwordRecovery(token, createDate, id, passwordForm.getPassword()) ? ResponseEntity.ok().build() : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = RestRoutes.Main.PASSWORD_FORGOTTEN_POST)
    public ResponseEntity passwordForgotten(@RequestBody String email) throws CustomException {
        return mainService.passwordForgotten(email) ? ResponseEntity.ok().build() : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
