package com.yanvelasco.imgeliteapi.domain.userDomain.controller;

import com.yanvelasco.imgeliteapi.domain.userDomain.dto.UserRequestDTO;
import com.yanvelasco.imgeliteapi.domain.userDomain.useCases.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    @PostMapping
    public ResponseEntity<Object> save(UserRequestDTO userRequestDTO, UriComponentsBuilder uriComponentsBuilder) {
        return createUserUseCase.execute(userRequestDTO, uriComponentsBuilder);
    }

}
