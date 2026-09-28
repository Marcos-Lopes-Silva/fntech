package com.fntech.auth_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fntech.auth_service.models.Role;
import com.fntech.auth_service.models.User;
import com.fntech.auth_service.repositories.UserRepository;

@Configuration 
public class DataInitializer {
    
    @Bean 
    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.existsByEmail("admin@fntech.com")) {
                return;
            }

            User admin = User.builder()
                .email("admin@fntech.com")
                .name("ADMIN")
                .role(Role.ROLE_GESTOR)
                .password(passwordEncoder.encode("admin"))
                .build();

            userRepository.save(admin);
        };
    }
}
