package com.saboresmundo.recetas.controller;

import com.saboresmundo.recetas.model.Ingrediente;
import com.saboresmundo.recetas.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping
    public ResponseEntity<List<Ingrediente>> listarIngredientes() {
        List<Ingrediente> ingredientes = ingredienteService.listarTodos();
        return ResponseEntity.ok(ingredientes);
    }

    @PostMapping
    public ResponseEntity<Ingrediente> crearIngrediente(@RequestBody Ingrediente ingrediente) {
        Ingrediente nuevo = ingredienteService.crearIngrediente(ingrediente);
        return ResponseEntity.ok(nuevo);
    }
}
