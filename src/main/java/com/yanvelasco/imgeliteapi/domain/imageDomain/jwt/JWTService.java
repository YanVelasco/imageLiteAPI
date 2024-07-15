package com.yanvelasco.imgeliteapi.domain.imageDomain.jwt;

import com.yanvelasco.imgeliteapi.domain.security.AccessToken;
import com.yanvelasco.imgeliteapi.domain.userDomain.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                .expiration( expiration())
                .claims(generateTokenClaims(userEntity))
                .compact();

        return new AccessToken(token);
    }

    private Date expiration() {
        LocalDateTime now = LocalDateTime.now().plusHours(2);
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Map<String, Object> generateTokenClaims(UserEntity userEntity){
        return Map.of(
                "name", userEntity.getName(),
                "id", userEntity.getId(),
                "email", userEntity.getEmail(),
                "password", userEntity.getPassword(),
                "createdAt", userEntity.getCreatedAt()
        );
    }
}