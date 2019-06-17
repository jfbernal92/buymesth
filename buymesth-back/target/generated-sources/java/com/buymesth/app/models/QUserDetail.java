package com.buymesth.app.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserDetail is a Querydsl query type for UserDetail
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserDetail extends EntityPathBase<UserDetail> {

    private static final long serialVersionUID = 1144578107L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserDetail userDetail = new QUserDetail("userDetail");

    public final DateTimePath<java.util.Date> activateDate = createDateTime("activateDate", java.util.Date.class);

    public final NumberPath<Double> bank = createNumber("bank", Double.class);

    public final StringPath country = createString("country");

    public final StringPath door = createString("door");

    public final StringPath firstSurname = createString("firstSurname");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> lastLogin = createDateTime("lastLogin", java.util.Date.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> number = createNumber("number", Integer.class);

    public final NumberPath<Integer> postalCode = createNumber("postalCode", Integer.class);

    public final StringPath province = createString("province");

    public final StringPath region = createString("region");

    public final StringPath secondSurname = createString("secondSurname");

    public final StringPath state = createString("state");

    public final StringPath street = createString("street");

    public final QUser user;

    public QUserDetail(String variable) {
        this(UserDetail.class, forVariable(variable), INITS);
    }

    public QUserDetail(Path<? extends UserDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserDetail(PathMetadata metadata, PathInits inits) {
        this(UserDetail.class, metadata, inits);
    }

    public QUserDetail(Class<? extends UserDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

