package com.buymesth.app.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class PasswordForm {

    @Size(min = 6)
    String password;

    @Size(min = 6)
    String password2;

    public boolean checkMatching() {
        return this.password.equals(this.password2);
    }
}
