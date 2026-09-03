package com.s3.microservice.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.Instant;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                "An I/O error occurred: " + ex.getMessage(),
                status.value(),
                "Please check the file system or network connection."
        );
        return ResponseEntity.status(status).body(response);
    }

    public record ErrorResponse(Instant timestamp, String message, int status, String description) {
    }
}
