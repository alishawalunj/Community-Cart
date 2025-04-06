package com.nzefler.product_service.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends GraphQLApiException {

    public EntityNotFoundException(String message) {
        super("Entity not found", HttpStatus.NOT_FOUND);
    }
}
