package com.yanvelasco.imgeliteapi.domain.imageDomain.usecases;

import com.yanvelasco.imgeliteapi.domain.imageDomain.dto.ImageResponseDTO;
import com.yanvelasco.imgeliteapi.domain.imageDomain.entity.ImageEntity;
import com.yanvelasco.imgeliteapi.domain.imageDomain.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListAllImagesByExtensionOrQuery {
    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<List<ImageResponseDTO>> execute(String extension, String query) {
        try {
            List<ImageEntity> images = imageRepository.findByExtensionAndNameOrTagsLike(extension, query);

            List<ImageResponseDTO> response = new ArrayList<>();
            for (ImageEntity image : images) {
                ImageResponseDTO imageResponseDTO = ImageResponseDTO.builder()
                        .url(String.valueOf(buildURI(image)))
                        .name(image.getName())
                        .size(image.getSize())
                        .extension(image.getExtension().name())
                        .uploadedAt(image.getCreatedAt().toLocalDate())
                        .build(
                );
                response.add(imageResponseDTO);
            }
            if (response.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private URI buildURI(ImageEntity image) {
        String path = "/%s".formatted(image.getId());
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path(path).build().toUri();
    }
}