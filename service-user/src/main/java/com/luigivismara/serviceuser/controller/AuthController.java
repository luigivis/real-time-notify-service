package com.luigivismara.serviceuser.controller;

import com.luigivismara.modeldomain.http.HttpResponse;
import com.luigivismara.modeldomain.security.JwtUtil;
import com.luigivismara.serviceuser.dto.request.LoginRequest;
import com.luigivismara.serviceuser.dto.response.JwtResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luigivismara.modeldomain.enums.TokenType;
import lombok.RequiredArgsConstructor;

import static com.luigivismara.modeldomain.configuration.ConstantsVariables.API_V1;

@RestController
@RequestMapping(API_V1 + "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public HttpResponse<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        final var userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final var jwt = jwtUtil.generateToken(userDetails);

        return new HttpResponse<>(HttpStatus.OK, new JwtResponse(jwt, TokenType.BEARER));
    }
}
