package com.luigivismara.modeldomain.repository;

import com.luigivismara.modeldomain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository  extends JpaRepository<UserEntity, UUID> {
}
