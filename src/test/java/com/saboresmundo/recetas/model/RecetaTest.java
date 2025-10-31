package com.saboresmundo.recetas.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecetaTest {

    private Receta receta;
    private Usuario usuario;
    private Pais pais;

    @BeforeEach
    void setUp() {
        receta = new Receta();
        usuario = new Usuario();
        usuario.setNombre("Chef João");
        pais = new Pais();
        pais.setNombre("Brasil");
    }

    @Test
    void testConstructorVacio() {
        assertNotNull(receta);
        assertNull(receta.getId());
        assertNull(receta.getTitulo());
        assertNull(receta.getDescripcion());
        assertNull(receta.getDuracionMinutos());
        assertNull(receta.getDificultad());
        assertNull(receta.getValoracion());
        assertNull(receta.getFotoUrl());
        assertNull(receta.getEstado());
        assertNull(receta.getFechaPublicacion());
        assertNull(receta.getPais());
        assertNull(receta.getUsuario());
        assertNotNull(receta.getPasos());
        assertNotNull(receta.getRecetaIngredientes());
        assertNotNull(receta.getComentarios());
    }

    @Test
    void testConstructorConParametros() {
        String titulo = "Feijoada Brasileña";
        String descripcion = "Plato típico brasileño";
        Integer duracion = 180;
        String dificultad = "MEDIA";
        Float valoracion = 4.5f;
        String fotoUrl = "feijoada.jpg";
        String estado = "PUBLICADA";
        LocalDateTime fechaPublicacion = LocalDateTime.now();

        Receta recetaConParametros = new Receta(titulo, descripcion, duracion, dificultad,
                valoracion, fotoUrl, estado, fechaPublicacion, pais, usuario);

        assertEquals(titulo, recetaConParametros.getTitulo());
        assertEquals(descripcion, recetaConParametros.getDescripcion());
        assertEquals(duracion, recetaConParametros.getDuracionMinutos());
        assertEquals(dificultad, recetaConParametros.getDificultad());
        assertEquals(valoracion, recetaConParametros.getValoracion());
        assertEquals(fotoUrl, recetaConParametros.getFotoUrl());
        assertEquals(estado, recetaConParametros.getEstado());
        assertEquals(fechaPublicacion, recetaConParametros.getFechaPublicacion());
        assertEquals(pais, recetaConParametros.getPais());
        assertEquals(usuario, recetaConParametros.getUsuario());
    }

    @Test
    void testSettersEGetters() {
        String titulo = "Paella Valenciana";
        String descripcion = "Plato español tradicional";
        Integer duracion = 45;
        String dificultad = "ALTA";
        Float valoracion = 4.8f;
        String fotoUrl = "paella.jpg";
        String estado = "BORRADOR";
        LocalDateTime fechaPublicacion = LocalDateTime.of(2023, 12, 25, 14, 30);

        receta.setTitulo(titulo);
        receta.setDescripcion(descripcion);
        receta.setDuracionMinutos(duracion);
        receta.setDificultad(dificultad);
        receta.setValoracion(valoracion);
        receta.setFotoUrl(fotoUrl);
        receta.setEstado(estado);
        receta.setFechaPublicacion(fechaPublicacion);
        receta.setPais(pais);
        receta.setUsuario(usuario);

        assertEquals(titulo, receta.getTitulo());
        assertEquals(descripcion, receta.getDescripcion());
        assertEquals(duracion, receta.getDuracionMinutos());
        assertEquals(dificultad, receta.getDificultad());
        assertEquals(valoracion, receta.getValoracion());
        assertEquals(fotoUrl, receta.getFotoUrl());
        assertEquals(estado, receta.getEstado());
        assertEquals(fechaPublicacion, receta.getFechaPublicacion());
        assertEquals(pais, receta.getPais());
        assertEquals(usuario, receta.getUsuario());
    }

    @Test
    void testSetIdParaPruebas() {
        Long id = 123L;
        receta.setId(id);
        assertEquals(id, receta.getId());
    }

    @Test
    void testListaPasos() {
        List<PasoReceta> pasos = new ArrayList<>();
        PasoReceta paso1 = new PasoReceta();
        PasoReceta paso2 = new PasoReceta();
        pasos.add(paso1);
        pasos.add(paso2);

        receta.setPasos(pasos);

        assertEquals(2, receta.getPasos().size());
        assertTrue(receta.getPasos().contains(paso1));
        assertTrue(receta.getPasos().contains(paso2));
    }

    @Test
    void testListaRecetaIngredientes() {
        List<RecetaIngrediente> ingredientes = new ArrayList<>();
        RecetaIngrediente ingrediente1 = new RecetaIngrediente();
        RecetaIngrediente ingrediente2 = new RecetaIngrediente();
        ingredientes.add(ingrediente1);
        ingredientes.add(ingrediente2);

        receta.setRecetaIngredientes(ingredientes);

        assertEquals(2, receta.getRecetaIngredientes().size());
        assertTrue(receta.getRecetaIngredientes().contains(ingrediente1));
        assertTrue(receta.getRecetaIngredientes().contains(ingrediente2));
    }

    @Test
    void testListaComentarios() {
        List<ComentarioReceta> comentarios = new ArrayList<>();
        ComentarioReceta comentario1 = new ComentarioReceta();
        ComentarioReceta comentario2 = new ComentarioReceta();
        comentarios.add(comentario1);
        comentarios.add(comentario2);

        receta.setComentarios(comentarios);

        assertEquals(2, receta.getComentarios().size());
        assertTrue(receta.getComentarios().contains(comentario1));
        assertTrue(receta.getComentarios().contains(comentario2));
    }

    @Test
    void testEqualsComMesmoObjeto() {
        assertTrue(receta.equals(receta));
    }

    @Test
    void testEqualsComObjetoNull() {
        assertFalse(receta.equals(null));
    }

    @Test
    void testEqualsComClasseDiferente() {
        assertFalse(receta.equals("string"));
    }

    @Test
    void testEqualsComIdNull() {
        Receta outraReceta = new Receta();
        assertTrue(receta.equals(outraReceta));
    }

    @Test
    void testEqualsComIdsIguais() {
        receta.setId(1L);
        Receta outraReceta = new Receta();
        outraReceta.setId(1L);

        assertTrue(receta.equals(outraReceta));
    }

    @Test
    void testEqualsComIdsDiferentes() {
        receta.setId(1L);
        Receta outraReceta = new Receta();
        outraReceta.setId(2L);

        assertFalse(receta.equals(outraReceta));
    }

    @Test
    void testHashCode() {
        receta.setId(1L);
        Receta outraReceta = new Receta();
        outraReceta.setId(1L);

        assertEquals(receta.hashCode(), outraReceta.hashCode());
    }

    @Test
    void testToString() {
        receta.setId(1L);
        receta.setTitulo("Lasagna");
        receta.setUsuario(usuario);
        receta.setPais(pais);

        String toString = receta.toString();

        assertTrue(toString.contains("Receta{"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("titulo='Lasagna'"));
        assertTrue(toString.contains("usuario=null")); // Usuario não tem ID definido
        assertTrue(toString.contains("pais=null")); // Pais não tem ID definido
    }

    @Test
    void testToStringComUsuarioNull() {
        receta.setId(1L);
        receta.setTitulo("Pizza");
        // usuario e pais ficam null

        String toString = receta.toString();

        assertTrue(toString.contains("Receta{"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("titulo='Pizza'"));
        assertTrue(toString.contains("usuario=null"));
        assertTrue(toString.contains("pais=null"));
    }

    @Test
    void testValoresLimiteBoundary() {
        String tituloMaximo = "A".repeat(150); // máximo 150 caracteres
        String dificultadMaxima = "A".repeat(50); // máximo 50 caracteres
        String estadoMaximo = "A".repeat(20); // máximo 20 caracteres
        String textoLongo = "Lorem ipsum ".repeat(100); // texto longo para campos text

        receta.setTitulo(tituloMaximo);
        receta.setDescripcion(textoLongo);
        receta.setDificultad(dificultadMaxima);
        receta.setEstado(estadoMaximo);
        receta.setFotoUrl(textoLongo);

        assertEquals(tituloMaximo, receta.getTitulo());
        assertEquals(textoLongo, receta.getDescripcion());
        assertEquals(dificultadMaxima, receta.getDificultad());
        assertEquals(estadoMaximo, receta.getEstado());
        assertEquals(textoLongo, receta.getFotoUrl());
    }

    @Test
    void testValoracionMinEMax() {
        // Testando valores extremos de valoração
        receta.setValoracion(0.0f);
        assertEquals(0.0f, receta.getValoracion());

        receta.setValoracion(5.0f);
        assertEquals(5.0f, receta.getValoracion());

        receta.setValoracion(2.5f);
        assertEquals(2.5f, receta.getValoracion());
    }

    @Test
    void testDuracionMinutos() {
        // Testando diferentes valores de duração
        receta.setDuracionMinutos(1);
        assertEquals(1, receta.getDuracionMinutos());

        receta.setDuracionMinutos(480); // 8 horas
        assertEquals(480, receta.getDuracionMinutos());

        receta.setDuracionMinutos(0);
        assertEquals(0, receta.getDuracionMinutos());
    }
}