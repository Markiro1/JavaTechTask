package com.ashapiro.testassigment.exception;

public class IncorrectDateException extends RuntimeException{
    public IncorrectDateException() {
        super("Invalid date");
    }
}
