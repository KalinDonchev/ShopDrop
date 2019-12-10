package com.kalin.shopdrop.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Review not found.")
public class ReviewNotFoundException extends RuntimeException{

    private int statusCode;

    public ReviewNotFoundException() {
        this.statusCode = 404;
    }

    public ReviewNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }


}
