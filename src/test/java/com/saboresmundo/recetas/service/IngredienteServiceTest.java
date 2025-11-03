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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    void debeCrearIngredienteConExito() {
        // Arrange
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre("Sal");
        ingrediente.setDescripcion("Sal de mesa");
        
        when(ingredienteRepository.save(any(Ingrediente.class))).thenAnswer(invocation -> {
            Ingrediente i = invocation.getArgument(0);
            try {
                java.lang.reflect.Field field = Ingrediente.class.getDeclaredField("id");
                field.setAccessible(true);
                field.set(i, 1L);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return i;
        });

        // Act
        Ingrediente salvo = ingredienteService.crearIngrediente(ingrediente);

        // Assert
        assertNotNull(salvo);
        assertNotNull(salvo.getId());
        assertEquals("Sal", salvo.getNombre());
        assertEquals("Sal de mesa", salvo.getDescripcion());
        verify(ingredienteRepository).save(ingrediente);
    }

    @Test
    void debeListarTodosLosIngredientes() {
        // Arrange
        List<Ingrediente> ingredientes = Arrays.asList(
            crearIngrediente(1L, "Sal", "Sal de mesa"),
            crearIngrediente(2L, "Azúcar", "Azúcar blanca")
        );
        when(ingredienteRepository.findAll()).thenReturn(ingredientes);

        // Act
        List<Ingrediente> resultado = ingredienteService.listarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Sal", resultado.get(0).getNombre());
        assertEquals("Azúcar", resultado.get(1).getNombre());
        verify(ingredienteRepository).findAll();
    }

    @Test
    void debeRetornarListaVaciaCuandoNoHayIngredientes() {
        // Arrange
        when(ingredienteRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Ingrediente> resultado = ingredienteService.listarTodos();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(ingredienteRepository).findAll();
    }

    @Test
    void debeCrearIngredienteSinDescripcion() {
        // Arrange
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre("Sal");
        // No establecemos descripción

        when(ingredienteRepository.save(any(Ingrediente.class))).thenReturn(ingrediente);

        // Act
        Ingrediente salvo = ingredienteService.crearIngrediente(ingrediente);

        // Assert
        assertNotNull(salvo);
        assertEquals("Sal", salvo.getNombre());
        assertNull(salvo.getDescripcion());
        verify(ingredienteRepository).save(ingrediente);
    }

    // Método auxiliar para crear ingredientes de prueba
    private Ingrediente crearIngrediente(Long id, String nombre, String descripcion) {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre(nombre);
        ingrediente.setDescripcion(descripcion);
        try {
            java.lang.reflect.Field field = Ingrediente.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(ingrediente, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ingrediente;
    }
}
