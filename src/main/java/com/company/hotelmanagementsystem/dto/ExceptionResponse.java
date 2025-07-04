package com.company.hotelmanagementsystem.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public class ExceptionResponse {
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String message;
    private String path;
    private Set<String> validationErrors;

}
