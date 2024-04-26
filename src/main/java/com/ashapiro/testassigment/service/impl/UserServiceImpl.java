package com.ashapiro.testassigment.service.impl;

import com.ashapiro.testassigment.dto.UpdateUserDto;
import com.ashapiro.testassigment.dto.UserDto;
import com.ashapiro.testassigment.exception.IncorrectDateException;
import com.ashapiro.testassigment.exception.UpdateDataException;
import com.ashapiro.testassigment.exception.UserAgeLimitException;
import com.ashapiro.testassigment.exception.UserNotFoundException;
import com.ashapiro.testassigment.model.User;
import com.ashapiro.testassigment.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Setter
    private Map<Integer, User> userMap = new HashMap<>();

    private final ModelMapper modelMapper;

    private static int counter = 1;

    @Value("${user.ageLimit}")
    private int ageLimit;

    @Override
    public User add(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        LocalDate userBirthday = user.getBirthDate();
        verifyAge(userBirthday);
        user.setId(counter++);
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(int userId, UserDto userDto) {
        verifyId(userId);
        User updatedUser = modelMapper.map(userDto, User.class);
        updatedUser.setId(userId);
        userMap.replace(userId, updatedUser);
        return updatedUser;
    }

    @Override
    public User partiallyUpdate(int userId, UpdateUserDto updateUserDto) {
        verifyId(userId);
        User currentUser = userMap.get(userId);
        try {
            return updateFields(currentUser, updateUserDto);
        } catch (IllegalAccessException e) {
            throw new UpdateDataException();
        }
    }

    @Override
    public void delete(int userId) {
        verifyId(userId);
        userMap.remove(userId);
    }

    @Override
    public List<User> searchUsersByDate(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new IncorrectDateException();
        }
        return userMap.values().stream()
                .filter(user -> !user.getBirthDate().isBefore(from) && !user.getBirthDate().isAfter(to))
                .toList();
    }

    private User updateFields(User currentUser, UpdateUserDto updateUserDto) throws IllegalAccessException {
        User updatedUser = modelMapper.map(updateUserDto, User.class);
        Field[] fields = currentUser.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            if (name.equals("id")) {
                continue;
            }
            Object currentValue = field.get(currentUser);
            Object newValue = field.get(updatedUser);

            if (newValue != null && !Objects.equals(currentValue, updatedUser)) {
                field.set(currentUser, newValue);
            }
        }
        return currentUser;
    }

    private void verifyAge(LocalDate userBirthday) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(userBirthday, currentDate);
        int age = period.getYears();
        if (userBirthday.isEqual(currentDate) || age < ageLimit) {
            throw new UserAgeLimitException();
        }
    }

    private void verifyId(int id) {
        if (id < 0 || !userMap.containsKey(id)) {
            throw new UserNotFoundException(id);
        }
    }

}
