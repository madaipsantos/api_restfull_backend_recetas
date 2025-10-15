package com.saboresmundo.recetas.controller;

import com.saboresmundo.recetas.dto.RecetaListItemResponse;
import com.saboresmundo.recetas.dto.RecetaRequest;
import com.saboresmundo.recetas.dto.RecetaResponse;
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
}
