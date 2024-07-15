package com.yanvelasco.imgeliteapi.domain.userDomain.repository;

import com.yanvelasco.imgeliteapi.domain.userDomain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
}