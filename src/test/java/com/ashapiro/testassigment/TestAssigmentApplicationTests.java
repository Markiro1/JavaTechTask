package com.ashapiro.testassigment;

import com.ashapiro.testassigment.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@AutoConfigureMockMvc
class TestAssigmentApplicationTests {

  /*  @Test
    public void createUserTest() throws Exception {
        UserDto userDto = new UserDto("1@gmail.com", "testname", "test", LocalDate.of(2000, 1, 1), null, null);

        String userJson = """
            {
                "email": "1@gmail.com",
                "firstName": "testname",
                "lastName": "test",
                "birthDate": "2000-01-01"
            }
        """;

        mockMvc.perform(post("/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void createUserInvalidAgeTest() throws Exception {
        String userJson = """
            {
                "email": "1@example.com",
                "firstName": "testname",
                "lastName": "test",
                "birthDate": "2010-01-01"
            }
        """;

        mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User must be at least 18 years old"));
    }*/
}
