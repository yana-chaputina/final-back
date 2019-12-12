package ru.rosbank.javaschool.finalprojectback.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rosbank.javaschool.finalprojectback.domain.UploadInfo;
import ru.rosbank.javaschool.finalprojectback.dto.UploadResponseDto;
import ru.rosbank.javaschool.finalprojectback.service.FileService;


import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class RestFileController {
    private final FileService service;


    @PostMapping("/multipart")
    public UploadResponseDto uploadMultipart(@RequestParam MultipartFile file) {
        return service.save(file);
    }

    @PostMapping("/bytes")
    public UploadResponseDto uploadMultipart(@RequestBody byte[] bytes) {
        return service.save(bytes);
    }

    @GetMapping("/entity/{id}")
    public ResponseEntity<Resource> getEntity(@PathVariable String id) {
        UploadInfo uploadInfo = service.get(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploadInfo.getMimeType()))
                .body(uploadInfo.getResource());
    }

    @GetMapping("/bytes/{id}")
    public ResponseEntity<byte[]> getBytes(@PathVariable String id) throws IOException {
        UploadInfo uploadInfo = service.get(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploadInfo.getMimeType()))
                .body(uploadInfo.getResource().getInputStream().readAllBytes());
    }
}
