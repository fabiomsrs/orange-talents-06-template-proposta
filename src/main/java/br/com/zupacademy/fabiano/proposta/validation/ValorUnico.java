package br.com.zupacademy.fabiano.proposta.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValorUnicoValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValorUnico {
    String message() default "Restricao valor unico";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String field();
    Class instanceClass();
}
