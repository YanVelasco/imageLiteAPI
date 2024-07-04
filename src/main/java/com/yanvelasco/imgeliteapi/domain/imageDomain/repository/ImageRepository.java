package com.yanvelasco.imgeliteapi.domain.imageDomain.repository;

import com.yanvelasco.imgeliteapi.domain.imageDomain.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<ImageEntity, UUID> {
    Optional<Object> findByName(String name);
}
