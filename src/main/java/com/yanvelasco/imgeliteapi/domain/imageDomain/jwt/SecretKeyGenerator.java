package com.yanvelasco.imgeliteapi.domain.imageDomain.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class SecretKeyGenerator {

    private SecretKey secretKey;

    public SecretKey getSecretKey() {
        if (secretKey == null) {
            secretKey = Jwts.SIG.HS256.key().build();
        }
        return secretKey;
    }
}
