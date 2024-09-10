package com.luigivismara.serviceuser.dto.resquest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.luigivismara.modeldomain.configuration.ConstantsVariables.PATTERN_SECURE_PASSWORD;
import static com.luigivismara.modeldomain.configuration.ConstantsVariables.PATTERN_SECURE_PASSWORD_MESSAGE;

public record UserDto(
        @NotBlank(message = "The userName Can not be null")
        String username,
        @Email(message = "The email is invalid")
        @NotBlank(message = "The email Can not be null")
        String email,
        @NotBlank(message = "The password Can not be null")
        @Pattern(regexp = PATTERN_SECURE_PASSWORD, message = PATTERN_SECURE_PASSWORD_MESSAGE)
        String password) {
}