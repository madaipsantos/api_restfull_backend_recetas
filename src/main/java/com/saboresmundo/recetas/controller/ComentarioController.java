package com.saboresmundo.recetas.controller;

import com.saboresmundo.recetas.model.ComentarioReceta;
import com.saboresmundo.recetas.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recetas/{id}/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping
    public ResponseEntity<ComentarioReceta> agregarComentario(@PathVariable Long id,
            @RequestBody ComentarioReceta comentario) {
        // Agregar comentario a receta
        ComentarioReceta nuevo = comentarioService.agregarComentario(id, comentario);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping
    public ResponseEntity<List<ComentarioReceta>> verComentarios(@PathVariable Long id) {
        List<ComentarioReceta> comentarios = comentarioService.verComentarios(id);
        return ResponseEntity.ok(comentarios);
    }
}
