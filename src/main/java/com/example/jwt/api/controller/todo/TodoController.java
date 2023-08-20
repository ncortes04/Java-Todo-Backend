package com.example.jwt.api.controller.todo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todo")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/create")
    public ResponseEntity<TodoResponse> createTodo(
            @RequestBody TodoRequest request
    ) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        TodoResponse response = todoService.createTodo(request, userEmail);

        return ResponseEntity.ok(response);
    }
    @PatchMapping("/update-status")
    public ResponseEntity<TodoResponse> updateTodoStatus(
            @RequestBody TodoStatusUpdateRequest request
    ) {
        TodoResponse response = todoService.updateTodoStatus(request);

        return ResponseEntity.ok(response);
    }
}
