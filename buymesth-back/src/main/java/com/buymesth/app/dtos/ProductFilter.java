package com.buymesth.app.dtos;

import com.buymesth.app.anotations.NotEmptyList;
import com.buymesth.app.utils.enums.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class ProductFilter {

    @DecimalMin("0.02")
    @DecimalMax("999.99")
    @Digits(integer = 3, fraction = 2)
    @NotNull
    @ApiModelProperty(required = true)
    private double maxPrice;

    @DecimalMin("0.01")
    @DecimalMax("999.98")
    @Digits(integer = 3, fraction = 2)
    @NotNull
    @ApiModelProperty(required = true)
    private double minPrice;

    @NotEmptyList
    @ApiModelProperty(required = true)
    private List<Category> categories;

}
