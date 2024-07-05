package com.yanvelasco.imgeliteapi.domain.imageDomain.repository;

import com.yanvelasco.imgeliteapi.domain.imageDomain.entity.ImageEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<ImageEntity, UUID>, JpaSpecificationExecutor<ImageEntity> {

    Optional<Object> findByName(String name);

    Optional<ImageEntity> getImageById(UUID id);

    default List<ImageEntity> findByExtensionAndNameOrTagsLike(String extension, String query) {
        return findAll((root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (extension != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.upper(root.get("extension")), extension.toUpperCase()));
            }

            if (StringUtils.hasText(query)) {
                String queryUpperCase = "%" + query.toUpperCase() + "%";
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.or(
                                criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), queryUpperCase),
                                criteriaBuilder.like(criteriaBuilder.upper(root.get("tags")), queryUpperCase)
                        ));
            }
            return predicate;
        });
    }
}