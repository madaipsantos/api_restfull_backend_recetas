package com.saboresmundo.recetas.service;

import com.saboresmundo.recetas.model.Pais;
import com.saboresmundo.recetas.repository.PaisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class PaisServiceTest {

    @Mock
    private PaisRepository paisRepository;

    @InjectMocks
    private PaisService paisService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void debeCrearPaisConExito() {
        // Arrange
        Pais pais = new Pais();
        pais.setNombre("España");
        
        when(paisRepository.save(any(Pais.class))).thenAnswer(invocation -> {
            Pais p = invocation.getArgument(0);
            try {
                java.lang.reflect.Field field = Pais.class.getDeclaredField("id");
                field.setAccessible(true);
                field.set(p, 1L);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return p;
        });

        // Act
        Pais guardado = paisService.crearPais(pais);

        // Assert
        assertNotNull(guardado);
        assertNotNull(guardado.getId());
        assertEquals("España", guardado.getNombre());
        verify(paisRepository).save(pais);
    }

    @Test
    void debeListarTodosLosPaises() {
        // Arrange
        List<Pais> paises = Arrays.asList(
            crearPais(1L, "España"),
            crearPais(2L, "México"),
            crearPais(3L, "Argentina")
        );
        when(paisRepository.findAll()).thenReturn(paises);

        // Act
        List<Pais> resultado = paisService.listarPaises();

        // Assert
        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        assertEquals("España", resultado.get(0).getNombre());
        assertEquals("México", resultado.get(1).getNombre());
        assertEquals("Argentina", resultado.get(2).getNombre());
        verify(paisRepository).findAll();
    }

    @Test
    void debeRetornarListaVaciaCuandoNoHayPaises() {
        // Arrange
        when(paisRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Pais> resultado = paisService.listarPaises();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(paisRepository).findAll();
    }

    @Test
    void debeBuscarPaisPorIdExistente() {
        // Arrange
        Long id = 1L;
        Pais pais = crearPais(id, "España");
        when(paisRepository.findById(id)).thenReturn(Optional.of(pais));

        // Act
        Optional<Pais> resultado = paisService.buscarPorId(id);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("España", resultado.get().getNombre());
        assertEquals(id, resultado.get().getId());
        verify(paisRepository).findById(id);
    }

    @Test
    void debeRetornarOptionalVacioCuandoPaisNoExiste() {
        // Arrange
        Long id = 999L;
        when(paisRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Pais> resultado = paisService.buscarPorId(id);

        // Assert
        assertFalse(resultado.isPresent());
        verify(paisRepository).findById(id);
    }

    // Método auxiliar para crear países de prueba
    private Pais crearPais(Long id, String nombre) {
        Pais pais = new Pais();
        pais.setNombre(nombre);
        try {
            java.lang.reflect.Field field = Pais.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(pais, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pais;
    }
}
