package com.luigivismara.serviceuser.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luigivismara.modeldomain.http.HttpResponse;
import com.luigivismara.serviceuser.dto.response.UserDtoResponse;
import com.luigivismara.serviceuser.dto.resquest.UserDto;
import com.luigivismara.serviceuser.services.UserServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.luigivismara.modeldomain.configuration.ConstantsVariables.API_V1;

@RestController
@RequestMapping(API_V1 + "/users")
@RequiredArgsConstructor
public class UserControllers {

    private final UserServices services;

    @PostMapping
    public HttpResponse<UserDtoResponse> create(@Valid @RequestBody UserDto user) throws JsonProcessingException {
        return services.create(user);
    }
}
