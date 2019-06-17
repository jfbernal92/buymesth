package com.buymesth.app.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UserDto {
    private Long id;
    private String email;
    private Date signupDate;
    private Set<RoleDto> roles;
    private boolean enabled;
    private boolean locked;
    private UserDetailDto userDetail;
}
