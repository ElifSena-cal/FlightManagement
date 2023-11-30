package com.project.flightmanagement.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.flightmanagement.entity.User;
import com.project.flightmanagement.request.AuthRequest;
import com.project.flightmanagement.request.UserRequest;
import com.project.flightmanagement.response.AuthResponse;
import com.project.flightmanagement.service.KeyCloakService;
import com.project.flightmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final KeyCloakService keyCloakService;

    @PostMapping("/login")
    public AuthResponse generateJwtToken(@RequestBody AuthRequest userDto) throws Exception {
        User user = userService.getOneUserByUserName(userDto.getUserName());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(keyCloakService.getToken("login-app",userDto.getUserName(),userDto.getPassword()));
        authResponse.setRole(user.getRole());
        authResponse.setUserName(user.getUserName());
        return authResponse;
    }

    @PostMapping("/register")
    public AuthResponse registerUser(@RequestBody UserRequest registerRequest) throws JsonProcessingException {
        userService.createUser(registerRequest);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(keyCloakService.getToken("login-app",registerRequest.getUserName(),registerRequest.getPassword()));
        authResponse.setRole(registerRequest.getRole());
        authResponse.setUserName(registerRequest.getUserName());
        return authResponse;
    }



}
