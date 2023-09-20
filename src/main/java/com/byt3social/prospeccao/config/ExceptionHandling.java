package com.byt3social.prospeccao.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandling {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity sqlIntegrityConstraintViolation(SQLIntegrityConstraintViolationException e) {
        Map<String, String> response = new HashMap<>();
        response.put("code", ((Integer) HttpStatus.INTERNAL_SERVER_ERROR.value()).toString());
        response.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.put("message", e.getMessage());

        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValid(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getFieldErrors();

        return new ResponseEntity(errors.stream().map(FieldErrorDTO::new).toList(), HttpStatus.BAD_REQUEST);
    }

    private record FieldErrorDTO(String field, String message) {
        public FieldErrorDTO(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
