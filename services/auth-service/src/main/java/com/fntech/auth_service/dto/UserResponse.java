package com.fntech.auth_service.dto;


import java.util.UUID;

import com.fntech.auth_service.models.Role;

import lombok.Builder;

@Builder
public record UserResponse(UUID id, String name, String email, Role role) {
    
}
