package com.nzefler.cart_service.exception;

import org.springframework.http.HttpStatus;

public class GraphQLApiException extends RuntimeException{

    private HttpStatus status;

    public GraphQLApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
