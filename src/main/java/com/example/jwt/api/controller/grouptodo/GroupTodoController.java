package com.example.jwt.api.controller.grouptodo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupTodoController {
    
    private final GroupTodoService groupTodoService;

    @PostMapping("/create")
    public ResponseEntity<GroupTodoResponse> createGroup(
        @RequestBody GroupTodoRequest request
    ) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        
        GroupTodoResponse response = groupTodoService.createGroup(request, userEmail);
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<GroupTodoResponse> deleteGroup(
        @RequestBody DeleteGroupRequest request
    ) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        
        GroupTodoResponse response = groupTodoService.deleteGroup(request.getGroupId(), userEmail);
        
        return ResponseEntity.ok(response);
    }
}
