package com.saboresmundo.recetas.service;

import com.saboresmundo.recetas.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    // Simulación de acceso a datos. Reemplazar por repositorio real.
    public Usuario findById(Long id) {
        // TODO: Implementar acceso a base de datos
        return null;
    }

    public Usuario getAuthenticatedUser() {
        // TODO: Implementar obtención de usuario autenticado (Spring Security)
        return null;
    }
}
