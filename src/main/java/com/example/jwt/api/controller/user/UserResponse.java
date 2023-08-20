package com.example.jwt.api.controller.user;

import java.util.List;

import com.example.jwt.api.controller.user.DTO.GroupTodoDTO;
import com.example.jwt.api.controller.user.DTO.TodoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String message;
    private String name;
    private String email;
    private List<TodoDTO> todos;
    private List<GroupTodoDTO> groupTodos;

}
