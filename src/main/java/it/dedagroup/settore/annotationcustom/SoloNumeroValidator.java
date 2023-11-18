package it.dedagroup.settore.annotationcustom;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SoloNumeroValidator implements ConstraintValidator<SoloNumero,Object> {
    @Override
    public void initialize(SoloNumero constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null è considerato valido, gestire se necessario
        }

        try {
            Double.parseDouble(value.toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
