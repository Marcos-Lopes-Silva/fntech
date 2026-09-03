package com.fntech.auth_service.dto;

import com.fntech.auth_service.models.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(@NotNull @NotBlank String name, @Email @NotBlank String email, @NotBlank @Min(5) String password, @NotNull Role role) {}
