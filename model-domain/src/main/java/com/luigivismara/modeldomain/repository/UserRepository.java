package com.luigivismara.modeldomain.repository;

import com.luigivismara.modeldomain.entity.UserEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Cacheable(value = "user:register", key = "#user.username", cacheManager = "tenMinutesCacheManager")
    UserEntity save(@NonNull UserEntity user);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    @Cacheable(value = "user:exist:username", key = "#username", cacheManager = "tenMinutesCacheManager")
    boolean existsByUsername(String username);

    @Cacheable(value = "user:exist:email", key = "#email", cacheManager = "tenMinutesCacheManager")
    boolean existsByEmail(String email);

    @Cacheable(value = "user:list", key = "{ 'size[' + #pageable.pageSize + ']', 'number[' + #pageable.pageNumber + ']'}")
    Page<UserEntity> findAll(@NonNull Pageable pageable);
}
