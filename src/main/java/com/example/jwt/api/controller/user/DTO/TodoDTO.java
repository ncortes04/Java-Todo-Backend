package com.example.jwt.api.controller.user.DTO;

import lombok.Data;

@Data
public class TodoDTO {
    private String id;
    private String task;
    private String status;
    private GroupTodoDTO group; // You can create a GroupTodoDTO similarly
}
