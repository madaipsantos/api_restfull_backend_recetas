package com.saboresmundo.recetas.controller;

import com.saboresmundo.recetas.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody com.saboresmundo.recetas.dto.RegisterRequest request) {
        // Implementar lógica de registro y validación
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthService.LoginRequest loginRequest) {
        // Implementar lógica de login y generación de JWT
        return authService.login(loginRequest);
    }
}
