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
}
