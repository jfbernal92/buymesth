package com.buymesth.app.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class OperationDto {

    @NotNull
    private Long idUser;

    @NotNull
    private Long idOperation;

    @NotNull
    private Date date;

    @NotNull
    @Min(0)
    @Max(399)
    private double amount;

    @NotNull
    @NotBlank
    private String type;

    @NotNull
    @NotBlank
    private String status;

    @NotNull
    private ProductDto product;

    @NotNull
    private boolean visible;
}
