package com.ashapiro.modelUtils;

import com.ashapiro.testassigment.dto.UserDto;
import com.ashapiro.testassigment.model.User;

import java.time.LocalDate;

public class ModelUtils {


    public UserDto userDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail("1@gmail.com");
        userDto.setFirstName("test");
        userDto.setLastName("test");
        userDto.setBirthDate(LocalDate.of(2000, 1, 1));
        return userDto;
    }

    public User user() {
        User user = new User();
        user.setId(1);
        user.setEmail("1@gmail.com");
        user.setFirstName("test");
        user.setLastName("test");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        return user;
    }

    public UserDto updateDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail("update@gmail.com");
        userDto.setFirstName("update");
        userDto.setLastName("update");
        userDto.setBirthDate(LocalDate.of(2000, 1, 1));
        return userDto;
    }

    public User updateUser() {
        User user = new User();
        user.setId(1);
        user.setEmail("update@gmail.com");
        user.setFirstName("update");
        user.setLastName("update");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        return user;
    }

    public UserDto youngUserDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail("update@gmail.com");
        userDto.setFirstName("update");
        userDto.setLastName("update");
        userDto.setBirthDate(LocalDate.of(2016, 1, 1));
        return userDto;
    }

    public User youngUser() {
        User user = new User();
        user.setId(1);
        user.setEmail("1@gmail.com");
        user.setFirstName("test");
        user.setLastName("test");
        user.setBirthDate(LocalDate.of(2016, 1, 1));
        return user;
    }
}
