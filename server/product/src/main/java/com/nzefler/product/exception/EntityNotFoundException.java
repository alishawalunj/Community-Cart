package com.nzefler.product.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
