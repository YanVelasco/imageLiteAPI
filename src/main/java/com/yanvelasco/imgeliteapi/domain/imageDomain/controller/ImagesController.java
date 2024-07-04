package com.yanvelasco.imgeliteapi.domain.imageDomain.controller;

import com.yanvelasco.imgeliteapi.domain.imageDomain.usecases.CreateImageUseCase;
import com.yanvelasco.imgeliteapi.domain.imageDomain.usecases.GetImageById;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/v1/images")
@Slf4j
@RequiredArgsConstructor
public class ImagesController {

    private final CreateImageUseCase createImageUseCase;
    private final GetImageById getImageById;

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

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> get(@PathVariable UUID id) {
        return getImageById.execute(id);
    }
}
