package com.ashapiro.testassigment.service;

import com.ashapiro.testassigment.dto.UserDto;
import com.ashapiro.testassigment.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    User add(UserDto userDto);

    User update(int userId, UserDto userDto);

    User partiallyUpdate(int userId, User user);

    void delete(int userId);

    List<User> searchUsersByDate(LocalDate from, LocalDate to);
}
