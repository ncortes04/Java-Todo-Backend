package com.example.jwt.api.controller.auth;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.jwt.api.model.Role;
import com.example.jwt.api.model.User;

import com.example.jwt.api.repos.UserRepository;
import com.example.jwt.config.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository repository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (request.getFirstname() == "" || request.getLastname() == "" ||
                request.getEmail() == "" || request.getPassword() == "") {
            return AuthenticationResponse.builder()
                    .message("All fields are required")
                    .statusCode(400)
                    .build();
        }

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        if (repository.existsByEmail(request.getEmail())) {
            return AuthenticationResponse.builder()
                    .message("User Exists")
                    .statusCode(400)
                    .build();
        }

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .message("Success")
                .statusCode(200)
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (request.getEmail() == "" || request.getPassword() == "") {
            return AuthenticationResponse.builder()
                    .message("All fields are required")
                    .statusCode(400)
                    .build();
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword(),
                            new ArrayList<>()));

            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow();

            var jwtToken = jwtService.generateToken(user);

            return AuthenticationResponse.builder()
                    .message("Success")
                    .statusCode(200)
                    .token(jwtToken)
                    .build();
        } catch (AuthenticationException e) {
            // Handle failed authentication here
            return AuthenticationResponse.builder()
                    .message("Invalid username or password")
                    .statusCode(401) // Unauthorized status code
                    .token(null)
                    .build();
        }
    }

}