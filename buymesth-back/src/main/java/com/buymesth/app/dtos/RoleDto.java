package com.buymesth.app.dtos;

import com.buymesth.app.utils.enums.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class RoleDto {

    @JsonIgnore
    private Long idRole;

    private RoleName name;
}
