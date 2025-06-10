package com.nzefler.cart.exception;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends RuntimeException{

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
