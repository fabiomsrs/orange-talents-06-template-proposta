package br.com.zupacademy.fabiano.proposta.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsBase64Validator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsBase64 {
    String message() default "Não é base 64";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
