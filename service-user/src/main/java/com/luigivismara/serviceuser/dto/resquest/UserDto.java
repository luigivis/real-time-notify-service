package com.luigivismara.serviceuser.dto.resquest;

import jakarta.validation.constraints.NotBlank;

public record UserDto(
        @NotBlank(message = "The userName Can not be null")
        String username,
        @NotBlank(message = "The email Can not be null")
        String email,
        @NotBlank(message = "The password Can not be null")
        String password) {
}