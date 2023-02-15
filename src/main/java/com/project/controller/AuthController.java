package com.project.controller;

import com.project.dto.request.AuthRequest;
import com.project.dto.request.RegisterRequest;
import com.project.dto.response.AuthResponse;
import com.project.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    public final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request){
        return ResponseEntity.ok(service.authenticate(request));

    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        return ResponseEntity.ok(service.logout(request));
    }
}
