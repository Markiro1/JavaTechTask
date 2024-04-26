package com.ashapiro.testassigment.controller;

import com.ashapiro.testassigment.dto.UserDto;
import com.ashapiro.testassigment.model.User;
import com.ashapiro.testassigment.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.add(userDto));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody UserDto userDto) {
        return ResponseEntity
                .ok()
                .body(userService.update(userId, userDto));
    }

    @PatchMapping("/update/{userId}")
    public ResponseEntity<User> partiallyUpdateUser(@PathVariable int userId, @RequestBody User user) {
        return ResponseEntity
                .ok()
                .body(userService.partiallyUpdate(userId, user));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        userService.delete(userId);
        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("/search-by-date")
    public ResponseEntity<List<User>> searchUsersByDate(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        return ResponseEntity
                .ok()
                .body(userService.searchUsersByDate(from, to));
    }
}
