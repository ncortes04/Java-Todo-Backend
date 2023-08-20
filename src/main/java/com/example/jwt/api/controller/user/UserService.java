package com.example.jwt.api.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwt.api.controller.user.DTO.GroupTodoDTO;
import com.example.jwt.api.controller.user.DTO.TodoDTO;
import com.example.jwt.api.controller.utils.ErrorMessage;
import com.example.jwt.api.model.Todo;
import com.example.jwt.api.model.User;
import com.example.jwt.api.repos.GroupTodoRepository;
import com.example.jwt.api.repos.TodoRepository;
import com.example.jwt.api.repos.UserRepository;
import com.example.jwt.api.model.GroupTodo;
import com.example.jwt.api.model.Todo;

import lombok.RequiredArgsConstructor;
import io.vavr.control.Either;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GroupTodoRepository groupTodoRepository;
    private final TodoRepository todoRepository;

    public Either<UserResponse, ErrorMessage> getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("User Not Found");
            errorMessage.setStatusCode("404");
            return Either.right(errorMessage);
        } else {
            List<GroupTodo> userGroups = groupTodoRepository.findByUser(user);
            List<Todo> userTodos = new ArrayList<>();

            for (GroupTodo groupTodo : userGroups) {
                userTodos.addAll(todoRepository.findByGroup(groupTodo));
            }

            UserResponse userResponse = new UserResponse();
            userResponse.setName(user.getFirstname());
            userResponse.setEmail(user.getEmail());

            // Convert todos and groupTodos to DTOs
            List<TodoDTO> todoDTOs = userTodos.stream()
                    .map(this::convertTodoToDTO)
                    .collect(Collectors.toList());
            userResponse.setTodos(todoDTOs);

            List<GroupTodoDTO> groupTodoDTOs = userGroups.stream()
                    .map(this::convertGroupTodoToDTO)
                    .collect(Collectors.toList());
            userResponse.setGroupTodos(groupTodoDTOs);

            return Either.left(userResponse);
        }
    }

    private TodoDTO convertTodoToDTO(Todo todo) {
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setId(todo.getId());
        todoDTO.setTask(todo.getTask());
        todoDTO.setStatus(todo.getStatus());
        todoDTO.setGroup(convertGroupTodoToDTO(todo.getGroup()));
        return todoDTO;
    }

    private GroupTodoDTO convertGroupTodoToDTO(GroupTodo groupTodo) {
        GroupTodoDTO groupTodoDTO = new GroupTodoDTO();
        groupTodoDTO.setId(groupTodo.getId());
        groupTodoDTO.setGroupname(groupTodo.getGroupname());
        return groupTodoDTO;
    }
}