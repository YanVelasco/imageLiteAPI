package com.yanvelasco.imgeliteapi.domain.userDomain.useCases;

import com.yanvelasco.imgeliteapi.domain.imageDomain.jwt.JWTService;
import com.yanvelasco.imgeliteapi.domain.security.AccessToken;
import com.yanvelasco.imgeliteapi.domain.userDomain.dto.UserRequestDTO;
import com.yanvelasco.imgeliteapi.domain.userDomain.dto.UserResponseDTO;
import com.yanvelasco.imgeliteapi.domain.userDomain.entity.UserEntity;
import com.yanvelasco.imgeliteapi.domain.userDomain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Transactional
    public ResponseEntity<Object> execute(UserRequestDTO userRequestDTO, UriComponentsBuilder uriComponentsBuilder) {
            var createdUser = UserEntity.builder()
                    .name(userRequestDTO.name())
                    .email(userRequestDTO.email())
                    .password(userRequestDTO.password())
                    .build();

            userRepository.findByEmail(userRequestDTO.email())
                    .ifPresent(user -> {
                        throw new IllegalArgumentException("Email already exists");
                    });

            encodePassword(createdUser);

            userRepository.save(createdUser);

            var response = UserResponseDTO.builder()
                    .name(createdUser.getName())
                    .email(createdUser.getEmail())
                    .build();

            return ResponseEntity.created(uriComponentsBuilder.path("/v1/users/{id}").buildAndExpand(createdUser.getId()).toUri()).body(response);
    }


    public AccessToken authenticate(String email, String password) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (!matches) {
            throw new IllegalArgumentException("Invalid password");
        }
        return jwtService.generateToken(user);
    }

    private void encodePassword(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}