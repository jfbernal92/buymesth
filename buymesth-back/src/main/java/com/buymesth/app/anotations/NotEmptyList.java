package com.buymesth.app.anotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({PARAMETER, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = NotEmptyListImpl.class)
public @interface NotEmptyList {
    String message() default "List must have at least 1 item";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
