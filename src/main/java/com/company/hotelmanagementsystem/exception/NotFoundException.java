package com.company.hotelmanagementsystem.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    public NotFoundException(String msg,String errorCode) {
        super(msg);
        this.errorCode = errorCode;
        this.errorMessage = msg;
    }
}
