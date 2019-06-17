package com.buymesth.app.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class BuyForm {

    @NotNull
    private Long userId;
    @NotNull
    private Long productId;
    @NotNull
    private boolean visible;

}
