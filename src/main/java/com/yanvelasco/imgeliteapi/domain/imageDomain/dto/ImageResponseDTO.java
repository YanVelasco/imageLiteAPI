package com.yanvelasco.imgeliteapi.domain.imageDomain.dto;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record ImageResponseDTO(
        String url,
        String name,
        Long size,
        String extension,
        LocalDate uploadedAt
) {}