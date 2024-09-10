package com.luigivismara.serviceuser.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luigivismara.modeldomain.entity.UserEntity;
import com.luigivismara.modeldomain.http.HttpResponse;
import com.luigivismara.modeldomain.repository.UserRepository;
import com.luigivismara.modeldomain.utils.PageableTools;
import com.luigivismara.serviceuser.dto.response.UserDtoResponse;
import com.luigivismara.serviceuser.dto.resquest.UserDto;
import com.luigivismara.serviceuser.services.UserServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PageableTools pageableTools;
    private final PasswordEncoder passwordEncoder;

    public List<UserDtoResponse> list() {

        final var user = userRepository.findAll();

        return user.stream()
                .map(users -> objectMapper.convertValue(user, UserDtoResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public HttpResponse<UserDtoResponse> create(UserDto userDto) {
        var userEntity = objectMapper.convertValue(userDto, UserEntity.class);
        userEntity.setUserId(UUID.randomUUID());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        var response = userRepository.save(userEntity);
        var result = objectMapper.convertValue(response, UserDtoResponse.class);
        return new HttpResponse<>(HttpStatus.CREATED, result);
    }

    @Override
    public HttpResponse<PageableTools.PaginationDto> list(PageRequest pageRequest) {
        return pageableTools.pagination(userRepository.findAll(pageRequest));
    }
}
