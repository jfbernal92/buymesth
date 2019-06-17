package com.buymesth.app.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -615835376L;

    public static final QProduct product = new QProduct("product");

    public final EnumPath<com.buymesth.app.utils.enums.Category> category = createEnum("category", com.buymesth.app.utils.enums.Category.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> idProduct = createNumber("idProduct", Long.class);

    public final NumberPath<Double> price = createNumber("price", Double.class);

    public final NumberPath<Integer> units = createNumber("units", Integer.class);

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

