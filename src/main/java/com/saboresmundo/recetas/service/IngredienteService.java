package com.saboresmundo.recetas.service;

import com.saboresmundo.recetas.model.Ingrediente;
import com.saboresmundo.recetas.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    public List<Ingrediente> listarTodos() {
        return ingredienteRepository.findAll();
    }

    public Ingrediente crearIngrediente(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }
}
