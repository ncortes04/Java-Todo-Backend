package com.example.jwt.api.repos;

import org.springframework.stereotype.Repository;

import com.example.jwt.api.model.GroupTodo;
import com.example.jwt.api.model.Todo;
import com.example.jwt.api.model.User;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {
    List<Todo> findByUser(User user);

    List<Todo> findByGroup(GroupTodo groupname);
}
