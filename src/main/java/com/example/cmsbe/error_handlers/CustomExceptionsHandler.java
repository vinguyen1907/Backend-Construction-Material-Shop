package com.example.cmsbe.error_handlers;

import com.example.cmsbe.error_handlers.errors.ApiError;
import com.example.cmsbe.error_handlers.custom_exceptions.NotEnoughQuantityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class CustomExceptionsHandler {
    @ExceptionHandler(NotEnoughQuantityException.class)
    public ResponseEntity<Object> handleNotEnoughQuantityException(NotEnoughQuantityException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setErrorCode("NOT_ENOUGH_QUANTITY");
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
