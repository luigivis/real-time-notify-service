package com.luigivismara.serviceuser.services.impl;

import com.luigivismara.modeldomain.repository.UserRepository;
import com.luigivismara.serviceuser.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;

    public UserServicesImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
