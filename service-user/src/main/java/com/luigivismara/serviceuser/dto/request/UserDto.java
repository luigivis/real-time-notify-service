package com.luigivismara.serviceuser.dto.request;

import com.luigivismara.modeldomain.annotation.NotBlankWithFieldName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static com.luigivismara.modeldomain.configuration.ConstantsVariables.*;

@Data
public class UserDto {
    @NotBlankWithFieldName
    private String username;

    @NotBlankWithFieldName
    @Email(message = INVALID_EMAIL)
    private String email;

    @NotBlankWithFieldName
    @Pattern(regexp = PATTERN_SECURE_PASSWORD, message = PATTERN_SECURE_PASSWORD_MESSAGE)
    private String password;
}