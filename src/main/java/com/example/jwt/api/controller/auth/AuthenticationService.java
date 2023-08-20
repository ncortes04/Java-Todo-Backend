package com.example.jwt.api.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
            .token(jwtService.generateToken(user))
            .build();
        }
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .message("Success")
            .token(jwtToken)
            .build();
        
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
       authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(), 
            request.getPassword()
        )
       );
       var user = repository.findByEmail(request.getEmail())
            .orElseThrow();
         var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .message("Success")
            .token(jwtToken)
            .build();
    }
    
}
