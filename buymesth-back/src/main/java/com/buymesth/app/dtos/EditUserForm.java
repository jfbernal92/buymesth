package com.buymesth.app.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class EditUserForm {
    @NotNull
    private String name;
    @NotBlank
    private String firstSurname;
    private String secondSurname;
    private String country;
    private String state;
    private String region;
    private String province;
    private Integer postalCode;
    private String street;
    private int number;
    private String door;

    //TODO: CORREGIR LAS RESTRICCIONES
}
