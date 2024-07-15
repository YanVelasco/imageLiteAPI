package com.yanvelasco.imgeliteapi.domain.userDomain.useCases;

import com.yanvelasco.imgeliteapi.domain.userDomain.jwt.JWTService;
import com.yanvelasco.imgeliteapi.domain.userDomain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public ResponseEntity<Object> authenticate(String email, String password) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (!matches) {
            throw new IllegalArgumentException("Invalid password");
        }
        var token = jwtService.generateToken(user);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok(token);
        }
    }
}