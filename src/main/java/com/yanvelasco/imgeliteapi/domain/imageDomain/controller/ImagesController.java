package com.yanvelasco.imgeliteapi.domain.imageDomain.controller;

import com.yanvelasco.imgeliteapi.domain.imageDomain.usecases.CreateImageUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("/v1/images")
@Slf4j
@RequiredArgsConstructor
public class ImagesController {

    private final CreateImageUseCase createImageUseCase;

    @PostMapping
    public ResponseEntity<Object> save(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("tags") List<String> tags,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var response = createImageUseCase.execute(file, name, tags, uriComponentsBuilder);

        return response;
    }
}
