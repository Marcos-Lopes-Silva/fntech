package com.fntech.auth_service.services;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fntech.auth_service.dto.UserEditRequest;
import com.fntech.auth_service.dto.UserRequest;
import com.fntech.auth_service.dto.UserResponse;
import com.fntech.auth_service.models.Role;
import com.fntech.auth_service.models.User;
import com.fntech.auth_service.repositories.UserRepository;

@Service 
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .role(user.getRole())
                        .build())
                .toList();
    }

    public UserResponse registerUser(UserRequest userRequest) {

        User user = User.builder()
            .email(userRequest.email())
            .password(passwordEncoder.encode(userRequest.password()))
            .name(userRequest.name())
            .role(userRequest.role()).build();

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
            .id(savedUser.getId())
            .email(savedUser.getEmail())
            .name(savedUser.getName())
            .role(savedUser.getRole())
            .build();
    }

    public UserResponse editUser(UUID id, UserEditRequest userEditRequest) {
        
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (userEditRequest.email() != null) {
            user.setEmail(userEditRequest.email());
        } 
        
        if (userEditRequest.name() != null) {
            user.setName(userEditRequest.name());
        }

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .role(savedUser.getRole())
                .build();
    }

    public UserResponse getUser(UUID id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    public UserResponse editUserRole(UUID id, Role role) {

        User user = this.userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        
        user.setRole(role);

        User savedUser = this.userRepository.save(user);

        return UserResponse.builder()
            .id(savedUser.getId())
            .email(savedUser.getEmail())
            .name(savedUser.getName())
            .role(savedUser.getRole())
            .build();
    }

    public UserResponse editUserPassword(UUID id, String password) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        user.setPassword(passwordEncoder.encode(password));

        User savedUser = this.userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .role(savedUser.getRole())
                .build();
    }
}
