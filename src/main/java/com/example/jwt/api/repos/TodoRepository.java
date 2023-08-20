package com.example.jwt.api.repos;

import org.springframework.stereotype.Repository;

import com.example.jwt.api.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;


@Repository
public interface TodoRepository extends  MongoRepository<Todo, String>{
    
}
