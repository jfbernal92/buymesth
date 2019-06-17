package com.buymesth.app.controllers;

import com.buymesth.app.anotations.ValidateTokenID;
import com.buymesth.app.dtos.*;
import com.buymesth.app.services.UserService;
import com.buymesth.app.utils.PageDto;
import com.buymesth.app.utils.exceptions.AssertUtil;
import com.buymesth.app.utils.exceptions.CustomException;
import com.buymesth.app.utils.routes.RestRoutes;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.buymesth.app.utils.routes.RestRoutes.User.*;

@Validated
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping(RestRoutes.User.URL)
public class UserController {


    private UserService userService;
    private ResourceGenerator resGen;

    @Autowired
    public UserController(@Lazy UserService userService, @Lazy ResourceGenerator resGen) {
        this.userService = userService;
        this.resGen = resGen;
    }


    @GetMapping(USER_PROFILE)
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    public ResponseEntity<UserDto> getUserProfile(@ValidateTokenID @PathVariable(name = "id") Long id) {
        return userService.getUserProfile(id).map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(USER_PROFILE)
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    public ResponseEntity<UserDetailDto> editUserProfile(@ValidateTokenID @PathVariable(name = "id") Long id, @Valid @RequestBody EditUserForm editUserForm) {
        return userService.editUserProfile(id, editUserForm).map(u -> new ResponseEntity<>(u, HttpStatus.CREATED))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(USER_PROFILE)
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    public ResponseEntity<UserDetailDto> completeYourProfile(@ValidateTokenID @PathVariable(name = "id") Long id, @Valid @RequestBody EditUserForm editUserForm) throws CustomException {
        return userService.completeYourProfile(id, editUserForm).map(u -> new ResponseEntity<>(u, HttpStatus.CREATED))
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping(USER_CHANGE_YOUR_PASSWORD)
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    public ResponseEntity<UserDto> changeYourPassword(@ValidateTokenID @PathVariable(name = "id") Long id, @Valid @RequestBody PasswordForm passwordForm) throws CustomException {
        AssertUtil.isTrue(passwordForm.checkMatching(), () -> new CustomException("Passwords must match", HttpStatus.BAD_REQUEST));
        return userService.changeYourPassword(id, passwordForm.getPassword()).map(ResponseEntity::ok)
                .orElse(ResponseEntity.unprocessableEntity().build());
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property,[asc|desc]. " +
                            "Default sort order is ascending.")
    })
    public Resource<PageDto<Resource<UserDto>>> getUsers(Pageable page) {
        Page<UserDto> users = userService.getUsers(page);
        List<Resource<UserDto>> list = new ArrayList<>();
        users.forEach(u -> list.add(resGen.getUserResource(u)));
        Resource<PageDto<Resource<UserDto>>> res = new Resource<>(PageDto.of(list, users.getTotalElements(), users.getNumberOfElements()));
        Link link = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getUsers(page)).withRel("all").expand();
        res.add(link);
        return res;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public ResponseEntity deleteUser(@PathVariable Long id) throws CustomException {
        return userService.deleteUser(id) ? ResponseEntity.noContent().build() : ResponseEntity.unprocessableEntity().build();
    }

    @GetMapping(USER_ROLES)
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    public ResponseEntity<List<String>> getUserRoles(@ValidateTokenID @PathVariable(name = "id") Long id) {
        List<String> roles = userService.getUserRoles(id);
        return roles.isEmpty() ? ResponseEntity.notFound().build() : new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping(USER_ALL)
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public ResponseEntity<UserCount> getTotalUsers() {
        return ResponseEntity.ok(userService.getTotalUsers());
    }

    @GetMapping(USER_BY_SINGUP_DATE)
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public ResponseEntity<Map<String, Integer>> countUserBySignupDate() {
        return ResponseEntity.ok(userService.countUserBySignupDate());
    }

    @GetMapping(USER_BY_COUNTRY)
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public ResponseEntity<Map<String, Integer>> countUserByCountry() {
        return ResponseEntity.ok(userService.countUserByCountry());
    }


}
