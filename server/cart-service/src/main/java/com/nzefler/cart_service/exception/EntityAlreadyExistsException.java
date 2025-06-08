package com.nzefler.cart_service.exception;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends GraphQLApiException{

    public EntityAlreadyExistsException(String message) {
        super("Entity already exists", HttpStatus.CONFLICT);
    }
}
