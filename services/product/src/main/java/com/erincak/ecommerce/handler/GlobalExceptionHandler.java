package com.erincak.ecommerce.handler;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.erincak.ecommerce.exception.ProductPurchaseException;

import jakarta.persistence.EntityNotFoundException;

/**
 * GlobalExceptionHandler is responsible for handling exceptions globally across the application.
 * It uses @RestControllerAdvice to provide centralized exception handling for all @RestController classes.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductPurchaseException.class)
    public ResponseEntity<String> handle(ProductPurchaseException exp){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMessage());
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handle(EntityNotFoundException exp){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMessage());
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exp){
        var errors = new HashMap<String, String>();
        exp.getBindingResult().getAllErrors().
            forEach(error -> {
                var fieldName = ((FieldError)error).getField();
                var errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        return ResponseEntity
        .status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
    }
}
