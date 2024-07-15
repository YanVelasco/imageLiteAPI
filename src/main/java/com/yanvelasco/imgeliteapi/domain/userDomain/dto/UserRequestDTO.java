package com.yanvelasco.imgeliteapi.domain.userDomain.dto;

public record UserRequestDTO(
        String name,
        String email,
        String password
) {
}
