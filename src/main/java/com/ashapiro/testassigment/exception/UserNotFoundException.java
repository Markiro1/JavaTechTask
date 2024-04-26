package com.ashapiro.testassigment.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(int id) {
        super(String.format("User does not exist with id: %d ", id));
    }
}
