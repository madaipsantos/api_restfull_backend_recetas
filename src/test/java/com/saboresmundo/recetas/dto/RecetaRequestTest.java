package com.saboresmundo.recetas.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecetaRequestTest {

    private RecetaRequest recetaRequest;
    private Validator validator;

    @BeforeEach
    void setUp() {
        recetaRequest = new RecetaRequest();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testConstructorVacio() {
        assertNotNull(recetaRequest);
        assertNull(recetaRequest.getTitulo());
        assertNull(recetaRequest.getDescripcion());
        assertNull(recetaRequest.getDuracionMinutos());
        assertNull(recetaRequest.getDificultad());
        assertNull(recetaRequest.getFotoUrl());
        assertNull(recetaRequest.getPaisId());
        assertNull(recetaRequest.getIngredientes());
        assertNull(recetaRequest.getPasos());
    }

    @Test
    void testSettersEGetters() {
        String titulo = "Feijoada Brasileira";
        String descripcion = "Plato típico del Brasil";
        Integer duracion = 180;
        String dificultad = "MEDIA";
        String fotoUrl = "feijoada.jpg";
        Long paisId = 1L;

        recetaRequest.setTitulo(titulo);
        recetaRequest.setDescripcion(descripcion);
        recetaRequest.setDuracionMinutos(duracion);
        recetaRequest.setDificultad(dificultad);
        recetaRequest.setFotoUrl(fotoUrl);
        recetaRequest.setPaisId(paisId);

        assertEquals(titulo, recetaRequest.getTitulo());
        assertEquals(descripcion, recetaRequest.getDescripcion());
        assertEquals(duracion, recetaRequest.getDuracionMinutos());
        assertEquals(dificultad, recetaRequest.getDificultad());
        assertEquals(fotoUrl, recetaRequest.getFotoUrl());
        assertEquals(paisId, recetaRequest.getPaisId());
    }

    @Test
    void testSetListaIngredientes() {
        List<RecetaRequest.IngredienteRequest> ingredientes = new ArrayList<>();
        RecetaRequest.IngredienteRequest ingrediente1 = new RecetaRequest.IngredienteRequest();
        ingrediente1.setIngredienteId(1L);
        ingrediente1.setCantidad("2");
        ingrediente1.setUnidad("xícaras");
        ingredientes.add(ingrediente1);

        recetaRequest.setIngredientes(ingredientes);

        assertEquals(1, recetaRequest.getIngredientes().size());
        assertEquals(ingrediente1, recetaRequest.getIngredientes().get(0));
    }

    @Test
    void testSetListaPasos() {
        List<RecetaRequest.PasoRequest> pasos = new ArrayList<>();
        RecetaRequest.PasoRequest paso1 = new RecetaRequest.PasoRequest();
        paso1.setOrden(1);
        paso1.setDescripcion("Primeiro paso");
        pasos.add(paso1);

        recetaRequest.setPasos(pasos);

        assertEquals(1, recetaRequest.getPasos().size());
        assertEquals(paso1, recetaRequest.getPasos().get(0));
    }

    @Test
    void testValoresNumericos() {
        recetaRequest.setDuracionMinutos(0);
        assertEquals(0, recetaRequest.getDuracionMinutos());

        recetaRequest.setDuracionMinutos(1440); // 24 horas
        assertEquals(1440, recetaRequest.getDuracionMinutos());

        recetaRequest.setPaisId(999L);
        assertEquals(999L, recetaRequest.getPaisId());
    }

    @Test
    void testDificultadesValidas() {
        String[] dificultades = { "FACIL", "MEDIA", "DIFICIL" };

        for (String dificultad : dificultades) {
            recetaRequest.setDificultad(dificultad);
            assertEquals(dificultad, recetaRequest.getDificultad());
        }
    }

    // Testes para IngredienteRequest
    @Test
    void testIngredienteRequestConstructorVacio() {
        RecetaRequest.IngredienteRequest ingrediente = new RecetaRequest.IngredienteRequest();

        assertNotNull(ingrediente);
        assertNull(ingrediente.getIngredienteId());
        assertNull(ingrediente.getNombre());
        assertNull(ingrediente.getDescripcion());
        assertNull(ingrediente.getCantidad());
        assertNull(ingrediente.getUnidad());
    }

    @Test
    void testIngredienteRequestSettersEGetters() {
        RecetaRequest.IngredienteRequest ingrediente = new RecetaRequest.IngredienteRequest();

        Long id = 1L;
        String nombre = "Arroz";
        String descripcion = "Arroz blanco grano largo";
        String cantidad = "2";
        String unidad = "xícaras";

        ingrediente.setIngredienteId(id);
        ingrediente.setNombre(nombre);
        ingrediente.setDescripcion(descripcion);
        ingrediente.setCantidad(cantidad);
        ingrediente.setUnidad(unidad);

        assertEquals(id, ingrediente.getIngredienteId());
        assertEquals(nombre, ingrediente.getNombre());
        assertEquals(descripcion, ingrediente.getDescripcion());
        assertEquals(cantidad, ingrediente.getCantidad());
        assertEquals(unidad, ingrediente.getUnidad());
    }

    @Test
    void testIngredienteRequestValidacaoCantidadVazia() {
        RecetaRequest.IngredienteRequest ingrediente = new RecetaRequest.IngredienteRequest();
        ingrediente.setCantidad("");
        ingrediente.setUnidad("gramos");

        Set<ConstraintViolation<RecetaRequest.IngredienteRequest>> violations = validator.validate(ingrediente);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cantidad")));
    }

    @Test
    void testIngredienteRequestValidacaoUnidadVazia() {
        RecetaRequest.IngredienteRequest ingrediente = new RecetaRequest.IngredienteRequest();
        ingrediente.setCantidad("100");
        ingrediente.setUnidad("");

        Set<ConstraintViolation<RecetaRequest.IngredienteRequest>> violations = validator.validate(ingrediente);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("unidad")));
    }

    @Test
    void testIngredienteRequestValidacaoNomeMuitoGrande() {
        RecetaRequest.IngredienteRequest ingrediente = new RecetaRequest.IngredienteRequest();
        ingrediente.setNombre("A".repeat(101)); // Mais que 100 caracteres
        ingrediente.setCantidad("100");
        ingrediente.setUnidad("gramos");

        Set<ConstraintViolation<RecetaRequest.IngredienteRequest>> violations = validator.validate(ingrediente);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre") &&
                (v.getMessage().contains("size") || v.getMessage().contains("tamaño"))));
    }

    @Test
    void testIngredienteRequestValidacaoDescricaoMuitoGrande() {
        RecetaRequest.IngredienteRequest ingrediente = new RecetaRequest.IngredienteRequest();
        ingrediente.setDescripcion("A".repeat(501)); // Mais que 500 caracteres
        ingrediente.setCantidad("100");
        ingrediente.setUnidad("gramos");

        Set<ConstraintViolation<RecetaRequest.IngredienteRequest>> violations = validator.validate(ingrediente);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("descripcion") &&
                (v.getMessage().contains("size") || v.getMessage().contains("tamaño"))));
    }

    // Testes para PasoRequest
    @Test
    void testPasoRequestConstructorVacio() {
        RecetaRequest.PasoRequest paso = new RecetaRequest.PasoRequest();

        assertNotNull(paso);
        assertNull(paso.getOrden());
        assertNull(paso.getDescripcion());
        assertNull(paso.getFotoUrl());
    }

    @Test
    void testPasoRequestSettersEGetters() {
        RecetaRequest.PasoRequest paso = new RecetaRequest.PasoRequest();

        Integer orden = 1;
        String descripcion = "Lave o arroz em água corrente";
        String fotoUrl = "paso1.jpg";

        paso.setOrden(orden);
        paso.setDescripcion(descripcion);
        paso.setFotoUrl(fotoUrl);

        assertEquals(orden, paso.getOrden());
        assertEquals(descripcion, paso.getDescripcion());
        assertEquals(fotoUrl, paso.getFotoUrl());
    }

    @Test
    void testPasoRequestValidacaoOrdenNull() {
        RecetaRequest.PasoRequest paso = new RecetaRequest.PasoRequest();
        paso.setOrden(null);
        paso.setDescripcion("Descripción del paso");

        Set<ConstraintViolation<RecetaRequest.PasoRequest>> violations = validator.validate(paso);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("orden")));
    }

    @Test
    void testPasoRequestValidacaoDescripcionVazia() {
        RecetaRequest.PasoRequest paso = new RecetaRequest.PasoRequest();
        paso.setOrden(1);
        paso.setDescripcion("");

        Set<ConstraintViolation<RecetaRequest.PasoRequest>> violations = validator.validate(paso);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("descripcion")));
    }

    @Test
    void testPasoRequestValidacaoDescripcionNull() {
        RecetaRequest.PasoRequest paso = new RecetaRequest.PasoRequest();
        paso.setOrden(1);
        paso.setDescripcion(null);

        Set<ConstraintViolation<RecetaRequest.PasoRequest>> violations = validator.validate(paso);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("descripcion")));
    }

    @Test
    void testPasoRequestValidacaoComDadosValidos() {
        RecetaRequest.PasoRequest paso = new RecetaRequest.PasoRequest();
        paso.setOrden(1);
        paso.setDescripcion("Descripción válida del paso");
        paso.setFotoUrl("paso.jpg");

        Set<ConstraintViolation<RecetaRequest.PasoRequest>> violations = validator.validate(paso);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testIngredienteRequestComDadosValidos() {
        RecetaRequest.IngredienteRequest ingrediente = new RecetaRequest.IngredienteRequest();
        ingrediente.setIngredienteId(1L);
        ingrediente.setNombre("Arroz");
        ingrediente.setDescripcion("Arroz integral");
        ingrediente.setCantidad("200");
        ingrediente.setUnidad("gramos");

        Set<ConstraintViolation<RecetaRequest.IngredienteRequest>> violations = validator.validate(ingrediente);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testRecetaCompleta() {
        // Configurar receta principal
        recetaRequest.setTitulo("Arroz con Pollo");
        recetaRequest.setDescripcion("Plato tradicional latinoamericano");
        recetaRequest.setDuracionMinutos(45);
        recetaRequest.setDificultad("MEDIA");
        recetaRequest.setFotoUrl("arroz_pollo.jpg");
        recetaRequest.setPaisId(1L);

        // Configurar ingredientes
        List<RecetaRequest.IngredienteRequest> ingredientes = new ArrayList<>();
        RecetaRequest.IngredienteRequest arroz = new RecetaRequest.IngredienteRequest();
        arroz.setIngredienteId(1L);
        arroz.setCantidad("2");
        arroz.setUnidad("xícaras");
        ingredientes.add(arroz);

        RecetaRequest.IngredienteRequest pollo = new RecetaRequest.IngredienteRequest();
        pollo.setNombre("Pollo");
        pollo.setDescripcion("Pollo cortado en trozos");
        pollo.setCantidad("500");
        pollo.setUnidad("gramos");
        ingredientes.add(pollo);

        recetaRequest.setIngredientes(ingredientes);

        // Configurar pasos
        List<RecetaRequest.PasoRequest> pasos = new ArrayList<>();
        RecetaRequest.PasoRequest paso1 = new RecetaRequest.PasoRequest();
        paso1.setOrden(1);
        paso1.setDescripcion("Lavar el arroz");
        pasos.add(paso1);

        RecetaRequest.PasoRequest paso2 = new RecetaRequest.PasoRequest();
        paso2.setOrden(2);
        paso2.setDescripcion("Cocinar el pollo");
        paso2.setFotoUrl("cocinando_pollo.jpg");
        pasos.add(paso2);

        recetaRequest.setPasos(pasos);

        // Verificações
        assertEquals("Arroz con Pollo", recetaRequest.getTitulo());
        assertEquals(2, recetaRequest.getIngredientes().size());
        assertEquals(2, recetaRequest.getPasos().size());
        assertEquals("Lavar el arroz", recetaRequest.getPasos().get(0).getDescripcion());
        assertEquals("gramos", recetaRequest.getIngredientes().get(1).getUnidad());
    }
}