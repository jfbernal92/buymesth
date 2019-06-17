package com.buymesth.app.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class EmailAndPwdForm {

    @ApiModelProperty(value = "Email")
    @Email
    @NotBlank
    @NotNull
    private String email;

    @ApiModelProperty(value = "Password")
    @Size(min = 6, max = 15)
    @NotNull
    @NotBlank
    private String password;
}
