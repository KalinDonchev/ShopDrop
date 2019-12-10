package com.kalin.shopdrop.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Product already exists.")
public class ProductAlreadyExistsException extends RuntimeException {

    private int statusCode;

    public ProductAlreadyExistsException() {
        this.statusCode = 409;
    }

    public ProductAlreadyExistsException(String message) {
        super(message);
        this.statusCode = 409;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
