package com.saboresmundo.recetas.dto;

import com.saboresmundo.recetas.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe RecetaResponse
 * 
 * Testa funcionalidades do DTO de resposta complexo para receitas:
 * - Construtores múltiplos
 * - Mapeamento de entidades para DTOs aninhados
 * - Getters e setters
 * - Classes internas (IngredienteDTO, PasoDTO, ComentarioDTO)
 */
class RecetaResponseTest {

    private Usuario usuario;
    private Pais pais;
    private Receta receta;
    private Ingrediente ingrediente1;
    private PasoReceta paso1;
    private ComentarioReceta comentario1;
    private RecetaIngrediente recetaIngrediente1;

    @BeforeEach
    void setUp() {
        // Setup de entidades base para testes
        usuario = new Usuario();
        usuario.setNombre("Chef Test");
        usuario.setEmail("chef@test.com");
        usuario.setPasswordHash("password");

        pais = new Pais();
        pais.setNombre("España");

        // Ingrediente
        ingrediente1 = new Ingrediente();
        ingrediente1.setNombre("Arroz");

        // Receta principal
        receta = new Receta();
        receta.setId(10L);
        receta.setTitulo("Paella Valenciana");
        receta.setDescripcion("Una deliciosa paella tradicional");
        receta.setDuracionMinutos(45);
        receta.setDificultad("Media");
        receta.setValoracion(4.5f);
        receta.setFotoUrl("paella.jpg");
        receta.setEstado("Publicada");
        receta.setFechaPublicacion(LocalDateTime.of(2024, 1, 15, 10, 30));
        receta.setUsuario(usuario);
        receta.setPais(pais);

        // RecetaIngrediente
        recetaIngrediente1 = new RecetaIngrediente();
        recetaIngrediente1.setReceta(receta);
        recetaIngrediente1.setIngrediente(ingrediente1);
        recetaIngrediente1.setCantidad("2");
        recetaIngrediente1.setUnidad("tazas");

        // Paso
        paso1 = new PasoReceta();
        paso1.setReceta(receta);
        paso1.setOrden(1);
        paso1.setDescripcion("Preparar el sofrito");

        // Comentario
        Usuario usuarioComentario = new Usuario();
        usuarioComentario.setNombre("Comentarista");

        comentario1 = new ComentarioReceta();
        comentario1.setId(1L);
        comentario1.setReceta(receta);
        comentario1.setUsuario(usuarioComentario);
        comentario1.setComentario("Excelente receta!");
        comentario1.setValoracion(5);
        comentario1.setFecha(LocalDateTime.of(2024, 1, 20, 14, 0));
    }

    @Test
    void testConstructorConReceta() {
        // Teste do construtor que recebe uma entidade Receta
        RecetaResponse response = new RecetaResponse(receta);

        assertNotNull(response);
        assertEquals(receta.getId(), response.getId());
        assertEquals(receta.getTitulo(), response.getTitulo());
        assertEquals(receta.getDescripcion(), response.getDescripcion());
        assertEquals(receta.getDuracionMinutos(), response.getDuracionMinutos());
        assertEquals(receta.getDificultad(), response.getDificultad());
        assertEquals(receta.getValoracion(), response.getValoracion());
        assertEquals(receta.getFotoUrl(), response.getFotoUrl());
        assertEquals(receta.getEstado(), response.getEstado());
        assertEquals(usuario.getNombre(), response.getAutor());
        assertEquals(pais.getNombre(), response.getPais());
    }

    @Test
    void testConstructorConMensaje() {
        // Teste do construtor que recebe apenas uma mensagem
        String mensaje = "Receta no encontrada";
        RecetaResponse response = new RecetaResponse(mensaje);

        assertNotNull(response);
        assertEquals(mensaje, response.getMensaje());
        assertNull(response.getId());
        assertNull(response.getTitulo());
    }

    @Test
    void testIngredienteDTOConstructor() {
        // Teste específico para IngredienteDTO
        RecetaResponse.IngredienteDTO ingredienteDTO = new RecetaResponse.IngredienteDTO(recetaIngrediente1);

        assertEquals(ingrediente1.getId(), ingredienteDTO.getId());
        assertEquals(ingrediente1.getNombre(), ingredienteDTO.getNombre());
        assertEquals(recetaIngrediente1.getCantidad(), ingredienteDTO.getCantidad());
        assertEquals(recetaIngrediente1.getUnidad(), ingredienteDTO.getUnidad());
    }

    @Test
    void testPasoDTOConstructor() {
        // Teste específico para PasoDTO
        RecetaResponse.PasoDTO pasoDTO = new RecetaResponse.PasoDTO(paso1);

        assertEquals(paso1.getOrden(), pasoDTO.getOrden());
        assertEquals(paso1.getDescripcion(), pasoDTO.getDescripcion());
    }

    @Test
    void testComentarioDTOConstructor() {
        // Teste específico para ComentarioDTO
        RecetaResponse.ComentarioDTO comentarioDTO = new RecetaResponse.ComentarioDTO(comentario1);

        assertEquals(comentario1.getId(), comentarioDTO.getId());
        assertEquals(comentario1.getComentario(), comentarioDTO.getTexto());
        assertEquals(comentario1.getValoracion(), comentarioDTO.getValoracion());
        assertEquals("Comentarista", comentarioDTO.getUsuario());
    }

    @Test
    void testSettersEGetters() {
        // Teste dos métodos getters e setters da classe principal
        RecetaResponse response = new RecetaResponse(receta);

        response.setId(99L);
        response.setTitulo("Novo Título");
        response.setDescripcion("Nova descrição");
        response.setDuracionMinutos(120);
        response.setDificultad("Difícil");
        response.setValoracion(3.5f);
        response.setFotoUrl("nova-foto.jpg");
        response.setEstado("Borrador");
        response.setFechaPublicacion("31/12/2024");
        response.setAutor("Novo Chef");
        response.setPais("Brasil");
        response.setMensaje("Teste mensagem");

        assertEquals(99L, response.getId());
        assertEquals("Novo Título", response.getTitulo());
        assertEquals("Nova descrição", response.getDescripcion());
        assertEquals(120, response.getDuracionMinutos());
        assertEquals("Difícil", response.getDificultad());
        assertEquals(3.5f, response.getValoracion());
        assertEquals("nova-foto.jpg", response.getFotoUrl());
        assertEquals("Borrador", response.getEstado());
        assertEquals("31/12/2024", response.getFechaPublicacion());
        assertEquals("Novo Chef", response.getAutor());
        assertEquals("Brasil", response.getPais());
        assertEquals("Teste mensagem", response.getMensaje());
    }

    @Test
    void testRecetaComUsuarioNull() {
        // Teste com usuário null
        receta.setUsuario(null);
        RecetaResponse response = new RecetaResponse(receta);

        assertNull(response.getAutor());
        assertNull(response.getAutorId());
    }

    @Test
    void testRecetaComPaisNull() {
        // Teste com país null
        receta.setPais(null);
        RecetaResponse response = new RecetaResponse(receta);

        assertNull(response.getPais());
    }

    @Test
    void testRecetaComFechaNull() {
        // Teste com fecha de publicação null
        receta.setFechaPublicacion(null);
        RecetaResponse response = new RecetaResponse(receta);

        assertNull(response.getFechaPublicacion());
    }

    @Test
    void testIngredienteDTOComIngredienteNull() {
        // Teste com ingrediente null no RecetaIngrediente
        recetaIngrediente1.setIngrediente(null);

        // Este teste vai gerar uma exceção porque o construtor tenta acessar
        // propriedades do ingrediente null
        assertThrows(NullPointerException.class, () -> {
            new RecetaResponse.IngredienteDTO(recetaIngrediente1);
        });
    }

    @Test
    void testComentarioDTOComUsuarioNull() {
        // Teste com usuário null no comentário
        comentario1.setUsuario(null);
        RecetaResponse.ComentarioDTO comentarioDTO = new RecetaResponse.ComentarioDTO(comentario1);

        assertEquals("", comentarioDTO.getUsuario());
        assertEquals(comentario1.getId(), comentarioDTO.getId());
        assertEquals(comentario1.getComentario(), comentarioDTO.getTexto());
    }

    @Test
    void testComentarioDTOComFechaNull() {
        // Teste com fecha de comentário null
        comentario1.setFecha(null);
        RecetaResponse.ComentarioDTO comentarioDTO = new RecetaResponse.ComentarioDTO(comentario1);

        assertNull(comentarioDTO.getFecha());
    }

    @Test
    void testValoresExtremos() {
        // Teste com valores extremos
        receta.setId(Long.MAX_VALUE);
        receta.setDuracionMinutos(Integer.MAX_VALUE);
        receta.setValoracion(Float.MAX_VALUE);

        RecetaResponse response = new RecetaResponse(receta);

        assertEquals(Long.MAX_VALUE, response.getId());
        assertEquals(Integer.MAX_VALUE, response.getDuracionMinutos());
        assertEquals(Float.MAX_VALUE, response.getValoracion());
    }

    @Test
    void testCaracteresEspeciais() {
        // Teste com caracteres especiais
        receta.setTitulo("Paëlla Niçoise à la Française");
        receta.setDescripcion("¡Qué rico está! 100% recomendable 👨‍🍳");
        usuario.setNombre("José María");
        pais.setNombre("México");

        RecetaResponse response = new RecetaResponse(receta);

        assertEquals("Paëlla Niçoise à la Française", response.getTitulo());
        assertEquals("¡Qué rico está! 100% recomendable 👨‍🍳", response.getDescripcion());
        assertEquals("José María", response.getAutor());
        assertEquals("México", response.getPais());
    }

    @Test
    void testGettersDTOIngrediente() {
        // Teste específico dos getters de IngredienteDTO
        RecetaResponse.IngredienteDTO ingredienteDTO = new RecetaResponse.IngredienteDTO(recetaIngrediente1);

        // ID pode ser null se não foi persistido no banco
        assertDoesNotThrow(() -> ingredienteDTO.getId());
        assertNotNull(ingredienteDTO.getNombre());
        assertNotNull(ingredienteDTO.getCantidad());
        assertNotNull(ingredienteDTO.getUnidad());
        assertNull(ingredienteDTO.getDescripcion()); // Campo não preenchido no construtor
    }

    @Test
    void testGettersDTOPaso() {
        // Teste específico dos getters de PasoDTO
        paso1.setFotoUrl("paso1.jpg");
        RecetaResponse.PasoDTO pasoDTO = new RecetaResponse.PasoDTO(paso1);

        assertNotNull(pasoDTO.getOrden());
        assertNotNull(pasoDTO.getDescripcion());
        assertEquals("paso1.jpg", pasoDTO.getFotoUrl());
    }

    @Test
    void testGettersDTOComentario() {
        // Teste específico dos getters de ComentarioDTO
        RecetaResponse.ComentarioDTO comentarioDTO = new RecetaResponse.ComentarioDTO(comentario1);

        assertNotNull(comentarioDTO.getId());
        assertNotNull(comentarioDTO.getUsuario());
        assertNotNull(comentarioDTO.getTexto());
        assertNotNull(comentarioDTO.getValoracion());
        assertNotNull(comentarioDTO.getFecha());
    }

    @Test
    void testValoresMinimos() {
        // Teste com valores mínimos
        receta.setId(0L);
        receta.setDuracionMinutos(0);
        receta.setValoracion(0.0f);
        paso1.setOrden(0);
        comentario1.setValoracion(0);

        RecetaResponse response = new RecetaResponse(receta);
        RecetaResponse.PasoDTO pasoDTO = new RecetaResponse.PasoDTO(paso1);
        RecetaResponse.ComentarioDTO comentarioDTO = new RecetaResponse.ComentarioDTO(comentario1);

        assertEquals(0L, response.getId());
        assertEquals(0, response.getDuracionMinutos());
        assertEquals(0.0f, response.getValoracion());
        assertEquals(0, pasoDTO.getOrden());
        assertEquals(0, comentarioDTO.getValoracion());
    }

    @Test
    void testCamposVazios() {
        // Teste com strings vazias
        receta.setTitulo("");
        receta.setDescripcion("");
        receta.setDificultad("");
        receta.setFotoUrl("");
        receta.setEstado("");

        RecetaResponse response = new RecetaResponse(receta);

        assertEquals("", response.getTitulo());
        assertEquals("", response.getDescripcion());
        assertEquals("", response.getDificultad());
        assertEquals("", response.getFotoUrl());
        assertEquals("", response.getEstado());
    }

    @Test
    void testAutorIdMapping() {
        // Teste específico do mapeamento do autorId
        // Como Usuario não tem setId público, testamos apenas o cenário com usuario
        // null
        receta.setUsuario(null);
        RecetaResponse response = new RecetaResponse(receta);

        assertNull(response.getAutorId());

        // Testando com usuario válido
        receta.setUsuario(usuario);
        RecetaResponse response2 = new RecetaResponse(receta);

        // Se o usuario tem ID, deve retornar; se não tem, pode ser null
        assertEquals(usuario.getId(), response2.getAutorId());
    }

    @Test
    void testSetListasNull() {
        // Teste configurando listas como null
        RecetaResponse response = new RecetaResponse(receta);

        response.setIngredientes(null);
        response.setPasos(null);
        response.setComentarios(null);

        assertNull(response.getIngredientes());
        assertNull(response.getPasos());
        assertNull(response.getComentarios());
    }

    @Test
    void testFechaPublicacionStringFormat() {
        // Teste da conversão da data para string
        LocalDateTime fecha = LocalDateTime.of(2024, 6, 15, 14, 30, 45);
        receta.setFechaPublicacion(fecha);

        RecetaResponse response = new RecetaResponse(receta);

        // A data é convertida usando toString(), então terá o formato ISO
        assertEquals(fecha.toString(), response.getFechaPublicacion());
    }

    @Test
    void testComentarioDTOFechaStringFormat() {
        // Teste da conversão da data do comentário para string
        LocalDateTime fecha = LocalDateTime.of(2024, 6, 15, 14, 30, 45);
        comentario1.setFecha(fecha);

        RecetaResponse.ComentarioDTO comentarioDTO = new RecetaResponse.ComentarioDTO(comentario1);

        // A data é convertida usando toString(), então terá o formato ISO
        assertEquals(fecha.toString(), comentarioDTO.getFecha());
    }
}