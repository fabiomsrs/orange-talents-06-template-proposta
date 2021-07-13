package br.com.zupacademy.fabiano.proposta.validation;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsBase64Validator implements ConstraintValidator<IsBase64, String> {
    @Override
    public void initialize(IsBase64 constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        return Base64.isBase64(value.toString());
    }
}
