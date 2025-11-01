package com.saboresmundo.recetas.service;
import com.saboresmundo.recetas.model.ComentarioReceta;
import com.saboresmundo.recetas.model.Receta;
import com.saboresmundo.recetas.model.Usuario;
import com.saboresmundo.recetas.repository.ComentarioRecetaRepository;
import com.saboresmundo.recetas.repository.RecetaRepository;
import com.saboresmundo.recetas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public ComentarioReceta editarComentario(Long comentarioId, String nuevoComentario, Integer nuevaValoracion) {
        ComentarioReceta comentario = comentarioRecetaRepository.findById(comentarioId)
            .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        if (!esUsuarioAutorizado(comentario)) {
            throw new RuntimeException("No tienes permiso para editar este comentario");
        }

        if (nuevoComentario != null) {
            comentario.setComentario(nuevoComentario);
        }
        if (nuevaValoracion != null) {
            if (nuevaValoracion < 1 || nuevaValoracion > 5) {
                throw new RuntimeException("La valoración debe estar entre 1 y 5");
            }
            comentario.setValoracion(nuevaValoracion);
        }
        comentario.setFecha(LocalDateTime.now());
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

    public void eliminarComentario(Long comentarioId) {
        ComentarioReceta comentario = comentarioRecetaRepository.findById(comentarioId)
            .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
        
        String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!comentario.getUsuario().getEmail().equals(usuarioActual)) {
            throw new RuntimeException("No tienes permiso para eliminar este comentario");
        }

        comentarioRecetaRepository.delete(comentario);
    }

    private boolean esUsuarioAutorizado(ComentarioReceta comentario) {
        String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
        return comentario.getUsuario().getEmail().equals(usuarioActual);
    }

    private Usuario obtenerUsuarioAutenticado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
