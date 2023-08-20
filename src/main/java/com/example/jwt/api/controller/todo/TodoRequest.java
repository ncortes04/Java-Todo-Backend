package com.example.jwt.api.controller.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoRequest {
    private String task;
    private String status;
    private String groupId; // This field holds the ID of the associated group
}
