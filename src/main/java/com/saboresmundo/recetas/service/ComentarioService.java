package com.saboresmundo.recetas.service;

import com.saboresmundo.recetas.model.ComentarioReceta;
import com.saboresmundo.recetas.model.Receta;
import com.saboresmundo.recetas.repository.ComentarioRecetaRepository;
import com.saboresmundo.recetas.repository.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRecetaRepository comentarioRecetaRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    public ComentarioReceta agregarComentario(Long recetaId, ComentarioReceta comentario) {
        Optional<Receta> recetaOpt = recetaRepository.findById(recetaId);
        if (recetaOpt.isEmpty()) {
            throw new RuntimeException("Receta no encontrada");
        }
        Receta receta = recetaOpt.get();
        // Aquí deberías obtener el usuario autenticado, por ejemplo usando
        // SecurityContextHolder
        // Para ejemplo, se asume que el comentario ya tiene el usuario seteado
        comentario.setReceta(receta);
        comentario.setFecha(LocalDateTime.now());
        return comentarioRecetaRepository.save(comentario);
    }

    public List<ComentarioReceta> verComentarios(Long recetaId) {
        Optional<Receta> recetaOpt = recetaRepository.findById(recetaId);
        if (recetaOpt.isEmpty()) {
            throw new RuntimeException("Receta no encontrada");
        }
        Receta receta = recetaOpt.get();
        return comentarioRecetaRepository.findByReceta(receta);
    }
}
