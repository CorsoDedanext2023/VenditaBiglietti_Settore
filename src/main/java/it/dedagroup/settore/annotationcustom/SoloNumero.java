package it.dedagroup.settore.annotationcustom;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SoloNumeroValidator.class)
public @interface SoloNumero {

        String message() default "Deve essere un numero";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
}
