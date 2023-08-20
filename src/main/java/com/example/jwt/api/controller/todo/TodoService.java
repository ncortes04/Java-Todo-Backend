package com.example.jwt.api.controller.todo;

import org.springframework.stereotype.Service;
import com.example.jwt.api.model.GroupTodo;
import com.example.jwt.api.model.Todo;
import com.example.jwt.api.model.User;
import com.example.jwt.api.repos.GroupTodoRepository;
import com.example.jwt.api.repos.TodoRepository;
import com.example.jwt.api.repos.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final GroupTodoRepository groupTodoRepository;
    private final UserRepository userRepository;

    public TodoResponse createTodo(TodoRequest request, String userEmail) {
        // Fetch the user entity based on the provided userEmail
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the group entity based on the provided groupId
        GroupTodo groupTodo = groupTodoRepository.findById(request.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        // Create a new Todo entity and associate it with the user and group
        Todo todo = new Todo();
        todo.setTask(request.getTask());
        todo.setStatus(request.getStatus());
        todo.setUser(user);
        todo.setGroup(groupTodo);

        // Save the todo entity to the database
        todoRepository.save(todo);

        return TodoResponse.builder()
                .message("Success")
                .statusCode(200)
                .build();
    }
    public TodoResponse updateTodoStatus(TodoStatusUpdateRequest request) {
        Todo todo = todoRepository.findById(request.getTodoId())
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        todo.setStatus(request.getStatus());

        todoRepository.save(todo);

        return TodoResponse.builder()
                .message("Todo status updated successfully")
                .statusCode(200)
                .build();
    }
}
