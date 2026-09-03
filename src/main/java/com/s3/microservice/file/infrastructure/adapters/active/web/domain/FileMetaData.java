package com.s3.microservice.file.infrastructure.adapters.active.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileMetaData {
    private String fileName;
    private String fileType;
    private long fileSize;
    private String fileDate;
}
