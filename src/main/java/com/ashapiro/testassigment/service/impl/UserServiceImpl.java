package com.ashapiro.testassigment.service.impl;

import com.ashapiro.testassigment.dto.user.UserAuthDTO;
import com.ashapiro.testassigment.exception.UserAlreadyExistException;
import com.ashapiro.testassigment.model.User;
import com.ashapiro.testassigment.repository.UserRepository;
import com.ashapiro.testassigment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User registration(UserAuthDTO userDTO) {
        validateEmail(userDTO.getEmail());
        User user = createUserByDto(userDTO);
        userRepository.save(user);
        return user;
    }

    private User createUserByDto(UserAuthDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setId(UUID.randomUUID());
        return user;
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistException(email);
        }
    }
}
