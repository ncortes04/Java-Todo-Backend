package com.example.jwt.api.model;

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

    @DBRef //reference a User
    private User user; // Reference to the User who created the group
}