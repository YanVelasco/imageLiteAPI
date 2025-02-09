package com.yanvelasco.imgeliteapi.domain.userDomain.dto.jwt;

import com.yanvelasco.imgeliteapi.security.AccessToken;
import com.yanvelasco.imgeliteapi.domain.userDomain.entity.UserEntity;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final SecretKeyGenerator secretKeyGenerator;

    public AccessToken generateToken(UserEntity userEntity) {
        secretKeyGenerator.getSecretKey();

        String token = Jwts.builder()
                .signWith(secretKeyGenerator.getSecretKey())
                .setSubject(userEntity.getEmail())
                .setExpiration(expiration())
                .addClaims(generateTokenClaims(userEntity))
                .compact();

        return new AccessToken(token);
    }

    private Date expiration() {
        Date now = new Date(System.currentTimeMillis());
        return new Date(now.getTime() + 2 * 60 * 60 * 1000);
    }

    private Map<String, Object> generateTokenClaims(UserEntity userEntity) {
        return Map.of(
                "name", userEntity.getName(),
                "id", userEntity.getId(),
                "email", userEntity.getEmail(),
                "password", userEntity.getPassword(),
                "createdAt", Date.from(userEntity.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant())
        );
    }

    public String getEmailFromToken(String token) {
        try {
            return Jwts.parser().verifyWith(secretKeyGenerator.getSecretKey())
                    .build().parseSignedClaims(token)
                    .getPayload().getSubject();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid token");
        }
    }
}
