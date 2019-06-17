package com.buymesth.app.utils.converters;

import com.buymesth.app.dtos.ProductDto;
import com.buymesth.app.models.Product;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-06-16T17:33:09+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_191 (Oracle Corporation)"
)
@Component
public class ProductConverterImpl implements ProductConverter {

    @Override
    public Product fromDtoToEntity(ProductDto dto) {
        if ( dto == null ) {
            return null;
        }

        Product product = new Product();

        product.setIdProduct( dto.getIdProduct() );
        product.setDescription( dto.getDescription() );
        product.setPrice( dto.getPrice() );
        product.setUnits( dto.getUnits() );
        product.setCategory( dto.getCategory() );

        return product;
    }

    @Override
    public List<Product> fromDtosToEntities(Collection<ProductDto> dtos) {
        if ( dtos == null ) {
            return new ArrayList<Product>();
        }

        List<Product> list = new ArrayList<Product>( dtos.size() );
        for ( ProductDto productDto : dtos ) {
            list.add( fromDtoToEntity( productDto ) );
        }

        return list;
    }

    @Override
    public ProductDto fromEntityToDto(Product entity) {
        if ( entity == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setIdProduct( entity.getIdProduct() );
        productDto.setDescription( entity.getDescription() );
        productDto.setPrice( entity.getPrice() );
        productDto.setUnits( entity.getUnits() );
        productDto.setCategory( entity.getCategory() );

        return productDto;
    }

    @Override
    public List<ProductDto> fromEntitiesToDtos(Collection<Product> entities) {
        if ( entities == null ) {
            return new ArrayList<ProductDto>();
        }

        List<ProductDto> list = new ArrayList<ProductDto>( entities.size() );
        for ( Product product : entities ) {
            list.add( fromEntityToDto( product ) );
        }

        return list;
    }
}
