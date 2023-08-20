package com.example.jwt.api.controller.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoStatusUpdateRequest {
    private String todoId;
    private String status;
}
