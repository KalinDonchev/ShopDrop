package com.kalin.shopdrop.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Category not found.")
public class NewsNotFoundException extends RuntimeException {

    private int statusCode;

    public NewsNotFoundException() {
        this.statusCode = 404;
    }

    public NewsNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }



}
