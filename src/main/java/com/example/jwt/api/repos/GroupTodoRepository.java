package com.example.jwt.api.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.jwt.api.model.GroupTodo;
import com.example.jwt.api.model.Todo;
import com.example.jwt.api.model.User;

@Repository
public interface GroupTodoRepository extends MongoRepository<GroupTodo, String> {
    List<GroupTodo> findByUser(User user);

    boolean existsByUserAndGroupname(User user, String groupname);
}
