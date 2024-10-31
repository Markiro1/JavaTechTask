package com.ashapiro.testassigment.util;

import com.ashapiro.testassigment.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String jwt = extractJwt(authHeader, response);
        if (jwt == null) return;

        String email = extractEmailFromJwt(jwt, response);
        if (email == null) return;

        authenticateUser(email, response);

        filterChain.doFilter(request, response);
    }

    private String extractJwt(String authHeader, HttpServletResponse response) throws IOException {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authorization token required");
            return null;
        }
        return authHeader.substring(7);
    }

    private String extractEmailFromJwt(String jwt, HttpServletResponse response) throws IOException {
        try {
            return jwtTokenUtils.getEmail(jwt);
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid JWT signature");
            return null;
        }
    }

    private void authenticateUser(String email, HttpServletResponse response) throws IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            if (userRepository.existsByEmail(email)) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        null
                );
                SecurityContextHolder.getContext().setAuthentication(token);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token is no longer valid");
            }
        }
    }
}
