package com.example.jwt.api.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@Document(collection = "todos")
public class Todo {
    @Id
    private String id;
    private String task;
    private String status;

    @DBRef // Reference to a group
    private GroupTodo group;

    @DBRef // Reference to a user
    private User user;

    @CreatedDate // Annotation for creation date
    private Date createdDate;

    @LastModifiedDate // Annotation for last modified date
    private Date lastModifiedDate;
}
