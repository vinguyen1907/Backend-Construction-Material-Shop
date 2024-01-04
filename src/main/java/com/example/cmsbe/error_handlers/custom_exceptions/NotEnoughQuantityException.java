package com.example.cmsbe.error_handlers.custom_exceptions;

public class NotEnoughQuantityException extends RuntimeException {

    public NotEnoughQuantityException(String message) {
        super(message);
    }
}