package com.saboresmundo.recetas.service;

import com.saboresmundo.recetas.model.Pais;
import com.saboresmundo.recetas.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaisService {

    @Autowired
    private PaisRepository paisRepository;

    public List<Pais> listarPaises() {
        return paisRepository.findAll();
    }

    public Optional<Pais> buscarPorId(Long id) {
        return paisRepository.findById(id);
    }
}
