package com.company.hotelmanagementsystem.exception;

import lombok.Getter;

@Getter
public class DuplicateLanguageException extends RuntimeException {
    private final String errorMessage;
    private final String errorCode;

    public DuplicateLanguageException(String errorMessage, String errorCode) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

}
