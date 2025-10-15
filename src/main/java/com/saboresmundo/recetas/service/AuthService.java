package com.saboresmundo.recetas.service;

import com.saboresmundo.recetas.model.Usuario;
import com.saboresmundo.recetas.repository.UsuarioRepository;
//import com.saboresmundo.recetas.dto.RegisterRequest;
import com.saboresmundo.recetas.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public static class LoginRequest {
        private String email;
        private String password;

        public LoginRequest() {
        }

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public ResponseEntity<?> register(com.saboresmundo.recetas.dto.RegisterRequest request) {
        // Validación básica
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El email ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword())); // Hashear la contraseña
        usuario.setRol(request.getRol());
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario registrado");
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail()).orElse(null);
        if (usuario == null || !passwordEncoder.matches(loginRequest.getPassword(), usuario.getPasswordHash())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
        String token = jwtUtil.generateToken(usuario.getEmail());
        // Devuelve el token en un objeto JSON
        return ResponseEntity.ok(java.util.Collections.singletonMap("token", token));
    }
}
