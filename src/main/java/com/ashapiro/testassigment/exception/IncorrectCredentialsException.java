package com.ashapiro.testassigment.exception;

public class IncorrectCredentialsException extends RuntimeException{

    public IncorrectCredentialsException() {
        super("Incorrect email or password");
    }
}
