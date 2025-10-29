package com.saboresmundo.recetas.controller;

import com.saboresmundo.recetas.dto.RecetaListItemResponse;
import com.saboresmundo.recetas.dto.RecetaRequest;
import com.saboresmundo.recetas.dto.RecetaResponse;
import com.saboresmundo.recetas.dto.ComentarioRequest;
import com.saboresmundo.recetas.model.ComentarioReceta;
import com.saboresmundo.recetas.model.Usuario;
import com.saboresmundo.recetas.repository.ComentarioRecetaRepository;
import com.saboresmundo.recetas.repository.RecetaRepository;
import com.saboresmundo.recetas.repository.UsuarioRepository;
import com.saboresmundo.recetas.model.Receta;
import com.saboresmundo.recetas.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {
    private static final Logger logger = LoggerFactory.getLogger(RecetaController.class);

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private ComentarioRecetaRepository comentarioRecetaRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private com.saboresmundo.recetas.service.ComentarioService comentarioService;

    @PutMapping("/{recetaId}/comentarios/{comentarioId}")
    public ResponseEntity<?> editarComentario(@PathVariable Long recetaId,
            @PathVariable Long comentarioId,
            @RequestBody ComentarioRequest comentarioRequest) {
        try {
            // Verificar que la receta existe
            var recetaOpt = recetaRepository.findById(recetaId);
            if (recetaOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "Receta no encontrada"));
            }

            // Verificar que el comentario pertenece a la receta
            var comentarioOpt = comentarioRecetaRepository.findById(comentarioId);
            if (comentarioOpt.isEmpty() || !comentarioOpt.get().getReceta().getId().equals(recetaId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "Comentario no encontrado para esta receta"));
            }

            // Editar el comentario
            ComentarioReceta actualizado = comentarioService.editarComentario(
                    comentarioId,
                    comentarioRequest.getComentario(),
                    comentarioRequest.getValoracion());

            // Actualizar el promedio de valoraciones de la receta
            var comentarios = comentarioRecetaRepository.findByReceta(recetaOpt.get());
            double promedio = comentarios.stream()
                    .filter(c -> c.getValoracion() != null)
                    .mapToInt(ComentarioReceta::getValoracion)
                    .average()
                    .orElse(0.0);
            recetaOpt.get().setValoracion((float) promedio);
            recetaRepository.save(recetaOpt.get());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error al editar comentario", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error al editar comentario: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> listarRecetas(@RequestParam(required = false) String filtro) {
        try {
            logger.info("Listando recetas con filtro: {}", filtro);
            List<Receta> recetas = recetaService.listarRecetas(filtro);
            List<RecetaListItemResponse> response = RecetaListItemResponse.fromRecetas(recetas);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al listar recetas", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error al listar recetas: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id) {
        try {
            logger.info("Buscando receta por ID: {}", id);
            Receta receta = recetaService.findById(id);

            if (receta == null) {
                logger.warn("Receta no encontrada con ID: {}", id);
                return ResponseEntity.notFound().build();
            }

            RecetaResponse response = new RecetaResponse(receta);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al obtener detalle de receta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error al obtener la receta: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<RecetaResponse> crearReceta(@RequestBody RecetaRequest recetaRequest) {
        logger.info("Creando nueva receta: {}", recetaRequest.getTitulo());
        RecetaResponse response = recetaService.crearReceta(recetaRequest);

        if (response.getId() != null) {
            logger.info("Receta creada exitosamente con ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            logger.error("Error al crear receta: {}", response.getMensaje());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaResponse> editarReceta(@PathVariable Long id,
            @RequestBody RecetaRequest recetaRequest) {
        logger.info("Editando receta con ID: {}", id);
        RecetaResponse response = recetaService.editarReceta(id, recetaRequest);

        if (response.getId() != null) {
            logger.info("Receta actualizada exitosamente con ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } else {
            logger.error("Error al editar receta: {}", response.getMensaje());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RecetaResponse> eliminarReceta(@PathVariable Long id) {
        logger.info("Eliminando receta con ID: {}", id);
        RecetaResponse response = recetaService.eliminarReceta(id);

        if ("Receta eliminada correctamente".equals(response.getMensaje())) {
            logger.info("Receta eliminada exitosamente con ID: {}", id);
            return ResponseEntity.ok(response);
        } else {
            logger.error("Error al eliminar receta: {}", response.getMensaje());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerRecetasPorUsuario(@PathVariable Long usuarioId) {
        try {
            logger.info("Obteniendo recetas del usuario con ID: {}", usuarioId);
            List<RecetaResponse> recetas = recetaService.obtenerRecetasPorUsuario(usuarioId);
            return ResponseEntity.ok(recetas);
        } catch (Exception e) {
            logger.error("Error al obtener recetas del usuario", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error al obtener las recetas: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/comentarios")
    public ResponseEntity<?> agregarComentario(@PathVariable Long id,
            @RequestBody ComentarioRequest comentarioRequest) {
        try {
            logger.info("Agregando comentario a receta ID: {}", id);
            var recetaOpt = recetaRepository.findById(id);
            if (recetaOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "Receta no encontrada"));
            }
            Receta receta = recetaOpt.get();

            Usuario usuario = null;
            if (comentarioRequest.getUsuarioId() != null) {
                usuario = usuarioRepository.findById(comentarioRequest.getUsuarioId()).orElse(null);
            }

            ComentarioReceta comentario = new ComentarioReceta();
            comentario.setReceta(receta);
            comentario.setUsuario(usuario);
            comentario.setComentario(comentarioRequest.getComentario());
            comentario.setValoracion(comentarioRequest.getValoracion());
            comentario.setFecha(java.time.LocalDateTime.now());

            comentarioRecetaRepository.save(comentario);

            // Actualizar el promedio de valoraciones de la receta
            var comentarios = comentarioRecetaRepository.findByReceta(receta);
            double promedio = comentarios.stream()
                    .filter(c -> c.getValoracion() != null)
                    .mapToInt(ComentarioReceta::getValoracion)
                    .average()
                    .orElse(0.0);
            receta.setValoracion((float) promedio);
            recetaRepository.save(receta);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            logger.error("Error al agregar comentario", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error al agregar comentario: " + e.getMessage()));
        }
    }
}
