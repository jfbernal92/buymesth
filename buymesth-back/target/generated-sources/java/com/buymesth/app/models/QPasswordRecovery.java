package com.buymesth.app.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPasswordRecovery is a Querydsl query type for PasswordRecovery
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPasswordRecovery extends EntityPathBase<PasswordRecovery> {

    private static final long serialVersionUID = -2084070321L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPasswordRecovery passwordRecovery = new QPasswordRecovery("passwordRecovery");

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath token = createString("token");

    public final QUser user;

    public QPasswordRecovery(String variable) {
        this(PasswordRecovery.class, forVariable(variable), INITS);
    }

    public QPasswordRecovery(Path<? extends PasswordRecovery> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPasswordRecovery(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPasswordRecovery(PathMetadata metadata, PathInits inits) {
        this(PasswordRecovery.class, metadata, inits);
    }

    public QPasswordRecovery(Class<? extends PasswordRecovery> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

