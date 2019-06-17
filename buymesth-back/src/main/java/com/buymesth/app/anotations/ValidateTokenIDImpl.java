package com.buymesth.app.anotations;

import com.buymesth.app.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.buymesth.app.utils.enums.RoleName.ADMIN;

public class ValidateTokenIDImpl implements ConstraintValidator<ValidateTokenID, Long> {

    private Long id;

    @Autowired
    private JwtProvider jwt;

    @Override
    public void initialize(ValidateTokenID constraintAnnotation) {
        this.id = jwt.getIdFromSecurityContext();
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
		
        System.out.println("id context: " + jwt.getIdFromSecurityContext());
		System.out.println("id param: " + id);
		System.out.println("roles: " + jwt.getAuthoritiesFromSecurityContext());
		System.out.println("this id: " + this.id);
		System.out.println(this.id.equals(id));
		System.out.println(this.id.equals(id)|| jwt.getAuthoritiesFromSecurityContext().contains(ADMIN));
		
		if(this.id.equals(id)){
			return true;
		}
		
		if(jwt.getAuthoritiesFromSecurityContext().contains(ADMIN)){
			return true;
		}
		
		return false;
		
        //return this.id == id || jwt.getAuthoritiesFromSecurityContext().contains(ADMIN);
    }
}
