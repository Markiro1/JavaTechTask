package com.ashapiro.testassigment.exception;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException(String email) {
        super(String.format("User already exist with email: %s", email));
    }
}
