package com.buymesth.app.anotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( {PARAMETER, METHOD})
@Retention( RUNTIME)
@Constraint( validatedBy = ValidateTokenIDImpl.class)
public @interface ValidateTokenID {

    String message() default "ID check validation failed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
