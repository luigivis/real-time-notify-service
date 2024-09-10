package com.luigivismara.modeldomain.security.dto.request;

import com.luigivismara.modeldomain.annotation.NotBlankWithFieldName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Pattern;

import static com.luigivismara.modeldomain.configuration.ConstantsVariables.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlankWithFieldName
    private String username;

    @NotBlankWithFieldName
    @Pattern(regexp = PATTERN_SECURE_PASSWORD, message = PATTERN_SECURE_PASSWORD_MESSAGE)
    private String password;
}
