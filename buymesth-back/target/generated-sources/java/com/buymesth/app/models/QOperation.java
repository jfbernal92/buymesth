package com.buymesth.app.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOperation is a Querydsl query type for Operation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOperation extends EntityPathBase<Operation> {

    private static final long serialVersionUID = -692740792L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOperation operation = new QOperation("operation");

    public final NumberPath<Double> amount = createNumber("amount", Double.class);

    public final DateTimePath<java.util.Date> date = createDateTime("date", java.util.Date.class);

    public final NumberPath<Long> idOperation = createNumber("idOperation", Long.class);

    public final QProduct product;

    public final QOperationStatus status;

    public final QOperationType type;

    public final QUser user;

    public final BooleanPath visible = createBoolean("visible");

    public QOperation(String variable) {
        this(Operation.class, forVariable(variable), INITS);
    }

    public QOperation(Path<? extends Operation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOperation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOperation(PathMetadata metadata, PathInits inits) {
        this(Operation.class, metadata, inits);
    }

    public QOperation(Class<? extends Operation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product")) : null;
        this.status = inits.isInitialized("status") ? new QOperationStatus(forProperty("status")) : null;
        this.type = inits.isInitialized("type") ? new QOperationType(forProperty("type")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

