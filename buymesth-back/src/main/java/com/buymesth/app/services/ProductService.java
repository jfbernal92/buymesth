package com.buymesth.app.services;

import com.buymesth.app.dtos.ProductDto;
import com.buymesth.app.dtos.ProductFilter;
import com.buymesth.app.models.Product;
import com.buymesth.app.repositories.ProductRepository;
import com.buymesth.app.utils.converters.ProductConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private ProductConverter productConverter;

    @Autowired
    public ProductService(@Lazy ProductRepository productRepository, @Lazy ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }

    public Page<ProductDto> getProducts(Pageable page) {
        Page<Product> products = productRepository.findAll(page);
        return new PageImpl<>(products.getContent().stream().map(productConverter::fromEntityToDto).collect(Collectors.toList()), page, products.getTotalElements());

    }

    public Optional<ProductDto> getProduct(Long id) {
        return productRepository.findById(id).map(productConverter::fromEntityToDto);
    }

    @Transactional
    public Optional<Collection<ProductDto>> createProduct(List<ProductDto> productDto) {
        return Optional.of(productRepository.saveAll(productConverter.fromDtosToEntities(productDto)).stream().map(productConverter::fromEntityToDto).collect(Collectors.toList()));
    }

    @Transactional
    public Optional<ProductDto> editProduct(ProductDto productDto) {
        if (!productRepository.existsById(productDto.getIdProduct()))
            return Optional.empty();
        return Optional.of(productConverter.fromDtoToEntity(productDto)).map(productRepository::save).map(productConverter::fromEntityToDto);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductDto> searchProducts(ProductFilter filter) {
        return productConverter.fromEntitiesToDtos(productRepository.findAllByCategoryInAndPriceIsGreaterThanEqualAndPriceIsLessThanEqual(filter.getCategories(), filter.getMinPrice(), filter.getMaxPrice()));
    }

    public Map<String, Integer> countProductsByCategory() {
        List<Object[]> list = productRepository.countProductsByCategory();
        Map<String, Integer> map = new HashMap<>();
        for (Object[] o : list) {
            map.put(o[0].toString().trim(), Integer.parseInt(o[1].toString()));
        }
        return map;
    }

}
