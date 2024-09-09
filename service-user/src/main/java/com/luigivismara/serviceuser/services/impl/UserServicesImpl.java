package com.luigivismara.serviceuser.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luigivismara.modeldomain.entity.UserEntity;
import com.luigivismara.modeldomain.repository.UserRepository;
import com.luigivismara.serviceuser.dto.response.UserDtoResponse;
import com.luigivismara.serviceuser.dto.resquest.UserDto;
import com.luigivismara.serviceuser.services.UserServices;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;


    public UserServicesImpl(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    public List<UserDtoResponse> list() {

        List<UserEntity> user = userRepository.findAll();
        List<UserDtoResponse> userDtoResponses = user.stream()
                .map(users -> objectMapper.convertValue(user, UserDtoResponse.class))
                .collect(Collectors.toList());

        return userDtoResponses;
    }

    public UserDtoResponse create(UserDto userDto) {
        UserEntity userEntity = objectMapper.convertValue(userDto, UserEntity.class);
        UserEntity response = userRepository.save(userEntity);
        return objectMapper.convertValue(response, UserDtoResponse.class);

    }
}
