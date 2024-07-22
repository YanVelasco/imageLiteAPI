package com.yanvelasco.imgeliteapi.domain.userDomain.controller;

import com.yanvelasco.imgeliteapi.domain.userDomain.dto.UserRequestDTO;
import com.yanvelasco.imgeliteapi.domain.userDomain.useCases.AuthenticateUserUseCase;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users/auth")
@RequiredArgsConstructor
public class AuthUserController {
    private final AuthenticateUserUseCase authenticateUserUseCase;

    @PostMapping
    public ResponseEntity<Object> authenticate(@RequestBody UserRequestDTO userRequestDTO) {
        return authenticateUserUseCase.authenticate(userRequestDTO.email(), userRequestDTO.password());
    }
}
