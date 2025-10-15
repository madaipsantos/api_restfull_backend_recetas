package com.saboresmundo.recetas.service;

import com.saboresmundo.recetas.model.ComentarioReceta;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComentarioService {
    // Simulación de acceso a datos. Reemplazar por repositorio real.
    public ComentarioReceta agregarComentario(Long recetaId, ComentarioReceta comentario) {
        // TODO: Implementar persistencia y relación con receta
        return comentario;
    }

    public List<ComentarioReceta> verComentarios(Long recetaId) {
        // TODO: Implementar consulta de comentarios por receta
        return new ArrayList<>();
    }
}
