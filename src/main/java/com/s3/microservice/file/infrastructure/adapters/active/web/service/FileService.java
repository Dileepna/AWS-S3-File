package com.s3.microservice.file.infrastructure.adapters.active.web.service;

import com.s3.microservice.file.config.S3ConfigurationProperties;
import com.s3.microservice.file.infrastructure.adapters.active.web.domain.FileMetaData;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final S3Template s3Template;
    private final S3ConfigurationProperties s3ConfigurationProperties;

    public void uploadFileToS3(String fileName, InputStream file) {
        s3Template.upload(s3ConfigurationProperties.getBucketName(), fileName, file);
    }

    public List<FileMetaData> getFilesMetaData() {
        List<FileMetaData> filesMetaData = s3Template.listAllObjects(s3ConfigurationProperties.getBucketName())
                .stream()
                .map(s3Resource -> new FileMetaData(
                        s3Resource.getFilename(),
                        s3Resource.contentType(),
                        s3Resource.contentLength(),
                        String.valueOf(s3Resource.lastModified())))
                .collect(Collectors.toList());
        return filesMetaData;
    }

    public Resource downloadFileFromS3(String fileName) throws IOException {
        return s3Template.download(s3ConfigurationProperties.getBucketName(), fileName);
    }
}
