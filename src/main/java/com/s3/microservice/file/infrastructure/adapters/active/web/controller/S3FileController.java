package com.s3.microservice.file.infrastructure.adapters.active.web.controller;

import com.s3.microservice.file.infrastructure.adapters.active.web.domain.FileMetaData;
import com.s3.microservice.file.infrastructure.adapters.active.web.service.FileService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
@Slf4j
public class S3FileController {

    private final FileService fileService;

    @GetMapping("/welcomenote")
    public ResponseEntity<String> welcomNote() {
    return new ResponseEntity<>("Welcome to AWS S3 storage application.", HttpStatus.OK);
    }

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws IOException {
        fileService.uploadFileToS3(file.getOriginalFilename(), file.getInputStream());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/files/metadata")
    public ResponseEntity<List<FileMetaData>> getFilesMetaDataFropmS3() {
        List<FileMetaData> files = fileService.getFilesMetaData();
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping("/file")
    public ResponseEntity<Resource> downloadFileFromS3(@PathVariable @Validated @NonNull String fileName) throws IOException {
        Resource file = fileService.downloadFileFromS3(fileName);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }
}
