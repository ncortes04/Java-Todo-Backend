package com.example.jwt.api.controller.grouptodo;

import org.springframework.stereotype.Service;

import com.example.jwt.api.model.GroupTodo;
import com.example.jwt.api.model.User;
import com.example.jwt.api.repos.GroupTodoRepository;
import com.example.jwt.api.repos.UserRepository;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupTodoService {
    
    private final GroupTodoRepository groupTodoRepository;
    private final UserRepository userRepository;

    public GroupTodoResponse createGroup(GroupTodoRequest request, String userEmail) {
        // Check if groupname is null or empty
        if (StringUtils.isBlank(request.getGroupname())) {
            return GroupTodoResponse.builder()
                    .message("Groupname cannot be empty")
                    .statusCode(400)
                    .build();
        }
    
        // Fetch the user entity based on the provided userEmail
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        // Create a new GroupTodo entity and associate it with the user
        GroupTodo groupTodo = new GroupTodo();
        groupTodo.setGroupname(request.getGroupname());
        groupTodo.setUser(user);
        
        // Check if a group with the same name already exists for the user
        if (groupTodoRepository.existsByUserAndGroupname(user, request.getGroupname())) {
            return GroupTodoResponse.builder()
                    .message("Group with the same name already exists")
                    .statusCode(400)
                    .build();
        }
        
        // Save the groupTodo entity to the database
        groupTodoRepository.save(groupTodo);
    
        return GroupTodoResponse.builder()
                .message("Success")
                .statusCode(200)
                .build();
    }
    
    public GroupTodoResponse deleteGroup(String groupId, String userEmail) {
        // Fetch the user entity based on the provided userEmail
               // Check if groupname is null or empty
        if (groupId == null) {
            return GroupTodoResponse.builder()
                    .message("GroupId cannot be empty")
                    .statusCode(400)
                    .build();
        }
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the group entity based on the provided groupId
        GroupTodo groupTodo = groupTodoRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        // Check if the group belongs to the user
        if (!groupTodo.getUser().equals(user)) {
            return GroupTodoResponse.builder()
                    .message("Group does not belong to the user")
                    .statusCode(400)
                    .build();
        }

        // Delete the group
        groupTodoRepository.delete(groupTodo);

        return GroupTodoResponse.builder()
                .message("Group deleted successfully")
                .statusCode(200)
                .build();
    }
}

