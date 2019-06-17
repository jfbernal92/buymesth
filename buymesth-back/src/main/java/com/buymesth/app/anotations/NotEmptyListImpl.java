package com.buymesth.app.anotations;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;


public class NotEmptyListImpl implements ConstraintValidator<NotEmptyList, Collection> {

    @Override
    public void initialize(NotEmptyList constraintAnnotation) {

    }

    @Override
    public boolean isValid(Collection collection, ConstraintValidatorContext context) {
        return collection != null && !collection.isEmpty();
    }

}
