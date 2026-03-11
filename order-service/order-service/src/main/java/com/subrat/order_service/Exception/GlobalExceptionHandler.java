package com.subrat.order_service.Exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handelProductNotFound(FeignException ex){
            ErrorResponse error = new ErrorResponse(ex.getMessage(), 404);

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handelRuntimeException(RuntimeException ex){

        ErrorResponse error = new ErrorResponse(ex.getMessage(), 400);
        return new  ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
