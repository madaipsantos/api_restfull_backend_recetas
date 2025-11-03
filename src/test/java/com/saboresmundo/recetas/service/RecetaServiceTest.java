package com.saboresmundo.recetas.service;

import com.saboresmundo.recetas.dto.RecetaRequest;
import com.saboresmundo.recetas.dto.RecetaResponse;
import com.saboresmundo.recetas.model.*;
import com.saboresmundo.recetas.repository.*;
import java.util.List;
import java.lang.reflect.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RecetaServiceTest {

    @Mock
    private RecetaRepository recetaRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PaisRepository paisRepository;
    @Mock
    private IngredienteRepository ingredienteRepository;
    @Mock
    private RecetaIngredienteRepository recetaIngredienteRepository;
    @Mock
    private PasoRecetaRepository pasoRecetaRepository;

    @InjectMocks
    private RecetaService recetaService;

    private RecetaRequest recetaRequest;
    private Usuario usuario;
    private Pais pais;
    private Authentication authentication;

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
        usuario.setEmail("test@example.com");
        usuario.setNombre("Test User");
        usuario.setRol("USER");
        setIdUsingReflection(usuario, 1L);
        
        // Configurar request de prueba
        recetaRequest = new RecetaRequest();
        recetaRequest.setTitulo("Receta de prueba");
        recetaRequest.setDescripcion("Descripción de prueba");
        recetaRequest.setDuracionMinutos(30);
        recetaRequest.setDificultad("FACIL");

        // Configurar autenticación
        authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Configurar mock del usuario
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(java.util.Optional.of(usuario));
    }

    @Test
    void debeCrearRecetaConExito() {
        // Arrange
        when(recetaRepository.save(any(Receta.class))).thenAnswer(i -> {
            Receta r = i.getArgument(0);
            setIdUsingReflection(r, 1L);
            return r;
        });

        // Act
        var response = recetaService.crearReceta(recetaRequest);

        // Assert
        assertNotNull(response);
        assertNull(response.getMensaje());
        assertEquals("Receta de prueba", response.getTitulo());
        verify(recetaRepository).save(any(Receta.class));
    }

    @Test
    void debeRetornarErrorAlCrearRecetaSinUsuarioAutenticado() {
        // Arrange
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(java.util.Optional.empty());

        // Act
        var response = recetaService.crearReceta(recetaRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getMensaje());
        assertTrue(response.getMensaje().contains("Usuario no encontrado"));
        verify(recetaRepository, never()).save(any());
    }

    @Test
    void debeListarRecetasPorFiltro() {
        // Arrange
        String filtro = "arroz";
        Receta receta = new Receta();
        receta.setTitulo("Arroz con Pollo");
        when(recetaRepository.findByTituloContainingIgnoreCase(filtro))
            .thenReturn(java.util.List.of(receta));

        // Act
        List<Receta> recetas = recetaService.listarRecetas(filtro);

        // Assert
        assertFalse(recetas.isEmpty());
        assertEquals(1, recetas.size());
        assertEquals("Arroz con Pollo", recetas.get(0).getTitulo());
        verify(recetaRepository).findByTituloContainingIgnoreCase(filtro);
    }

    @Test
    void debeBuscarRecetaPorId() {
        // Arrange
        Long id = 1L;
        Receta receta = new Receta();
        setIdUsingReflection(receta, id);
        receta.setTitulo("Receta de prueba");
        receta.setUsuario(usuario);
        when(recetaRepository.findById(id)).thenReturn(java.util.Optional.of(receta));

        // Act
        var encontrada = recetaService.findById(id);

        // Assert
        assertNotNull(encontrada);
        assertEquals(id, encontrada.getId());
        assertEquals("Receta de prueba", encontrada.getTitulo());
        verify(recetaRepository).findById(id);
    }

    @Test
    void debeEditarRecetaConExito() {
        // Arrange
        Long id = 1L;
        Receta recetaExistente = new Receta();
        setIdUsingReflection(recetaExistente, id);
        recetaExistente.setUsuario(usuario);
        
        when(recetaRepository.findById(id)).thenReturn(java.util.Optional.of(recetaExistente));
        when(recetaRepository.save(any(Receta.class))).thenReturn(recetaExistente);

        // Act
        var response = recetaService.editarReceta(id, recetaRequest);

        // Assert
        assertNotNull(response);
        assertNull(response.getMensaje());
        verify(recetaRepository).save(any(Receta.class));
    }

    @Test
    void debeNegarEdicionDeRecetaDeOtroUsuario() {
        // Arrange
        Long id = 1L;
        Usuario otroUsuario = new Usuario();
        setIdUsingReflection(otroUsuario, 2L);
        
        Receta recetaExistente = new Receta();
        setIdUsingReflection(recetaExistente, id);
        recetaExistente.setUsuario(otroUsuario);
        
        when(recetaRepository.findById(id)).thenReturn(java.util.Optional.of(recetaExistente));

        // Act
        var response = recetaService.editarReceta(id, recetaRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getMensaje());
        assertTrue(response.getMensaje().contains("No tienes permiso"));
        verify(recetaRepository, never()).save(any());
    }

    @Test
    void debePermitirEdicionParaAdmin() {
        // Arrange
        Long id = 1L;
        Usuario otroUsuario = new Usuario();
        setIdUsingReflection(otroUsuario, 2L);
        
        usuario.setRol("ADMIN");
        
        Receta recetaExistente = new Receta();
        setIdUsingReflection(recetaExistente, id);
        recetaExistente.setUsuario(otroUsuario);
        
        when(recetaRepository.findById(id)).thenReturn(java.util.Optional.of(recetaExistente));
        when(recetaRepository.save(any(Receta.class))).thenReturn(recetaExistente);

        // Act
        var response = recetaService.editarReceta(id, recetaRequest);

        // Assert
        assertNotNull(response);
        assertNull(response.getMensaje());
        verify(recetaRepository).save(any());
    }

    @Test
    void debeEliminarRecetaConExito() {
        // Arrange
        Long id = 1L;
        Receta recetaExistente = new Receta();
        setIdUsingReflection(recetaExistente, id);
        recetaExistente.setUsuario(usuario);
        
        when(recetaRepository.findById(id)).thenReturn(java.util.Optional.of(recetaExistente));

        // Act
        var response = recetaService.eliminarReceta(id);

        // Assert
        assertNotNull(response);
        assertEquals("Receta eliminada correctamente", response.getMensaje());
        verify(recetaIngredienteRepository).deleteByReceta(recetaExistente);
        verify(pasoRecetaRepository).deleteByReceta(recetaExistente);
        verify(recetaRepository).delete(recetaExistente);
    }

    @Test
    void debeNegarEliminacionDeRecetaDeOtroUsuario() {
        // Arrange
        Long id = 1L;
        Usuario otroUsuario = new Usuario();
        setIdUsingReflection(otroUsuario, 2L);
        
        Receta recetaExistente = new Receta();
        setIdUsingReflection(recetaExistente, id);
        recetaExistente.setUsuario(otroUsuario);
        
        when(recetaRepository.findById(id)).thenReturn(java.util.Optional.of(recetaExistente));

        // Act
        var response = recetaService.eliminarReceta(id);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getMensaje());
        assertTrue(response.getMensaje().contains("No tienes permiso"));
        verify(recetaRepository, never()).delete(any());
    }

    @Test
    void debeProcesarIngredientesAlCrearReceta() {
        // Arrange
        RecetaRequest.IngredienteRequest ingredienteRequest = new RecetaRequest.IngredienteRequest();
        ingredienteRequest.setNombre("Sal");
        ingredienteRequest.setCantidad("1");
        ingredienteRequest.setUnidad("cucharada");
        
        recetaRequest.setIngredientes(java.util.List.of(ingredienteRequest));
        
        when(recetaRepository.save(any(Receta.class))).thenAnswer(i -> {
            Receta r = i.getArgument(0);
            setIdUsingReflection(r, 1L);
            return r;
        });
        
        when(ingredienteRepository.findByNombreIgnoreCase(anyString()))
            .thenReturn(java.util.Optional.empty());
        
        when(ingredienteRepository.save(any())).thenAnswer(i -> {
            Ingrediente ing = i.getArgument(0);
            setIdUsingReflection(ing, 1L);
            return ing;
        });

        // Act
        var response = recetaService.crearReceta(recetaRequest);

        // Assert
        assertNotNull(response);
        assertNull(response.getMensaje());
        verify(ingredienteRepository).findByNombreIgnoreCase("Sal");
        verify(ingredienteRepository).save(any());
        verify(recetaIngredienteRepository).save(any());
    }

    @Test
    void debeProcesarPasosAlCrearReceta() {
        // Arrange
        RecetaRequest.PasoRequest pasoRequest = new RecetaRequest.PasoRequest();
        pasoRequest.setOrden(1);
        pasoRequest.setDescripcion("Primer paso");
        pasoRequest.setFotoUrl("foto1.jpg");
        
        recetaRequest.setPasos(java.util.List.of(pasoRequest));
        
        when(recetaRepository.save(any(Receta.class))).thenAnswer(i -> {
            Receta r = i.getArgument(0);
            setIdUsingReflection(r, 1L);
            return r;
        });

        // Act
        var response = recetaService.crearReceta(recetaRequest);

        // Assert
        assertNotNull(response);
        assertNull(response.getMensaje());
        verify(pasoRecetaRepository).save(argThat(paso -> 
            paso.getOrden() == 1 && 
            paso.getDescripcion().equals("Primer paso") &&
            paso.getFotoUrl().equals("foto1.jpg")
        ));
    }

    @Test
    void debeMantenerOrdenDeLosPasos() {
        // Arrange
        var pasos = java.util.List.of(
            crearPasoRequest(1, "Primer paso"),
            crearPasoRequest(2, "Segundo paso"),
            crearPasoRequest(3, "Tercer paso")
        );
        recetaRequest.setPasos(pasos);
        
        when(recetaRepository.save(any(Receta.class))).thenAnswer(i -> {
            Receta r = i.getArgument(0);
            setIdUsingReflection(r, 1L);
            return r;
        });

        // Act
        var response = recetaService.crearReceta(recetaRequest);

        // Assert
        assertNotNull(response);
        assertNull(response.getMensaje());
        verify(pasoRecetaRepository, times(3)).save(any());
        verify(pasoRecetaRepository).save(argThat(paso -> paso.getOrden() == 1));
        verify(pasoRecetaRepository).save(argThat(paso -> paso.getOrden() == 2));
        verify(pasoRecetaRepository).save(argThat(paso -> paso.getOrden() == 3));
    }

    @Test
    void debeManejarErrorAlGuardarIngrediente() {
        // Arrange
        RecetaRequest.IngredienteRequest ingredienteRequest = new RecetaRequest.IngredienteRequest();
        ingredienteRequest.setNombre("Sal");
        recetaRequest.setIngredientes(java.util.List.of(ingredienteRequest));
        
        when(recetaRepository.save(any(Receta.class))).thenAnswer(i -> {
            Receta r = i.getArgument(0);
            setIdUsingReflection(r, 1L);
            return r;
        });
        
        when(ingredienteRepository.findByNombreIgnoreCase(anyString()))
            .thenReturn(java.util.Optional.empty());
            
        when(ingredienteRepository.save(any()))
            .thenThrow(new RuntimeException("Error al guardar ingrediente"));

        // Act
        var response = recetaService.crearReceta(recetaRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getMensaje());
        assertTrue(response.getMensaje().contains("Error al crear receta"));
    }

    @Test
    void debeActualizarIngredientesAlEditarReceta() {
        // Arrange
        Long recetaId = 1L;
        Receta recetaExistente = new Receta();
        setIdUsingReflection(recetaExistente, recetaId);
        recetaExistente.setUsuario(usuario);

        RecetaRequest.IngredienteRequest nuevoIngrediente = new RecetaRequest.IngredienteRequest();
        nuevoIngrediente.setNombre("Pimienta");
        recetaRequest.setIngredientes(java.util.List.of(nuevoIngrediente));

        when(recetaRepository.findById(recetaId)).thenReturn(java.util.Optional.of(recetaExistente));
        when(recetaRepository.save(any())).thenReturn(recetaExistente);

        // Act
        var response = recetaService.editarReceta(recetaId, recetaRequest);

        // Assert
        assertNotNull(response);
        assertNull(response.getMensaje());
        verify(recetaIngredienteRepository).deleteByReceta(recetaExistente);
        verify(ingredienteRepository).findByNombreIgnoreCase("Pimienta");
    }

    @Test
    void debeManejarRecetaNoEncontrada() {
        // Arrange
        Long id = 999L;
        when(recetaRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Act
        var response = recetaService.editarReceta(id, recetaRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getMensaje());
        assertTrue(response.getMensaje().contains("Receta no encontrada"));
    }

    private RecetaRequest.PasoRequest crearPasoRequest(int orden, String descripcion) {
        RecetaRequest.PasoRequest paso = new RecetaRequest.PasoRequest();
        paso.setOrden(orden);
        paso.setDescripcion(descripcion);
        return paso;
    }

    @Test
    void debeObtenerRecetasPorUsuario() {
        // Arrange
        Long usuarioId = 1L;
        Receta receta1 = new Receta();
        receta1.setTitulo("Receta 1");
        receta1.setUsuario(usuario);
        setIdUsingReflection(receta1, 1L);

        Receta receta2 = new Receta();
        receta2.setTitulo("Receta 2");
        receta2.setUsuario(usuario);
        setIdUsingReflection(receta2, 2L);

        when(recetaRepository.findByUsuarioId(usuarioId))
            .thenReturn(java.util.List.of(receta1, receta2));

        // Act
        List<RecetaResponse> recetas = recetaService.obtenerRecetasPorUsuario(usuarioId);

        // Assert
        assertNotNull(recetas);
        assertEquals(2, recetas.size());
        assertEquals("Receta 1", recetas.get(0).getTitulo());
        assertEquals("Receta 2", recetas.get(1).getTitulo());
        verify(recetaRepository).findByUsuarioId(usuarioId);
    }

    @Test
    void debeRetornarListaVaciaParaUsuarioSinRecetas() {
        // Arrange
        Long usuarioId = 1L;
        when(recetaRepository.findByUsuarioId(usuarioId))
            .thenReturn(java.util.Collections.emptyList());

        // Act
        List<RecetaResponse> recetas = recetaService.obtenerRecetasPorUsuario(usuarioId);

        // Assert
        assertNotNull(recetas);
        assertTrue(recetas.isEmpty());
        verify(recetaRepository).findByUsuarioId(usuarioId);
    }

    @Test
    void debeListarTodasLasRecetasSinFiltro() {
        // Arrange
        Receta receta1 = new Receta();
        receta1.setTitulo("Paella");
        Receta receta2 = new Receta();
        receta2.setTitulo("Gazpacho");
        when(recetaRepository.findAll()).thenReturn(java.util.List.of(receta1, receta2));

        // Act
        List<Receta> recetas = recetaService.listarRecetas(null);

        // Assert
        assertNotNull(recetas);
        assertEquals(2, recetas.size());
        assertEquals("Paella", recetas.get(0).getTitulo());
        assertEquals("Gazpacho", recetas.get(1).getTitulo());
        verify(recetaRepository).findAll();
        verify(recetaRepository, never()).findByTituloContainingIgnoreCase(any());
    }

    @Test
    void debeManejarErrorAlGuardarPasos() {
        // Arrange
        RecetaRequest.PasoRequest pasoRequest = new RecetaRequest.PasoRequest();
        pasoRequest.setOrden(1);
        pasoRequest.setDescripcion("Paso con error");
        recetaRequest.setPasos(java.util.List.of(pasoRequest));
        
        when(recetaRepository.save(any(Receta.class))).thenAnswer(i -> {
            Receta r = i.getArgument(0);
            setIdUsingReflection(r, 1L);
            return r;
        });
        
        when(pasoRecetaRepository.save(any()))
            .thenThrow(new RuntimeException("Error al guardar paso"));

        // Act
        var response = recetaService.crearReceta(recetaRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getMensaje());
        assertTrue(response.getMensaje().contains("Error al crear receta"));
        verify(recetaRepository).save(any());
        verify(pasoRecetaRepository).save(any());
    }

    @Test
    void debePermitirEliminarRecetaComoAdmin() {
        // Arrange
        Long id = 1L;
        Usuario otroUsuario = new Usuario();
        setIdUsingReflection(otroUsuario, 2L);
        
        usuario.setRol("ADMIN");
        
        Receta recetaExistente = new Receta();
        setIdUsingReflection(recetaExistente, id);
        recetaExistente.setUsuario(otroUsuario);
        
        when(recetaRepository.findById(id)).thenReturn(java.util.Optional.of(recetaExistente));

        // Act
        var response = recetaService.eliminarReceta(id);

        // Assert
        assertNotNull(response);
        assertEquals("Receta eliminada correctamente", response.getMensaje());
        verify(recetaIngredienteRepository).deleteByReceta(recetaExistente);
        verify(pasoRecetaRepository).deleteByReceta(recetaExistente);
        verify(recetaRepository).delete(recetaExistente);
    }
}