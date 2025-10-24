package com.saboresmundo.recetas.service;

import com.saboresmundo.recetas.model.Ingrediente;
import com.saboresmundo.recetas.repository.IngredienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class IngredienteServiceTest {

    @Mock
    private IngredienteRepository ingredienteRepository;

    @InjectMocks
    private IngredienteService ingredienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarIngredienteComSucesso() {
        Ingrediente ingrediente = new Ingrediente();
        when(ingredienteRepository.save(any(Ingrediente.class))).thenReturn(ingrediente);

        Ingrediente salvo = ingredienteService.crearIngrediente(ingrediente);

        assertNotNull(salvo);
        verify(ingredienteRepository, times(1)).save(ingrediente);
    }
    // Adicione outros testes conforme a lógica do service
}
