package com.yanvelasco.imgeliteapi.domain.imageDomain.usecases;

import com.yanvelasco.imgeliteapi.domain.imageDomain.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetImageById {
    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> execute(UUID id) {
        return imageRepository.findById(id)
                .map(imageEntity -> {
                    String fileName = imageEntity.getName() + "." + imageEntity.getExtension().name().toLowerCase();
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.valueOf(imageEntity.getExtension().getContentType()));
                    headers.setContentDispositionFormData("inline; filename=\"" + imageEntity.getName() + "." + imageEntity.getExtension() + "\"", fileName);
                    return new ResponseEntity<>(imageEntity.getFile(), headers, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}