package com.luigivismara.modeldomain.security.service.impl;

import com.luigivismara.modeldomain.enums.TokenType;
import com.luigivismara.modeldomain.http.HttpResponse;
import com.luigivismara.modeldomain.security.JwtUtil;
import com.luigivismara.modeldomain.security.dto.request.LoginRequest;
import com.luigivismara.modeldomain.security.dto.response.JwtResponse;
import com.luigivismara.modeldomain.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    public HttpResponse<JwtResponse> login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        final var userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final var jwt = jwtUtil.generateToken(userDetails);

        return new HttpResponse<>(HttpStatus.OK, new JwtResponse(jwt, TokenType.BEARER));
    }
}
