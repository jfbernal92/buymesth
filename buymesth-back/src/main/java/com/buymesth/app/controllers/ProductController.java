package com.buymesth.app.controllers;

import com.buymesth.app.dtos.ProductDto;
import com.buymesth.app.dtos.ProductFilter;
import com.buymesth.app.services.ProductService;
import com.buymesth.app.utils.PageDto;
import com.buymesth.app.utils.routes.RestRoutes;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.buymesth.app.utils.routes.RestRoutes.Product.*;

@Validated
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping(RestRoutes.Product.URL)
public class ProductController {

    private ResourceGenerator resGen;
    private ProductService productService;

    @Autowired
    public ProductController(@Lazy ResourceGenerator resGen, @Lazy ProductService productService) {
        this.resGen = resGen;
        this.productService = productService;
    }


    @GetMapping
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property,[asc|desc]. " +
                            "Default sort order is ascending.")
    })
    public Resource<PageDto<Resource<ProductDto>>> getProducts(Pageable pageable) {
        Page<ProductDto> products = productService.getProducts(pageable);
        List<Resource<ProductDto>> list = products.stream().map(resGen::getProductResource).collect(Collectors.toList());
        Resource<PageDto<Resource<ProductDto>>> resource = new Resource<>(PageDto.of(list, products.getTotalElements(), products.getNumberOfElements()));
        Link link = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getProducts(pageable)).withRel("all").expand();
        resource.add(link);
        return resource;
    }

    @PreAuthorize(value = "hasAnyAuthority('USER')")
    @GetMapping(PRODUCT)
    public ResponseEntity<ProductDto> getProduct(@PathVariable(name = "id") Long id) {
        return productService.getProduct(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @PostMapping
    @ApiOperation(value = "", notes = "You can create some products at the same time. If you use an existing ID then it will be updated.")
    public ResponseEntity<Resource<PageDto<Resource<ProductDto>>>> createProduct(@Valid @RequestBody List<ProductDto> productDtos) {
        return productService.createProduct(productDtos).map(l -> resGen.getProductsResource(l, (long) l.size(), l.size())).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @PutMapping(PRODUCT)
    public ResponseEntity<ProductDto> ediProduct(@PathVariable(name = "id") Long id, @Valid @RequestBody ProductDto productDto) {
        productDto.setIdProduct(id);
        return productService.editProduct(productDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @DeleteMapping(PRODUCT)
    public ResponseEntity deleteProduct(@PathVariable(name = "id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize(value = "hasAnyAuthority('USER')")
    @GetMapping(SEARCH)
    public List<Resource<ProductDto>> searchProducts(@Valid ProductFilter filter) {
        return productService.searchProducts(filter).stream().map(resGen::getProductResource).collect(Collectors.toList());

    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @GetMapping(PRODUCT_BY_CATEGORY)
    public ResponseEntity<Map<String, Integer>> countProductByCategory() {
        return ResponseEntity.ok(productService.countProductsByCategory());

    }

}
