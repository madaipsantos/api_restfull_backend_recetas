package com.saboresmundo.recetas.controller;

import com.saboresmundo.recetas.model.Usuario;
import com.saboresmundo.recetas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getPerfilPublico(@PathVariable Long id) {
        // Obtener perfil público del usuario
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> getUsuarioAutenticado() {
        // Obtener datos del usuario autenticado
        Usuario usuario = usuarioService.getAuthenticatedUser();
        if (usuario == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(usuario);
    }
}
