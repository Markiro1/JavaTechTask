package com.ashapiro.testassigment.exception;

public class UserAgeLimitException extends RuntimeException {

    public UserAgeLimitException() {
        super("User must be at least 18 years old");
    }
}
