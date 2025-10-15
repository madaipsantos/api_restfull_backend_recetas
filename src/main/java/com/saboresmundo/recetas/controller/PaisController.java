package com.saboresmundo.recetas.controller;

import com.saboresmundo.recetas.model.Pais;
import com.saboresmundo.recetas.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/paises")
public class PaisController {

    @Autowired
    private PaisService paisService;

    @GetMapping
    public ResponseEntity<List<Pais>> listarPaises() {
        List<Pais> paises = paisService.listarPaises();
        return ResponseEntity.ok(paises);
    }

    @PostMapping
    public ResponseEntity<Pais> crearPais(@RequestBody Pais pais) {
        Pais nuevo = paisService.crearPais(pais);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pais> consultarPais(@PathVariable Long id) {
        return paisService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
