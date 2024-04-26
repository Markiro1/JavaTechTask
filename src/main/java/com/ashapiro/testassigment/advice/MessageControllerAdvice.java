package com.ashapiro.testassigment.advice;

import com.ashapiro.testassigment.exception.IncorrectDateException;
import com.ashapiro.testassigment.exception.UpdateDataException;
import com.ashapiro.testassigment.exception.UserAgeLimitException;
import com.ashapiro.testassigment.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
public class MessageControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleNotFound(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(IncorrectDateException.class)
    public ResponseEntity<String> handleIncorrectDate(IncorrectDateException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(UpdateDataException.class)
    public ResponseEntity<String> handleUpdate(UpdateDataException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    @ExceptionHandler(UserAgeLimitException.class)
    public ResponseEntity<String> handleUserAgeLimit(UserAgeLimitException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgument(MethodArgumentNotValidException e) {
        String message = Arrays.stream(e.getDetailMessageArguments())
                .map(Object::toString)
                .collect(Collectors.joining());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(message);
    }
}
