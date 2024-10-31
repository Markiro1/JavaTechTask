package com.ashapiro.testassigment.service.impl;

import com.ashapiro.testassigment.dto.user.UserAuthDTO;
import com.ashapiro.testassigment.exception.IncorrectCredentialsException;
import com.ashapiro.testassigment.service.AuthService;
import com.ashapiro.testassigment.service.UserService;
import com.ashapiro.testassigment.util.JwtTokenUtils;
import com.ashapiro.testassigment.util.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final JwtTokenUtils jwtTokenUtils;

    private final AuthenticationManager authenticationManager;


    @Override
    public void registerUser(UserAuthDTO userDTO) {
        userService.registration(userDTO);
    }

    @Override
    public String authenticateUser(UserAuthDTO userDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtTokenUtils.generateToken((UserDetailsImpl) userDetails);
            return token;
        } catch (BadCredentialsException e) {
            throw new IncorrectCredentialsException();
        }
    }

}
