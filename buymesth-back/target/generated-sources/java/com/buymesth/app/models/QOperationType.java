package com.buymesth.app.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOperationType is a Querydsl query type for OperationType
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOperationType extends EntityPathBase<OperationType> {

    private static final long serialVersionUID = 482196642L;

    public static final QOperationType operationType = new QOperationType("operationType");

    public final NumberPath<Long> idOpType = createNumber("idOpType", Long.class);

    public final EnumPath<com.buymesth.app.utils.enums.OperationTypeName> name = createEnum("name", com.buymesth.app.utils.enums.OperationTypeName.class);

    public final ListPath<Operation, QOperation> operations = this.<Operation, QOperation>createList("operations", Operation.class, QOperation.class, PathInits.DIRECT2);

    public QOperationType(String variable) {
        super(OperationType.class, forVariable(variable));
    }

    public QOperationType(Path<? extends OperationType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOperationType(PathMetadata metadata) {
        super(OperationType.class, metadata);
    }

}

