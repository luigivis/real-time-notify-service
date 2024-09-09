package com.luigivismara.serviceuser.dto.resquest;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank(message = "The userName Can not be null")
    private String username;

    @NotBlank(message = "The email Can not be null")
    private String email;

    @NotBlank(message = "The password Can not be null")
    private String password;
}
