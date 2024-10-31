package com.ashapiro.testassigment.service;

import com.ashapiro.testassigment.dto.user.UserAuthDTO;
import com.ashapiro.testassigment.model.User;

public interface UserService {

    User registration(UserAuthDTO userDTO);

}
