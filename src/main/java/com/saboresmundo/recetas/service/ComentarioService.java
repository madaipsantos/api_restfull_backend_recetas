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

    public ComentarioReceta editarComentario(Long comentarioId, String nuevoComentario, Integer nuevaValoracion) {
        Optional<ComentarioReceta> comentarioOpt = comentarioRecetaRepository.findById(comentarioId);
        if (comentarioOpt.isEmpty()) {
            throw new RuntimeException("Comentario no encontrado");
        }
        ComentarioReceta comentario = comentarioOpt.get();
        if (nuevoComentario != null) {
            comentario.setComentario(nuevoComentario);
        }
        if (nuevaValoracion != null) {
            comentario.setValoracion(nuevaValoracion);
        }
        // Puedes actualizar la fecha si lo deseas:
        // comentario.setFecha(LocalDateTime.now());
        return comentarioRecetaRepository.save(comentario);
    }

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
