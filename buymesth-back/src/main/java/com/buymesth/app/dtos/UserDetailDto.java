package com.buymesth.app.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UserDetailDto {

    private String name;
    private String firstSurname;
    private String secondSurname;
    private String country;
    private Date activateDate;
    private String state;
    private String region;
    private String province;
    private Integer postalCode;
    private String street;
    private int number;
    private String door;
    private Date lastLogin;
    private Double bank;
}
