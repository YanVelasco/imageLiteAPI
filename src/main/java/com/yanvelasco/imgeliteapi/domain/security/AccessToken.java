package com.yanvelasco.imgeliteapi.domain.security;

import lombok.Builder;

@Builder
public record AccessToken(
       String accessToken
) {
}
