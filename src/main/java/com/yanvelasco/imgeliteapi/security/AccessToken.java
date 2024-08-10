package com.yanvelasco.imgeliteapi.security;

import lombok.Builder;

@Builder
public record AccessToken(
       String accessToken
) {
}
