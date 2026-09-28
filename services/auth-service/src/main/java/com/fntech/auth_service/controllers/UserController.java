package com.fntech.auth_service.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fntech.auth_service.dto.UserEditRequest;
import com.fntech.auth_service.dto.UserRequest;
import com.fntech.auth_service.dto.UserResponse;
import com.fntech.auth_service.services.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/users")
@RestController 
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> userResponse = this.userService.findAllUsers();
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = this.userService.registerUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@Valid @RequestParam UUID id) {
        UserResponse userResponse = this.userService.getUser(id);
        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> editUser(@PathVariable UUID id, @Valid @RequestBody UserEditRequest userEditRequest) {
        UserResponse userResponse = this.userService.editUser(id, userEditRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<UserResponse> editUserRole(@PathVariable UUID id, @Valid @RequestBody UserEditRequest userEditRequest) {
        UserResponse userResponse = this.userService.editUserRole(id, userEditRequest.role());
        return ResponseEntity.ok(userResponse);
    }
    
}
