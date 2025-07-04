package com.company.hotelmanagementsystem.exception;

import com.company.hotelmanagementsystem.dto.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(NotFoundException ex, HttpServletRequest req){
        String path = req.getRequestURI();

        return ResponseEntity.status(NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                                .error("Not Found")
                                .message(ex.getMessage())
                                .status(404)
                                .path(path)
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleNotValid(MethodArgumentNotValidException ex, HttpServletRequest req){
        String path = req.getRequestURI();
        Set<String> errors = new HashSet<>();

        ex.getBindingResult().getFieldErrors()
                .forEach((e)-> errors.add(e.getDefaultMessage()));

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

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ExceptionResponse> handleInvalid(InvalidException ex, HttpServletRequest req){
        String path = req.getRequestURI();

        return ResponseEntity.status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .error("Bad Request")
                                .message(ex.getMessage())
                                .status(400)
                                .path(path)
                                .build()
                );
    }

}
