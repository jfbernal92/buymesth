package com.buymesth.app.dtos;

import com.buymesth.app.utils.enums.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class ProductDto {

    @ApiModelProperty(hidden = true)
    private Long idProduct;
    @NotBlank
    @Size(max = 80)
    private String description;
    @DecimalMin("0.1")
    @Digits(integer = 3, fraction = 2)
    @DecimalMax("999.99")
    private double price;
    @Min(0)
    private Integer units;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;
}
