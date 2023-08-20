package com.example.jwt.api.controller.grouptodo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupTodoRequest {

    private String groupname;
    private String userEmail; // This field will hold the user's email
}
