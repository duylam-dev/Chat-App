package com.hust.chatappbackend.exception;

import com.hust.chatappbackend.dto.ApiResponseCustom;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseCustom<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(ApiResponseCustom.<Void>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .errors(errors)
                .build());
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponseCustom<Void>> handleAppExceptions(AppException ex) {
        return ResponseEntity.badRequest().body(ApiResponseCustom.<Void>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build());
    }



}
