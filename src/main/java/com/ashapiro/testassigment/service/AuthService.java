package com.ashapiro.testassigment.service;

import com.ashapiro.testassigment.dto.user.UserAuthDTO;

public interface AuthService {
    void registerUser(UserAuthDTO userDTO);

    String authenticateUser(UserAuthDTO userDTO);
}
