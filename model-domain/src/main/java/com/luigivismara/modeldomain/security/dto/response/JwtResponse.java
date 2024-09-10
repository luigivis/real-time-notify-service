package com.luigivismara.modeldomain.security.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luigivismara.modeldomain.enums.RolesType;
import com.luigivismara.modeldomain.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponse {
    private String token;
    private TokenType type = TokenType.BEARER;
    private String username;
    private String email;
    private RolesType role;

    public JwtResponse(String token, TokenType type) {
        this.token = token;
        this.type = type;
    }
}
