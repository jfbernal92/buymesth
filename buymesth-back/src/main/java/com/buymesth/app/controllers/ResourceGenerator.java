package com.buymesth.app.controllers;

import com.buymesth.app.dtos.OperationDto;
import com.buymesth.app.dtos.ProductDto;
import com.buymesth.app.dtos.UserDto;
import com.buymesth.app.utils.PageDto;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResourceGenerator {

    public Resource<OperationDto> getOperationResource(OperationDto operationDto) {
        Resource<OperationDto> resource = new Resource<>(operationDto);
        resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(OperationController.class)
                .getOperation(operationDto.getIdUser(), operationDto.getIdOperation())).withRel("self"));
        return resource;
    }

    public Resource<UserDto> getUserResource(UserDto userDto) {
        Resource<UserDto> resource = new Resource<>(userDto);
        resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UserController.class)
                .getUserProfile(userDto.getId())).withRel("self"));
        return resource;
    }

    public Resource<ProductDto> getProductResource(ProductDto productDto) {
        Resource<ProductDto> resource = new Resource<>(productDto);
        resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ProductController.class)
                .getProduct(productDto.getIdProduct())).withRel("self"));
        return resource;
    }

    public Resource<PageDto<Resource<ProductDto>>> getProductsResource(Collection<ProductDto> products, Long totalElements, Integer numberOfElements) {
        List<Resource<ProductDto>> list = products.stream().map(this::getProductResource).collect(Collectors.toList());
        return new Resource<>(PageDto.of(list, totalElements, numberOfElements));
    }
}
