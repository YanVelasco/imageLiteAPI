package com.yanvelasco.imgeliteapi.domain.imageDomain.dto;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record ImageRequestDTO(
        MultipartFile  file,
        String name,
        List<String> tags
) {
}
