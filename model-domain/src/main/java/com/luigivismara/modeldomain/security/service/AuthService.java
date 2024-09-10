package com.luigivismara.modeldomain.security.service;

import com.luigivismara.modeldomain.http.HttpResponse;
import com.luigivismara.modeldomain.security.dto.request.LoginRequest;
import com.luigivismara.modeldomain.security.dto.response.JwtResponse;

public interface AuthService {
    HttpResponse<JwtResponse> login(LoginRequest loginRequest);
}
