package com.company.hotelmanagementsystem.exception;

import com.company.hotelmanagementsystem.dto.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(NotFoundException ex,
                                                            HttpServletRequest req) {
        log.error("Not found. errorMessage: {}", ex.getMessage());
        final String path = req.getRequestURI();

        return ResponseEntity.status(NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                                .error(ex.getErrorCode())
                                .message(ex.getErrorMessage())
                                .status(404)
                                .path(path)
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpServletRequest req) {

        log.error("Method argument not valid. errorMessage: {}", ex.getMessage());
        final String path = req.getRequestURI();
        Set<String> errors = new HashSet<>();

        ex.getBindingResult().getFieldErrors()
                .forEach((e) -> errors.add(e.getDefaultMessage()));

        return ResponseEntity.status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .error("Validation Error")
                                .validationErrors(errors)
                                .status(400)
                                .path(path)
                                .build()
                );
    }

    @ExceptionHandler(BookingValidationException.class)
    public ResponseEntity<ExceptionResponse> handleBookingValidation(BookingValidationException ex,
                                                                     HttpServletRequest req) {
        log.error("Booking is not valid. errorMessage: {}", ex.getMessage());
        final String path = req.getRequestURI();

        return ResponseEntity.status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .error(ex.getErrorCode())
                                .message(ex.getErrorMessage())
                                .status(400)
                                .path(path)
                                .build()
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgument(IllegalArgumentException ex,
                                                                   HttpServletRequest req) {
        log.error("IllegalArgumentException happened. errorMessage: {}", ex.getMessage());
        final String path = req.getRequestURI();

        return ResponseEntity.status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .error("ILLEGAL_ARGUMENT")
                                .message(ex.getMessage())
                                .status(400)
                                .path(path)
                                .build()
                );
    }

}
