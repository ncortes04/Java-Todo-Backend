package com.example.jwt.api.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(collection = "group")
public class GroupTodo {
    @Id
    private String id;

    private String groupname; // Corrected field name
    @DBRef
    private List<Todo> todos;

    @DBRef // reference a User
    private User user; // Reference to the User who created the group
}