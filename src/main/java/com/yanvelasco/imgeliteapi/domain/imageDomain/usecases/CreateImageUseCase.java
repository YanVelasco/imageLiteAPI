package com.yanvelasco.imgeliteapi.domain.imageDomain.usecases;

import com.yanvelasco.imgeliteapi.domain.imageDomain.dto.ImageRequestDTO;
import com.yanvelasco.imgeliteapi.domain.imageDomain.dto.ImageResponseDTO;
import com.yanvelasco.imgeliteapi.domain.imageDomain.entity.ImageEntity;
import com.yanvelasco.imgeliteapi.domain.imageDomain.enums.ImageExtensionEnum;
import com.yanvelasco.imgeliteapi.domain.imageDomain.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CreateImageUseCase {
    private final ImageRepository imageRepository;

    @Transactional
    public ResponseEntity<Object> execute(MultipartFile file, String name, List<String> tags, UriComponentsBuilder uriComponentsBuilder) {
        ImageRequestDTO imageRequestDTO = ImageRequestDTO.builder()
                .file(file)
                .name(name)
                .tags(tags)
                .build();

        try {
            if (imageRequestDTO.file() != null && !imageRequestDTO.file().isEmpty()) {
                imageRepository.findByName(imageRequestDTO.name()).ifPresent(imageEntity -> {
                    throw new IllegalArgumentException("Image with name " + imageRequestDTO.name() + " already exists");
                });

                ImageEntity imageEntity = ImageEntity.builder()
                        .name(imageRequestDTO.name())
                        .size(imageRequestDTO.file().getSize())
                        .extension(ImageExtensionEnum.valueOf(Objects.requireNonNull(imageRequestDTO.file().getContentType()).split("/")[1].toUpperCase()))
                        .tags(String.join(",", imageRequestDTO.tags()))
                        .file(imageRequestDTO.file().getBytes())
                        .build();

                imageRepository.save(imageEntity);

                ImageResponseDTO imageResponseDTO = ImageResponseDTO.builder()
                        .url(String.valueOf(buildURI(imageEntity)))
                        .name(imageEntity.getName())
                        .size(imageEntity.getSize())
                        .extension(imageEntity.getExtension().name())
                        .uploadedAt(imageEntity.getCreatedAt().toLocalDate())
                        .build();

                return ResponseEntity.created(uriComponentsBuilder.path("/v1/images/{id}").buildAndExpand(imageEntity.getId()).toUri()).body(imageResponseDTO);
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error accessing file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.badRequest().body("File is required");
    }

    private URI buildURI(ImageEntity image) {
        String path = "/%s".formatted(image.getId());
        return ServletUriComponentsBuilder.fromCurrentRequest().path(path).build().toUri();
    }
}