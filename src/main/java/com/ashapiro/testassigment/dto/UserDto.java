package com.ashapiro.testassigment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@ToString
@NoArgsConstructor
public class UserDto {

    @NotNull(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Email must be in a valid format")
    private String email;

    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Birthday is required")
    private LocalDate birthDate;

    private String address;

    private String phoneNumber;
}
