package com.buymesth.app.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOperationStatus is a Querydsl query type for OperationStatus
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOperationStatus extends EntityPathBase<OperationStatus> {

    private static final long serialVersionUID = -499170470L;

    public static final QOperationStatus operationStatus = new QOperationStatus("operationStatus");

    public final NumberPath<Long> idOpStatus = createNumber("idOpStatus", Long.class);

    public final EnumPath<com.buymesth.app.utils.enums.OperationStatusName> name = createEnum("name", com.buymesth.app.utils.enums.OperationStatusName.class);

    public final ListPath<Operation, QOperation> operations = this.<Operation, QOperation>createList("operations", Operation.class, QOperation.class, PathInits.DIRECT2);

    public QOperationStatus(String variable) {
        super(OperationStatus.class, forVariable(variable));
    }

    public QOperationStatus(Path<? extends OperationStatus> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOperationStatus(PathMetadata metadata) {
        super(OperationStatus.class, metadata);
    }

}

