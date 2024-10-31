package com.ashapiro.testassigment.controller;

import com.ashapiro.testassigment.dto.user.UserAuthDTO;
import com.ashapiro.testassigment.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@RequestBody UserAuthDTO userDTO) {
        authService.registerUser(userDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/signIn")
    public ResponseEntity authenticateUser(@RequestBody UserAuthDTO userDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.authenticateUser(userDTO));
    }
}
