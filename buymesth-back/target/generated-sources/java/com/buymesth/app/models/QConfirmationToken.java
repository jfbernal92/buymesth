package com.buymesth.app.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConfirmationToken is a Querydsl query type for ConfirmationToken
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConfirmationToken extends EntityPathBase<ConfirmationToken> {

    private static final long serialVersionUID = 124186021L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConfirmationToken confirmationToken = new QConfirmationToken("confirmationToken");

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath token = createString("token");

    public final QUser user;

    public QConfirmationToken(String variable) {
        this(ConfirmationToken.class, forVariable(variable), INITS);
    }

    public QConfirmationToken(Path<? extends ConfirmationToken> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConfirmationToken(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConfirmationToken(PathMetadata metadata, PathInits inits) {
        this(ConfirmationToken.class, metadata, inits);
    }

    public QConfirmationToken(Class<? extends ConfirmationToken> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

