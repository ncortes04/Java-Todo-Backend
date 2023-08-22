package com.example.jwt.api.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.jwt.api.controller.grouptodo.GroupTodoResponse;
import com.example.jwt.api.controller.todo.TodoRequest;
import com.example.jwt.api.controller.todo.TodoResponse;
import com.example.jwt.api.controller.todo.TodoService;
import com.example.jwt.api.controller.utils.ErrorMessage;
import com.example.jwt.config.JwtService;

import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = { "http://localhost:4200" }, allowedHeaders = "*")
public class UserController {

    private final UserService userService;

    @PostMapping("/getme")
    public ResponseEntity<Object> getUser() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Either<UserResponse, ErrorMessage> eitherResponse = userService.getUserByEmail(userId);

        if (eitherResponse.isLeft()) {
            UserResponse response = eitherResponse.getLeft();
            return ResponseEntity.ok(response);
        } else {
            ErrorMessage errorMessage = eitherResponse.get();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
