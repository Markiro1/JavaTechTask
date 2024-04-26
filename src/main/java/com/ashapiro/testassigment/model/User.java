package com.ashapiro.testassigment.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private int id;

    @NotNull(message = "Email is required")
    @Pattern(regexp = "^[0-9]+$",
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
