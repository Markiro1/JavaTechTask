package com.ashapiro.testassigment.exception;

public class UpdateDataException extends RuntimeException{
    public UpdateDataException() {
        super("Failed to update data");
    }
}
