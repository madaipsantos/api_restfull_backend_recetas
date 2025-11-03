package com.saboresmundo.recetas.service;

import com.saboresmundo.recetas.model.Usuario;
import com.saboresmundo.recetas.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.lang.reflect.Field;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarTodos() {
        // Arrange
        List<Usuario> usuarios = Arrays.asList(
            crearUsuarioTest(1L, "User1", "user1@test.com"),
            crearUsuarioTest(2L, "User2", "user2@test.com")
        );
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        // Act
        List<Usuario> resultado = usuarioService.listarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("User1", resultado.get(0).getNombre());
        assertEquals("User2", resultado.get(1).getNombre());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testEliminarUsuarioExitoso() {
        // Arrange
        Long userId = 1L;
        when(usuarioRepository.existsById(userId)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(userId);

        // Act
        boolean resultado = usuarioService.eliminarUsuario(userId);

        // Assert
        assertTrue(resultado);
        verify(usuarioRepository, times(1)).existsById(userId);
        verify(usuarioRepository, times(1)).deleteById(userId);
    }

    @Test
    void testEliminarUsuarioNoExistente() {
        // Arrange
        Long userId = 999L;
        when(usuarioRepository.existsById(userId)).thenReturn(false);

        // Act
        boolean resultado = usuarioService.eliminarUsuario(userId);

        // Assert
        assertFalse(resultado);
        verify(usuarioRepository, times(1)).existsById(userId);
        verify(usuarioRepository, never()).deleteById(anyLong());
    }

    @Test
    void testFindByIdNoEncontrado() {
        // Arrange
        Long userId = 1L;
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        Usuario resultado = usuarioService.findById(userId);

        // Assert
        assertNull(resultado);
        verify(usuarioRepository).findById(userId);
    }

    @Test
    void testGetAuthenticatedUserNoImplementado() {
        // Act & Assert
        assertNull(usuarioService.getAuthenticatedUser());
    }

    // Método auxiliar para crear usuarios de prueba
    private Usuario crearUsuarioTest(Long id, String nombre, String email) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        // Utilizamos reflexión para establecer el ID ya que es un campo privado y autogenerado
        try {
            java.lang.reflect.Field field = Usuario.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(usuario, id);
        } catch (Exception e) {
            // En un caso real, deberíamos manejar esta excepción apropiadamente
            throw new RuntimeException("Error setting up test user", e);
        }
        return usuario;
    }
}
