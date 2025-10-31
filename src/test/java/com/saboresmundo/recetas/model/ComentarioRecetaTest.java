package com.saboresmundo.recetas.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ComentarioRecetaTest {

    private ComentarioReceta comentario;
    private Receta receta;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        comentario = new ComentarioReceta();
        receta = new Receta();
        receta.setId(1L);
        receta.setTitulo("Pizza Margherita");
        usuario = new Usuario();
        usuario.setNombre("João Silva");
        usuario.setEmail("joao@example.com");
    }

    @Test
    void testConstructorVacio() {
        assertNotNull(comentario);
        assertNull(comentario.getId());
        assertNull(comentario.getReceta());
        assertNull(comentario.getUsuario());
        assertNull(comentario.getComentario());
        assertNull(comentario.getValoracion());
        assertNull(comentario.getFecha());
    }

    @Test
    void testConstructorConParametros() {
        String textoComentario = "Excelente receta, muy fácil de hacer";
        LocalDateTime fecha = LocalDateTime.of(2023, 12, 25, 14, 30);

        ComentarioReceta comentarioConParametros = new ComentarioReceta(receta, usuario, textoComentario, fecha);

        assertEquals(receta, comentarioConParametros.getReceta());
        assertEquals(usuario, comentarioConParametros.getUsuario());
        assertEquals(textoComentario, comentarioConParametros.getComentario());
        assertEquals(fecha, comentarioConParametros.getFecha());
    }

    @Test
    void testSettersEGetters() {
        String textoComentario = "Me encantó esta receta!";
        Integer valoracion = 5;
        LocalDateTime fecha = LocalDateTime.now();

        comentario.setReceta(receta);
        comentario.setUsuario(usuario);
        comentario.setComentario(textoComentario);
        comentario.setValoracion(valoracion);
        comentario.setFecha(fecha);

        assertEquals(receta, comentario.getReceta());
        assertEquals(usuario, comentario.getUsuario());
        assertEquals(textoComentario, comentario.getComentario());
        assertEquals(valoracion, comentario.getValoracion());
        assertEquals(fecha, comentario.getFecha());
    }

    @Test
    void testSetIdParaPruebas() {
        Long id = 123L;
        comentario.setId(id);
        assertEquals(id, comentario.getId());
    }

    @Test
    void testValoracionesValidas() {
        // Testando diferentes valores de valoração
        comentario.setValoracion(1);
        assertEquals(1, comentario.getValoracion());

        comentario.setValoracion(3);
        assertEquals(3, comentario.getValoracion());

        comentario.setValoracion(5);
        assertEquals(5, comentario.getValoracion());
    }

    @Test
    void testValoracionNull() {
        comentario.setValoracion(null);
        assertNull(comentario.getValoracion());
    }

    @Test
    void testComentarioLargo() {
        String comentarioLargo = "Este é um comentário muito longo que descreve em detalhes " +
                "toda a experiência de fazer esta receita maravilhosa. Primeiro, preciso dizer que " +
                "os ingredientes são fáceis de encontrar e o passo a passo é muito claro. " +
                "A preparação levou exatamente o tempo indicado e o resultado foi excepcional. " +
                "Toda a família adorou e já pediu para fazer novamente na próxima semana!";

        comentario.setComentario(comentarioLargo);
        assertEquals(comentarioLargo, comentario.getComentario());
    }

    @Test
    void testFechaAtual() {
        LocalDateTime agora = LocalDateTime.now();
        comentario.setFecha(agora);

        assertEquals(agora, comentario.getFecha());
    }

    @Test
    void testFechaEspecifica() {
        LocalDateTime natal2023 = LocalDateTime.of(2023, 12, 25, 18, 30, 45);
        comentario.setFecha(natal2023);

        assertEquals(natal2023, comentario.getFecha());
        assertEquals(2023, comentario.getFecha().getYear());
        assertEquals(12, comentario.getFecha().getMonthValue());
        assertEquals(25, comentario.getFecha().getDayOfMonth());
        assertEquals(18, comentario.getFecha().getHour());
        assertEquals(30, comentario.getFecha().getMinute());
    }

    @Test
    void testEqualsComMesmoObjeto() {
        assertTrue(comentario.equals(comentario));
    }

    @Test
    void testEqualsComObjetoNull() {
        assertFalse(comentario.equals(null));
    }

    @Test
    void testEqualsComClasseDiferente() {
        assertFalse(comentario.equals("string"));
    }

    @Test
    void testEqualsComIdNull() {
        ComentarioReceta outroComentario = new ComentarioReceta();
        assertTrue(comentario.equals(outroComentario));
    }

    @Test
    void testEqualsComIdsIguais() {
        comentario.setId(1L);
        ComentarioReceta outroComentario = new ComentarioReceta();
        outroComentario.setId(1L);

        assertTrue(comentario.equals(outroComentario));
    }

    @Test
    void testEqualsComIdsDiferentes() {
        comentario.setId(1L);
        ComentarioReceta outroComentario = new ComentarioReceta();
        outroComentario.setId(2L);

        assertFalse(comentario.equals(outroComentario));
    }

    @Test
    void testHashCode() {
        comentario.setId(1L);
        ComentarioReceta outroComentario = new ComentarioReceta();
        outroComentario.setId(1L);

        assertEquals(comentario.hashCode(), outroComentario.hashCode());
    }

    @Test
    void testToString() {
        comentario.setId(1L);
        comentario.setReceta(receta);
        comentario.setUsuario(usuario);

        String toString = comentario.toString();

        assertTrue(toString.contains("ComentarioReceta{"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("receta=1")); // ID da receta
        assertTrue(toString.contains("usuario=null")); // Usuario não tem ID definido
    }

    @Test
    void testToStringComRecetaUsuarioNull() {
        comentario.setId(2L);
        // receta e usuario ficam null

        String toString = comentario.toString();

        assertTrue(toString.contains("ComentarioReceta{"));
        assertTrue(toString.contains("id=2"));
        assertTrue(toString.contains("receta=null"));
        assertTrue(toString.contains("usuario=null"));
    }

    @Test
    void testComentarioCompleto() {
        // Configurar um comentário completo
        comentario.setId(1L);
        comentario.setReceta(receta);
        comentario.setUsuario(usuario);
        comentario.setComentario("Receita excelente! Muito saborosa e fácil de fazer.");
        comentario.setValoracion(5);
        comentario.setFecha(LocalDateTime.of(2023, 10, 15, 19, 45));

        // Verificações
        assertEquals(1L, comentario.getId());
        assertEquals(receta, comentario.getReceta());
        assertEquals(usuario, comentario.getUsuario());
        assertEquals("Receita excelente! Muito saborosa e fácil de fazer.", comentario.getComentario());
        assertEquals(5, comentario.getValoracion());
        assertNotNull(comentario.getFecha());
    }

    @Test
    void testComentarioSemValoracao() {
        comentario.setReceta(receta);
        comentario.setUsuario(usuario);
        comentario.setComentario("Comentário sem valoração");
        comentario.setFecha(LocalDateTime.now());
        // Valoração fica null

        assertEquals("Comentário sem valoração", comentario.getComentario());
        assertNull(comentario.getValoracion());
    }

    @Test
    void testValoracionesExtremas() {
        // Testando valores extremos que podem ser válidos
        comentario.setValoracion(0);
        assertEquals(0, comentario.getValoracion());

        comentario.setValoracion(10);
        assertEquals(10, comentario.getValoracion());

        comentario.setValoracion(-1); // Valor negativo
        assertEquals(-1, comentario.getValoracion());
    }

    @Test
    void testComentarioVazio() {
        comentario.setComentario("");
        assertEquals("", comentario.getComentario());
    }
}