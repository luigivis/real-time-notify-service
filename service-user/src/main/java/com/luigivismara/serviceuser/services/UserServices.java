package com.luigivismara.serviceuser.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luigivismara.modeldomain.http.HttpResponse;
import com.luigivismara.modeldomain.utils.PageableTools;
import com.luigivismara.serviceuser.dto.request.UserDto;
import com.luigivismara.serviceuser.dto.response.UserDtoResponse;
import com.luigivismara.serviceuser.dto.response.UserLoginResponse;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface UserServices {
    HttpResponse<UserDtoResponse> create(UserDto user) throws JsonProcessingException;

    HttpResponse<PageableTools.PaginationDto> list(PageRequest pageRequest);

    HttpResponse<UserDtoResponse> getById(UUID id);

    HttpResponse<UserDtoResponse> update(UUID id, UserDto user) throws JsonProcessingException;

    HttpResponse<Object> delete(UUID id);

    HttpResponse<UserDtoResponse> getByUsername(String username);

    HttpResponse<UserDtoResponse> getByEmail(String email);

    HttpResponse<Boolean> existsByUsername(String username);

    HttpResponse<Boolean> existsByEmail(String email);
}
