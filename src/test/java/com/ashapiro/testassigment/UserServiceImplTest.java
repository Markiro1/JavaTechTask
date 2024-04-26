package com.ashapiro.testassigment;

import com.ashapiro.modelUtils.ModelUtils;
import com.ashapiro.testassigment.dto.UserDto;
import com.ashapiro.testassigment.exception.IncorrectDateException;
import com.ashapiro.testassigment.exception.UserAgeLimitException;
import com.ashapiro.testassigment.exception.UserNotFoundException;
import com.ashapiro.testassigment.model.User;
import com.ashapiro.testassigment.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {
    private ModelUtils modelUtils = new ModelUtils();

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private Map<Integer, User> userMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userMap = new HashMap<>();
    }

    @Test
    void addUserTest() {
        UserDto userDto = modelUtils.userDto();
        User user = modelUtils.user();
        when(modelMapper.map(userDto, User.class)).thenReturn(user);

        User result = userService.add(userDto);

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void updateUserTest() {
        UserDto updateDto = modelUtils.updateDto();
        User user = modelUtils.user();
        userMap.put(1, user);
        userService.setUserMap(userMap);

        User updatedUser = modelUtils.updateUser();

        when(modelMapper.map(updateDto, User.class)).thenReturn(updatedUser);

        User result = userService.update(1, updateDto);

        assertEquals(result, updatedUser);
    }

    @Test
    void updateUserThrowsUserNotFoundExceptionTest() {
        UserDto userDto = modelUtils.userDto();
        assertThrows(UserNotFoundException.class, () -> userService.update(11, userDto));
    }

    @Test
    void deleteUserTest() {
        User user = modelUtils.user();
        userMap.put(1, user);
        userService.setUserMap(userMap);
        userService.delete(1);
        assertThrows(UserNotFoundException.class, () -> userService.delete(1));
    }

    @Test
    void deleteUserThrowsUserNotFoundExceptionTest() {
        int id = 23;
        Exception e = assertThrows(UserNotFoundException.class, () -> userService.delete(id));
        assertTrue(e.getMessage().contains("User does not exist with id: " + id));
    }


    @Test
    void partiallyUpdateUserTest() {
        User user = modelUtils.user();
        userMap.put(1, user);
        userService.setUserMap(userMap);

        User updatedUser = new User();
        updatedUser.setEmail("updated@gmail.com");

        User result = userService.partiallyUpdate(1, updatedUser);

        assertEquals("updated@gmail.com", result.getEmail());
        assertEquals("test", result.getFirstName());
    }

    @Test
    void testPartiallyUpdateThrowsUserNotFoundException() {
        User update = new User();
        assertThrows(UserNotFoundException.class, () -> userService.partiallyUpdate(99, update));
    }

    @Test
    void searchUsersByDateTest() {
        User user1 = modelUtils.user();
        user1.setBirthDate(LocalDate.of(2000, 1, 1));

        User user2 = modelUtils.user();
        user2.setBirthDate(LocalDate.of(2002, 1, 1));

        User user3 = modelUtils.user();
        user3.setBirthDate(LocalDate.of(2005, 1, 1));

        userMap.put(1, user1);
        userMap.put(2, user2);
        userMap.put(3, user3);
        userService.setUserMap(userMap);

        List<User> results = userService.searchUsersByDate(
                LocalDate.of(2001, 1, 1),
                LocalDate.of(2006, 1, 1)
        );

        assertEquals(2, results.size());

        results = userService.searchUsersByDate(
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2006, 1, 1)
        );

        assertEquals(3, results.size());
    }

    @Test
    void testSearchUsersByDateThrowsIncorrectDateException() {
        assertThrows(IncorrectDateException.class,
                () -> userService.searchUsersByDate(LocalDate.of(2006, 1, 1), LocalDate.of(2005, 1, 1)));
    }
}
