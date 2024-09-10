package com.luigivismara.serviceuser.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luigivismara.modeldomain.entity.UserEntity;
import com.luigivismara.modeldomain.http.HttpResponse;
import com.luigivismara.modeldomain.repository.UserRepository;
import com.luigivismara.modeldomain.utils.PageableTools;
import com.luigivismara.serviceuser.dto.request.UserDto;
import com.luigivismara.serviceuser.dto.response.UserDtoResponse;
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

    @Override
    public HttpResponse<UserDtoResponse> getById(UUID id) {
        return userRepository.findById(id)
                .map(user -> new HttpResponse<>(HttpStatus.OK, objectMapper.convertValue(user, UserDtoResponse.class)))
                .orElse(new HttpResponse<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public HttpResponse<UserDtoResponse> update(UUID id, UserDto userDto) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(userDto.getUsername());
                    user.setEmail(userDto.getEmail());
                    if (userDto.getPassword() != null) {
                        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    }
                    var updatedUser = userRepository.save(user);
                    return new HttpResponse<>(HttpStatus.OK, objectMapper.convertValue(updatedUser, UserDtoResponse.class));
                })
                .orElse(new HttpResponse<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public HttpResponse<Object> delete(UUID id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return new HttpResponse<>(HttpStatus.NO_CONTENT);
                })
                .orElse(new HttpResponse<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public HttpResponse<UserDtoResponse> getByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new HttpResponse<>(HttpStatus.OK, objectMapper.convertValue(user, UserDtoResponse.class)))
                .orElse(new HttpResponse<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public HttpResponse<UserDtoResponse> getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> new HttpResponse<>(HttpStatus.OK, objectMapper.convertValue(user, UserDtoResponse.class)))
                .orElse(new HttpResponse<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public HttpResponse<Boolean> existsByUsername(String username) {
        boolean exists = userRepository.existsByUsername(username);
        return new HttpResponse<>(HttpStatus.OK, exists);
    }

    @Override
    public HttpResponse<Boolean> existsByEmail(String email) {
        boolean exists = userRepository.existsByEmail(email);
        return new HttpResponse<>(HttpStatus.OK, exists);
    }

}
