package com.kalin.shopdrop.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Password is not correct.")
public class IncorrectPasswordException extends RuntimeException {

    private int statusCode;

    public IncorrectPasswordException() {
        this.statusCode = 409;
    }

    public IncorrectPasswordException(String message) {
        super(message);
        this.statusCode = 409;
    }

    public int getStatusCode() {
        return statusCode;

    }

}