package br.com.zupacademy.fabiano.proposta.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValorUnicoValidator implements ConstraintValidator<ValorUnico, Object> {
    private EntityManager em;

    private Class<?> object;
    private String field;

    public ValorUnicoValidator(EntityManager em) {
        this.em = em;
    }

    @Override
    public void initialize(ValorUnico constraintAnnotation) {
        this.object = constraintAnnotation.instanceClass();
        this.field = constraintAnnotation.field();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean result = this.em.createQuery("select 1 from "+this.object.getName()+" where "+ field+"=:field")
                .setParameter("field",value)
                .getResultList()
                .isEmpty();

        if(!result){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "entidade ja cadastrada");
        }

        return true;
    }
}
