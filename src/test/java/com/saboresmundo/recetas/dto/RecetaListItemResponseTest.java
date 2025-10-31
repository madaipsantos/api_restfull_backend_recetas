package com.saboresmundo.recetas.dto;

import com.saboresmundo.recetas.model.Pais;
import com.saboresmundo.recetas.model.Receta;
import com.saboresmundo.recetas.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe RecetaListItemResponse
 * 
 * Testa funcionalidades do DTO de resposta para lista de receitas:
 * - Construtor baseado em entidade Receta
 * - Método estático fromRecetas
 * - Lógica de truncamento de descrição
 * - Formatação de data
 * - Getters e setters
 */
class RecetaListItemResponseTest {

    private Usuario usuario;
    private Pais pais;
    private Receta receta;

    @BeforeEach
    void setUp() {
        // Setup de entidades base para testes
        usuario = new Usuario();
        usuario.setNombre("Chef Test");
        usuario.setEmail("chef@test.com");
        usuario.setPasswordHash("password");

        pais = new Pais();
        pais.setNombre("España");

        receta = new Receta();
        receta.setId(10L);
        receta.setTitulo("Paella Valenciana");
        receta.setDescripcion("Una deliciosa paella tradicional de Valencia con ingredientes frescos");
        receta.setDuracionMinutos(45);
        receta.setDificultad("Media");
        receta.setFechaPublicacion(LocalDateTime.of(2024, 1, 15, 10, 30));
        receta.setUsuario(usuario);
        receta.setPais(pais);
    }

    @Test
    void testConstructorConReceta() {
        // Teste do construtor que recebe uma entidade Receta
        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        assertNotNull(response);
        assertEquals(receta.getId(), response.getId());
        assertEquals(receta.getTitulo(), response.getTitulo());
        assertEquals(receta.getDescripcion(), response.getDescripcion());
        assertEquals(receta.getDuracionMinutos(), response.getDuracionMinutos());
        assertEquals(receta.getDificultad(), response.getDificultad());
        assertEquals("15/01/2024", response.getFechaPublicacion());
        assertEquals(usuario.getNombre(), response.getAutorNombre());
        assertEquals(pais.getNombre(), response.getPaisNombre());
    }

    @Test
    void testMetodoEstaticoFromRecetas() {
        // Teste do método estático fromRecetas()
        Receta receta2 = new Receta();
        receta2.setId(20L);
        receta2.setTitulo("Tortilla Española");
        receta2.setDescripcion("Clásica tortilla de patatas");
        receta2.setDuracionMinutos(30);
        receta2.setDificultad("Fácil");
        receta2.setFechaPublicacion(LocalDateTime.of(2024, 2, 10, 15, 0));
        receta2.setUsuario(usuario);
        receta2.setPais(pais);

        List<Receta> recetas = Arrays.asList(receta, receta2);
        List<RecetaListItemResponse> responses = RecetaListItemResponse.fromRecetas(recetas);

        assertEquals(2, responses.size());

        RecetaListItemResponse response1 = responses.get(0);
        assertEquals(receta.getId(), response1.getId());
        assertEquals(receta.getTitulo(), response1.getTitulo());

        RecetaListItemResponse response2 = responses.get(1);
        assertEquals(receta2.getId(), response2.getId());
        assertEquals(receta2.getTitulo(), response2.getTitulo());
    }

    @Test
    void testTruncamentoDescripcionExata100Caracteres() {
        // Teste com descrição de exatamente 100 caracteres (não deve truncar)
        String descripcion100 = "A".repeat(100);
        receta.setDescripcion(descripcion100);

        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        assertEquals(descripcion100, response.getDescripcion());
        assertFalse(response.getDescripcion().endsWith("..."));
    }

    @Test
    void testTruncamentoDescripcionMaior100Caracteres() {
        // Teste com descrição maior que 100 caracteres (deve truncar)
        String descripcionLarga = "A".repeat(150);
        receta.setDescripcion(descripcionLarga);

        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        assertEquals(100 + 3, response.getDescripcion().length()); // 100 + "..."
        assertTrue(response.getDescripcion().endsWith("..."));
        assertEquals("A".repeat(100) + "...", response.getDescripcion());
    }

    @Test
    void testTruncamentoDescripcionMenor100Caracteres() {
        // Teste com descrição menor que 100 caracteres (não deve truncar)
        String descripcionCorta = "A".repeat(50);
        receta.setDescripcion(descripcionCorta);

        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        assertEquals(descripcionCorta, response.getDescripcion());
        assertFalse(response.getDescripcion().endsWith("..."));
    }

    @Test
    void testDescripcionNull() {
        // Teste com descrição null
        receta.setDescripcion(null);

        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        assertNull(response.getDescripcion());
    }

    @Test
    void testDescripcionVazia() {
        // Teste com descrição vazia
        receta.setDescripcion("");

        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        assertEquals("", response.getDescripcion());
    }

    @Test
    void testFormatacaoDataDiferentesFechas() {
        // Teste com diferentes datas para verificar formatação
        LocalDateTime[] fechas = {
                LocalDateTime.of(2023, 12, 25, 9, 15),
                LocalDateTime.of(2024, 2, 29, 23, 59), // Ano bissexto
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 6, 15, 12, 30)
        };

        String[] expectedFormats = {
                "25/12/2023",
                "29/02/2024",
                "01/01/2024",
                "15/06/2024"
        };

        for (int i = 0; i < fechas.length; i++) {
            receta.setFechaPublicacion(fechas[i]);
            RecetaListItemResponse response = new RecetaListItemResponse(receta);
            assertEquals(expectedFormats[i], response.getFechaPublicacion());
        }
    }

    @Test
    void testFechaPublicacionNull() {
        // Teste com fecha de publicación null
        receta.setFechaPublicacion(null);

        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        assertNull(response.getFechaPublicacion());
    }

    @Test
    void testUsuarioNull() {
        // Teste com usuario null
        receta.setUsuario(null);

        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        assertNull(response.getAutorNombre());
    }

    @Test
    void testPaisNull() {
        // Teste com país null
        receta.setPais(null);

        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        assertNull(response.getPaisNombre());
    }

    @Test
    void testUsuarioComNomeNull() {
        // Teste com nome do usuário null
        usuario.setNombre(null);
        receta.setUsuario(usuario);

        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        assertNull(response.getAutorNombre());
    }

    @Test
    void testPaisComNomeNull() {
        // Teste com nome do país null
        pais.setNombre(null);
        receta.setPais(pais);

        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        assertNull(response.getPaisNombre());
    }

    @Test
    void testTodosOsCamposResponse() {
        // Teste verificando todos os getters da response
        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        // Verificar todos os campos
        assertEquals(10L, response.getId());
        assertEquals("Paella Valenciana", response.getTitulo());
        assertEquals("Una deliciosa paella tradicional de Valencia con ingredientes frescos",
                response.getDescripcion());
        assertEquals(45, response.getDuracionMinutos());
        assertEquals("Media", response.getDificultad());
        assertEquals("15/01/2024", response.getFechaPublicacion());
        assertEquals("Chef Test", response.getAutorNombre());
        assertEquals("España", response.getPaisNombre());
    }

    @Test
    void testRecetaComValoresMinimos() {
        // Teste com valores mínimos/edge cases
        receta.setId(0L);
        receta.setTitulo("");
        receta.setDuracionMinutos(0);
        receta.setDificultad("");

        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        assertEquals(0L, response.getId());
        assertEquals("", response.getTitulo());
        assertEquals(0, response.getDuracionMinutos());
        assertEquals("", response.getDificultad());
    }

    @Test
    void testRecetaComNomesEspeciais() {
        // Teste com caracteres especiais nos nomes
        receta.setTitulo("Paëlla Niçoise à la Française");
        usuario.setNombre("José María");
        pais.setNombre("México");

        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        assertEquals("Paëlla Niçoise à la Française", response.getTitulo());
        assertEquals("José María", response.getAutorNombre());
        assertEquals("México", response.getPaisNombre());
    }

    @Test
    void testSettersResponse() {
        // Teste dos setters da response
        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        response.setId(99L);
        response.setTitulo("Novo Título");
        response.setDescripcion("Nova descrição");
        response.setDuracionMinutos(120);
        response.setDificultad("Difícil");
        response.setValoracion(4.5f);
        response.setFotoUrl("nova-foto.jpg");
        response.setFechaPublicacion("31/12/2024");
        response.setPaisNombre("Brasil");
        response.setAutorNombre("Novo Chef");

        assertEquals(99L, response.getId());
        assertEquals("Novo Título", response.getTitulo());
        assertEquals("Nova descrição", response.getDescripcion());
        assertEquals(120, response.getDuracionMinutos());
        assertEquals("Difícil", response.getDificultad());
        assertEquals(4.5f, response.getValoracion());
        assertEquals("nova-foto.jpg", response.getFotoUrl());
        assertEquals("31/12/2024", response.getFechaPublicacion());
        assertEquals("Brasil", response.getPaisNombre());
        assertEquals("Novo Chef", response.getAutorNombre());
    }

    @Test
    void testDuracionMinutosValoresExtremos() {
        // Teste com valores extremos de duração
        Integer[] duraciones = { 1, 60, 120, 999, Integer.MAX_VALUE };

        for (Integer duracion : duraciones) {
            receta.setDuracionMinutos(duracion);
            RecetaListItemResponse response = new RecetaListItemResponse(receta);
            assertEquals(duracion, response.getDuracionMinutos());
        }
    }

    @Test
    void testDificultadesVariadas() {
        // Teste com diferentes níveis de dificuldade
        String[] dificultades = { "Fácil", "Media", "Difícil", "Muy Difícil", "FÁCIL", "fácil" };

        for (String dificultad : dificultades) {
            receta.setDificultad(dificultad);
            RecetaListItemResponse response = new RecetaListItemResponse(receta);
            assertEquals(dificultad, response.getDificultad());
        }
    }

    @Test
    void testValoracionNull() {
        // Teste com valoração null
        receta.setValoracion(null);

        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        assertNull(response.getValoracion());
    }

    @Test
    void testFotoUrlNull() {
        // Teste com foto URL null
        receta.setFotoUrl(null);

        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        assertNull(response.getFotoUrl());
    }

    @Test
    void testFromRecetasComListaVazia() {
        // Teste com lista de receitas vazia
        List<Receta> recetasVazias = Arrays.asList();
        List<RecetaListItemResponse> responses = RecetaListItemResponse.fromRecetas(recetasVazias);

        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }

    @Test
    void testTruncamentoComCaracteresEspeciais() {
        // Teste de truncamento com caracteres especiais
        String descripcionConEspeciales = "¡Esta es una receta fantástica! Con acentos café, niño, corazón. "
                + "También tiene símbolos como @#$%^&*()_+{}|:<>?[]\\;'.,/`~1234567890-=";
        receta.setDescripcion(descripcionConEspeciales);

        RecetaListItemResponse response = new RecetaListItemResponse(receta);

        assertEquals(100 + 3, response.getDescripcion().length());
        assertTrue(response.getDescripcion().endsWith("..."));
    }
}