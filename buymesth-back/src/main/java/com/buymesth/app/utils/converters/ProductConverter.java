package com.buymesth.app.utils.converters;

import com.buymesth.app.dtos.ProductDto;
import com.buymesth.app.models.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductConverter extends BasicConverter<Product, ProductDto> {
}
