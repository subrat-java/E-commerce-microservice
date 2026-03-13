package com.subrat.user_service.Exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(FeignException ex) {

        ErrorResponse error = new ErrorResponse("User not found", 404);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {

        ErrorResponse error = new ErrorResponse(ex.getMessage(), 400);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handelUserNotFoundForSecurity(UserNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), 4001);
        return new  ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

}