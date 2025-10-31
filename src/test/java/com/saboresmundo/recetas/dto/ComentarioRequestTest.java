package com.saboresmundo.recetas.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe ComentarioRequest
 * 
 * Testa funcionalidades do DTO para comentários:
 * - Construtores (vazio e com parâmetros)
 * - Getters e setters
 * - Validação de campos obrigatórios
 * - Casos edge e limites de valoração
 */
class ComentarioRequestTest {

    private ComentarioRequest comentarioRequest;

    @BeforeEach
    void setUp() {
        comentarioRequest = new ComentarioRequest();
    }

    @Test
    void testConstructorVazio() {
        // Verifica se o construtor vazio inicializa corretamente
        assertNotNull(comentarioRequest);
        assertNull(comentarioRequest.getId());
        assertNull(comentarioRequest.getRecetaId());
        assertNull(comentarioRequest.getComentario());
        assertNull(comentarioRequest.getValoracion());
        assertNull(comentarioRequest.getUsuarioId());
    }

    @Test
    void testInicializacaoComSetters() {
        // Teste de inicialização usando setters (sem construtor com parâmetros)
        Long id = 1L;
        Long recetaId = 2L;
        String comentario = "Deliciosa receta!";
        Integer valoracion = 5;
        Long usuarioId = 3L;

        ComentarioRequest comentarioComParametros = new ComentarioRequest();
        comentarioComParametros.setId(id);
        comentarioComParametros.setRecetaId(recetaId);
        comentarioComParametros.setComentario(comentario);
        comentarioComParametros.setValoracion(valoracion);
        comentarioComParametros.setUsuarioId(usuarioId);

        assertNotNull(comentarioComParametros);
        assertEquals(id, comentarioComParametros.getId());
        assertEquals(recetaId, comentarioComParametros.getRecetaId());
        assertEquals(comentario, comentarioComParametros.getComentario());
        assertEquals(valoracion, comentarioComParametros.getValoracion());
        assertEquals(usuarioId, comentarioComParametros.getUsuarioId());
    }

    @Test
    void testSettersEGetters() {
        // Teste dos métodos getters e setters
        Long id = 10L;
        Long recetaId = 20L;
        String comentario = "Excelente receta, muy fácil de hacer";
        Integer valoracion = 4;
        Long usuarioId = 30L;

        comentarioRequest.setId(id);
        comentarioRequest.setRecetaId(recetaId);
        comentarioRequest.setComentario(comentario);
        comentarioRequest.setValoracion(valoracion);
        comentarioRequest.setUsuarioId(usuarioId);

        assertEquals(id, comentarioRequest.getId());
        assertEquals(recetaId, comentarioRequest.getRecetaId());
        assertEquals(comentario, comentarioRequest.getComentario());
        assertEquals(valoracion, comentarioRequest.getValoracion());
        assertEquals(usuarioId, comentarioRequest.getUsuarioId());
    }

    @Test
    void testValoresNull() {
        // Teste com valores null
        comentarioRequest.setId(null);
        comentarioRequest.setRecetaId(null);
        comentarioRequest.setComentario(null);
        comentarioRequest.setValoracion(null);
        comentarioRequest.setUsuarioId(null);

        assertNull(comentarioRequest.getId());
        assertNull(comentarioRequest.getRecetaId());
        assertNull(comentarioRequest.getComentario());
        assertNull(comentarioRequest.getValoracion());
        assertNull(comentarioRequest.getUsuarioId());
    }

    @Test
    void testComentarioVazio() {
        // Teste com comentário vazio
        comentarioRequest.setComentario("");
        assertEquals("", comentarioRequest.getComentario());
    }

    @Test
    void testValoracionMinima() {
        // Teste com valoração mínima (1)
        comentarioRequest.setValoracion(1);
        assertEquals(1, comentarioRequest.getValoracion());
    }

    @Test
    void testValoracionMaxima() {
        // Teste com valoração máxima (5)
        comentarioRequest.setValoracion(5);
        assertEquals(5, comentarioRequest.getValoracion());
    }

    @Test
    void testValoracionesDiferentes() {
        // Teste com diferentes valores de valoração
        Integer[] valoraciones = { 1, 2, 3, 4, 5 };

        for (Integer valoracion : valoraciones) {
            comentarioRequest.setValoracion(valoracion);
            assertEquals(valoracion, comentarioRequest.getValoracion());
        }
    }

    @Test
    void testIdsPositivos() {
        // Teste com IDs positivos
        comentarioRequest.setId(100L);
        comentarioRequest.setRecetaId(200L);
        comentarioRequest.setUsuarioId(300L);

        assertEquals(100L, comentarioRequest.getId());
        assertEquals(200L, comentarioRequest.getRecetaId());
        assertEquals(300L, comentarioRequest.getUsuarioId());
    }

    @Test
    void testIdsZero() {
        // Teste com IDs zero (caso edge)
        comentarioRequest.setId(0L);
        comentarioRequest.setRecetaId(0L);
        comentarioRequest.setUsuarioId(0L);

        assertEquals(0L, comentarioRequest.getId());
        assertEquals(0L, comentarioRequest.getRecetaId());
        assertEquals(0L, comentarioRequest.getUsuarioId());
    }

    @Test
    void testComentarioLongo() {
        // Teste com comentário longo
        String comentarioLongo = "Esta es una receta absolutamente deliciosa que he probado múltiples veces. " +
                "Los ingredientes están perfectamente balanceados y las instrucciones son muy claras. " +
                "Definitivamente la recomiendo a cualquier persona que quiera preparar algo especial " +
                "para su familia. El tiempo de preparación es muy razonable y el resultado final " +
                "siempre es espectacular. ¡No puedo esperar a probar otras recetas del mismo chef!";

        comentarioRequest.setComentario(comentarioLongo);
        assertEquals(comentarioLongo, comentarioRequest.getComentario());
    }

    @Test
    void testComentarioCorto() {
        // Teste com comentário curto
        String comentarioCorto = "¡Excelente!";
        comentarioRequest.setComentario(comentarioCorto);
        assertEquals(comentarioCorto, comentarioRequest.getComentario());
    }

    @Test
    void testComentarioComCaracteresEspeciais() {
        // Teste com comentário contendo caracteres especiais
        String comentarioEspecial = "¡Fantástica receta! 5/5 ⭐⭐⭐⭐⭐ 100% recomendable 👍";
        comentarioRequest.setComentario(comentarioEspecial);
        assertEquals(comentarioEspecial, comentarioRequest.getComentario());
    }

    @Test
    void testComentarioComQuebrasLinha() {
        // Teste com comentário contendo quebras de linha
        String comentarioComQuebras = "Primera línea del comentario.\nSegunda línea.\n\nTercera línea.";
        comentarioRequest.setComentario(comentarioComQuebras);
        assertEquals(comentarioComQuebras, comentarioRequest.getComentario());
    }

    @Test
    void testValoracionForaLimites() {
        // Teste com valorações fora dos limites típicos (o DTO não valida, apenas
        // armazena)
        comentarioRequest.setValoracion(-1);
        assertEquals(-1, comentarioRequest.getValoracion());

        comentarioRequest.setValoracion(0);
        assertEquals(0, comentarioRequest.getValoracion());

        comentarioRequest.setValoracion(6);
        assertEquals(6, comentarioRequest.getValoracion());

        comentarioRequest.setValoracion(100);
        assertEquals(100, comentarioRequest.getValoracion());
    }

    @Test
    void testCasoUsoCompletoNovoComentario() {
        // Teste de um caso de uso completo - novo comentário
        Long recetaId = 15L;
        String comentario = "Una receta maravillosa, muy fácil de seguir";
        Integer valoracion = 5;
        Long usuarioId = 25L;

        // Novo comentário (sem ID)
        ComentarioRequest novoComentario = new ComentarioRequest();
        novoComentario.setRecetaId(recetaId);
        novoComentario.setComentario(comentario);
        novoComentario.setValoracion(valoracion);
        novoComentario.setUsuarioId(usuarioId);

        assertNull(novoComentario.getId());
        assertEquals(recetaId, novoComentario.getRecetaId());
        assertEquals(comentario, novoComentario.getComentario());
        assertEquals(valoracion, novoComentario.getValoracion());
        assertEquals(usuarioId, novoComentario.getUsuarioId());
    }

    @Test
    void testCasoUsoCompletoEdicaoComentario() {
        // Teste de um caso de uso completo - edição de comentário
        Long id = 5L;
        Long recetaId = 15L;
        String comentario = "Comentário atualizado após fazer a receta novamente";
        Integer valoracion = 4;
        Long usuarioId = 25L;

        // Edição de comentário (com ID)
        ComentarioRequest edicaoComentario = new ComentarioRequest();
        edicaoComentario.setId(id);
        edicaoComentario.setRecetaId(recetaId);
        edicaoComentario.setComentario(comentario);
        edicaoComentario.setValoracion(valoracion);
        edicaoComentario.setUsuarioId(usuarioId);

        assertEquals(id, edicaoComentario.getId());
        assertEquals(recetaId, edicaoComentario.getRecetaId());
        assertEquals(comentario, edicaoComentario.getComentario());
        assertEquals(valoracion, edicaoComentario.getValoracion());
        assertEquals(usuarioId, edicaoComentario.getUsuarioId());
    }

    @Test
    void testComentarioApenasTexto() {
        // Teste com apenas texto do comentário (outros campos null)
        String apenasTexto = "Solo un comentario sin valoración";
        comentarioRequest.setComentario(apenasTexto);

        assertEquals(apenasTexto, comentarioRequest.getComentario());
        assertNull(comentarioRequest.getValoracion());
        assertNull(comentarioRequest.getRecetaId());
        assertNull(comentarioRequest.getUsuarioId());
        assertNull(comentarioRequest.getId());
    }

    @Test
    void testApenasValoracion() {
        // Teste com apenas valoração (sem comentário)
        comentarioRequest.setValoracion(3);

        assertEquals(3, comentarioRequest.getValoracion());
        assertNull(comentarioRequest.getComentario());
        assertNull(comentarioRequest.getRecetaId());
        assertNull(comentarioRequest.getUsuarioId());
        assertNull(comentarioRequest.getId());
    }
}