package com.yanvelasco.imgeliteapi.domain.userDomain.dto;

import lombok.Builder;

@Builder
public record UserResponseDTO(
        String name,
        String email
) {
}
