package com.luigivismara.serviceuser.controller;

import com.luigivismara.modeldomain.http.HttpResponse;
import com.luigivismara.modeldomain.security.dto.request.LoginRequest;
import com.luigivismara.modeldomain.security.dto.response.JwtResponse;
import com.luigivismara.modeldomain.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.luigivismara.modeldomain.configuration.ConstantsVariables.API_V1;

@RestController
@RequestMapping(API_V1 + "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public HttpResponse<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
