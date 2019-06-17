package com.buymesth.app.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class OperationStatusForm {

    @NotNull
    @Min(0)
    private Long idOperation;

    @NotNull
    @Min(0)
    private Long idUser;

    @NotNull
    @NotBlank
    private String status;
}
