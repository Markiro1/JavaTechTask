package com.ashapiro.testassigment.exception;

public class StatisticsNotFoundException extends RuntimeException{

    public StatisticsNotFoundException() {
        super("Statistics not found");
    }
}
