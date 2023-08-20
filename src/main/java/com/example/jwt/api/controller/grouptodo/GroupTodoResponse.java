package com.example.jwt.api.controller.grouptodo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupTodoResponse {
    private String message;
    private int statusCode;
}
