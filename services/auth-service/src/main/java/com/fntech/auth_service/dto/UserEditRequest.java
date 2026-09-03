package com.fntech.auth_service.dto;

import com.fntech.auth_service.models.Role;

public record UserEditRequest(String email, String name, String password, Role role) {
} 
