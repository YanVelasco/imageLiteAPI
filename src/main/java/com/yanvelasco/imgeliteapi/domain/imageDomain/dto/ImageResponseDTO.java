package com.yanvelasco.imgeliteapi.domain.imageDomain.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ImageResponseDTO(
        UUID id,
        String name,
        Long size,
        String extension,
        String tags,
        byte[] file
) {
}
