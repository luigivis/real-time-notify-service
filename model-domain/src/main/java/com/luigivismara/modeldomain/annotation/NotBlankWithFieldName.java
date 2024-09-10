package com.luigivismara.modeldomain.annotation;

import com.luigivismara.modeldomain.annotation.impl.NotBlankWithFieldNameValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom validation annotation that checks if a field is not blank and includes
 * the field name in the validation message dynamically.
 * <p>
 * This annotation is a combination of {@code @NotBlank} and a custom validation
 * to include the name of the field being validated in the error message.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * public class UserDto {
 *
 *     @NotBlankWithFieldName
 *     private String username;
 *
 *     @Email(message = "Please provide a valid email address.")
 *     @NotBlankWithFieldName
 *     private String email;
 *
 *     @NotBlankWithFieldName
 *     private String password;
 *
 *     // Getters and setters...
 * }
 * }
 * </pre>
 *
 * The resulting error message for a blank field will include the name of the field
 * dynamically, such as "username cannot be blank." or "email cannot be blank."
 *
 * <p>This annotation uses the {@link NotBlankWithFieldNameValidatorImpl} to perform the validation logic.
 *
 * @see jakarta.validation.ConstraintValidator
 * @see jakarta.validation.Payload
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotBlankWithFieldNameValidatorImpl.class)
public @interface NotBlankWithFieldName {

    /**
     * The error message that will be shown if the field is blank.
     * The default message includes the name of the field dynamically.
     *
     * @return the error message
     */
    String message() default "{field} cannot be blank.";

    /**
     * Allows specifying validation groups, to which this constraint belongs.
     *
     * @return array of classes for grouping constraints
     */
    Class<?>[] groups() default {};

    /**
     * Can be used by clients of the Bean Validation API to assign custom payload objects
     * to a constraint.
     *
     * @return array of payload classes
     */
    Class<? extends Payload>[] payload() default {};
}
