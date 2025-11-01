package com.saboresmundo.recetas.service;

import com.saboresmundo.recetas.model.ComentarioReceta;
import com.saboresmundo.recetas.model.Receta;
import com.saboresmundo.recetas.model.Usuario;
import com.saboresmundo.recetas.repository.ComentarioRecetaRepository;
import com.saboresmundo.recetas.repository.RecetaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class ComentarioServiceTest {

    @Mock
    private ComentarioRecetaRepository comentarioRecetaRepository;

    @Mock
    private RecetaRepository recetaRepository;

    @InjectMocks
    private ComentarioService comentarioService;

    private Usuario usuario;
    private Receta receta;
    private ComentarioReceta comentario;

    private void setIdUsingReflection(Object obj, Long id) {
        try {
            Field idField = obj.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(obj, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar usuario de prueba
        usuario = new Usuario();
        usuario.setNombre("Test User");
        setIdUsingReflection(usuario, 1L);

        // Configurar receta de prueba
        receta = new Receta();
        receta.setTitulo("Receta de prueba");
        receta.setUsuario(usuario);
        setIdUsingReflection(receta, 1L);

        // Configurar comentario de prueba
        comentario = new ComentarioReceta();
        comentario.setComentario("¡Gran receta!");
        comentario.setValoracion(5);
        comentario.setUsuario(usuario);
        comentario.setReceta(receta);
        comentario.setFecha(LocalDateTime.now());
        setIdUsingReflection(comentario, 1L);
    }

    @Test
    void debeAgregarComentarioConExito() {
        // Arrange
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));
        when(comentarioRecetaRepository.save(any(ComentarioReceta.class))).thenReturn(comentario);

        // Act
        ComentarioReceta resultado = comentarioService.agregarComentario(1L, comentario);

        // Assert
        assertNotNull(resultado);
        assertEquals("¡Gran receta!", resultado.getComentario());
        assertEquals(5, resultado.getValoracion());
        verify(comentarioRecetaRepository).save(any(ComentarioReceta.class));
    }

    @Test
    void debeRetornarErrorSiRecetaNoExiste() {
        // Arrange
        when(recetaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            comentarioService.agregarComentario(999L, comentario);
        });
        verify(comentarioRecetaRepository, never()).save(any());
    }

    @Test
    void debeVerComentariosPorReceta() {
        // Arrange
        ComentarioReceta comentario1 = new ComentarioReceta();
        comentario1.setComentario("Comentario 1");
        comentario1.setUsuario(usuario);
        comentario1.setReceta(receta);
        comentario1.setFecha(LocalDateTime.now());
        setIdUsingReflection(comentario1, 1L);

        ComentarioReceta comentario2 = new ComentarioReceta();
        comentario2.setComentario("Comentario 2");
        comentario2.setUsuario(usuario);
        comentario2.setReceta(receta);
        comentario2.setFecha(LocalDateTime.now());
        setIdUsingReflection(comentario2, 2L);

        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));
        when(comentarioRecetaRepository.findByReceta(receta))
            .thenReturn(Arrays.asList(comentario2, comentario1));

        // Act
        List<ComentarioReceta> comentarios = comentarioService.verComentarios(1L);

        // Assert
        assertNotNull(comentarios);
        assertEquals(2, comentarios.size());
        assertEquals("Comentario 2", comentarios.get(0).getComentario());
        assertEquals("Comentario 1", comentarios.get(1).getComentario());
    }

    @Test
    void debeRetornarErrorSiRecetaNoExisteAlVerComentarios() {
        // Arrange
        when(recetaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            comentarioService.verComentarios(999L);
        });
    }

    @Test
    void debeEditarComentarioConExito() {
        // Arrange
        when(comentarioRecetaRepository.findById(1L)).thenReturn(Optional.of(comentario));
        when(comentarioRecetaRepository.save(any(ComentarioReceta.class))).thenReturn(comentario);

        String nuevoComentario = "Comentario actualizado";
        Integer nuevaValoracion = 4;

        // Act
        ComentarioReceta resultado = comentarioService.editarComentario(1L, nuevoComentario, nuevaValoracion);

        // Assert
        assertNotNull(resultado);
        assertEquals(nuevoComentario, resultado.getComentario());
        assertEquals(nuevaValoracion, resultado.getValoracion());
        verify(comentarioRecetaRepository).save(comentario);
    }

    @Test
    void debeRetornarErrorSiComentarioNoExisteAlEditar() {
        // Arrange
        when(comentarioRecetaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            comentarioService.editarComentario(999L, "Nuevo comentario", 5);
        });
    }
}
