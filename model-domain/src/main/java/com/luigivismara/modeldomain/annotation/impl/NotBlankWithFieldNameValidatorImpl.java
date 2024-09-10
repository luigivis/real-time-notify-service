package com.luigivismara.modeldomain.annotation.impl;

import com.luigivismara.modeldomain.annotation.NotBlankWithFieldName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import static com.luigivismara.modeldomain.configuration.ConstantsVariables.INVALID_STRING_NOT_BLANK;

public class NotBlankWithFieldNameValidatorImpl implements ConstraintValidator<NotBlankWithFieldName, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null || value.toString().trim().isEmpty()) {
            var fieldName = ((ConstraintValidatorContextImpl) context)
                    .getConstraintViolationCreationContexts()
                    .getFirst()
                    .getPath()
                    .asString();

            fieldName = fieldName.substring(0, 1).toUpperCase();

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fieldName + INVALID_STRING_NOT_BLANK)
                    .addConstraintViolation();

            return false;
        }
        return true;
    }
}
