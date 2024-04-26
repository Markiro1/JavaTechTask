package com.ashapiro.testassigment;

import com.ashapiro.modelUtils.ModelUtils;
import com.ashapiro.testassigment.controller.UserController;
import com.ashapiro.testassigment.dto.UserDto;
import com.ashapiro.testassigment.model.User;
import com.ashapiro.testassigment.service.UserService;
import com.ashapiro.testassigment.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    private ModelUtils modelUtils = new ModelUtils();

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void addUserTest() throws Exception {
        UserDto userDto = modelUtils.userDto();
        User user = modelUtils.user();

        when(userService.add(any(UserDto.class))).thenReturn(user);

        ResponseEntity<User> response = userController.addUser(userDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());

        String userJson = """
                    {
                        "email": "1@gmail.com",
                        "firstName": "test",
                        "lastName": "test",
                        "birthDate": "1990-01-01"
                    }
                """;

        mockMvc.perform(post("/users/add")
                        .contentType("application/json")
                        .content(userJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateUser() throws Exception {
        UserDto userDto = modelUtils.updateDto();
        User user = modelUtils.updateUser();

        when(userService.update(1, userDto)).thenReturn(user);

        ResponseEntity<User> response = userController.updateUser(1, userDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());

        String updatedUserJson = """
                    {
                        "email": "update@gmail.com",
                        "firstName": "update first",
                        "lastName": "update last",
                        "birthDate": "1990-01-01"
                    }
                """;

        mockMvc.perform(put("/users/update/1")
                        .contentType("application/json")
                        .content(updatedUserJson))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).delete(1);

        ResponseEntity<?> response = userController.deleteUser(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        mockMvc.perform(delete("/users/delete/1"))
                .andExpect(status().isOk());

        verify(userService, times(2)).delete(1);
    }

    @Test
    void testSearchUsersByDate() throws Exception {
        User user = modelUtils.user();
        List<User> users = List.of(user);

        when(userService.searchUsersByDate(LocalDate.of(1999, 1, 1), LocalDate.of(2002, 1, 1)))
                .thenReturn(users);

        ResponseEntity<List<User>> response = userController.searchUsersByDate(
                LocalDate.of(1999, 1, 1),
                LocalDate.of(2002, 1, 1)
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());

        mockMvc.perform(get("/users/search-by-date")
                        .param("from", "1999-01-01")
                        .param("to", "2002-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}