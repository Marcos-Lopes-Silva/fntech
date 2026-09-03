package com.fntech.auth_service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fntech.auth_service.dto.AuthenticationRequest;
import com.fntech.auth_service.dto.AuthenticationResponse;
import com.fntech.auth_service.services.AuthenticationService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RequestMapping("/auth")
@Controller
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
