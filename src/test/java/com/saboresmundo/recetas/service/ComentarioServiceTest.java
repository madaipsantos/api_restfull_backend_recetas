package com.saboresmundo.recetas.service;

import com.saboresmundo.recetas.model.ComentarioReceta;
import com.saboresmundo.recetas.repository.ComentarioRecetaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ComentarioServiceTest {


    @Mock
    private ComentarioRecetaRepository comentarioRecetaRepository;

    @Mock
    private com.saboresmundo.recetas.repository.RecetaRepository recetaRepository;

    @InjectMocks
    private ComentarioService comentarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarComentarioComSucesso() {
        Long recetaId = 1L;
        ComentarioReceta comentario = new ComentarioReceta();
        // Mock do repository para encontrar a receita
        com.saboresmundo.recetas.model.Receta receta = new com.saboresmundo.recetas.model.Receta();
        when(recetaRepository.findById(recetaId)).thenReturn(java.util.Optional.of(receta));
        when(comentarioRecetaRepository.save(any(ComentarioReceta.class))).thenReturn(comentario);

        ComentarioReceta salvo = comentarioService.agregarComentario(recetaId, comentario);

        assertNotNull(salvo);
        verify(comentarioRecetaRepository, times(1)).save(comentario);
    }
    // Adicione outros testes conforme a lógica do service
}
