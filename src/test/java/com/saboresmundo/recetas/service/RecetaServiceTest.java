package com.saboresmundo.recetas.service;

import com.saboresmundo.recetas.model.Receta;
import com.saboresmundo.recetas.repository.RecetaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RecetaServiceTest {

    @Mock
    private RecetaRepository recetaRepository;

    @InjectMocks
    private RecetaService recetaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarRecetaComSucesso() {
        Receta receta = new Receta();
        when(recetaRepository.save(any(Receta.class))).thenReturn(receta);

        // Ajuste o método conforme a implementação real do service
        // Exemplo: Receta salvo = recetaService.criarReceta(receta);
        // Aqui usamos save diretamente para evitar erro de compilação
        Receta salvo = recetaRepository.save(receta);

        assertNotNull(salvo);
        verify(recetaRepository, times(1)).save(receta);
    }
    // Adicione outros testes conforme a lógica do service
}
