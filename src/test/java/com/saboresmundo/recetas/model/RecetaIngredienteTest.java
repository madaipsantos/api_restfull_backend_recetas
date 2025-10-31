package com.saboresmundo.recetas.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecetaIngredienteTest {

    private RecetaIngrediente recetaIngrediente;
    private Receta receta;
    private Ingrediente ingrediente;

    @BeforeEach
    void setUp() {
        recetaIngrediente = new RecetaIngrediente();

        receta = new Receta();
        receta.setId(1L);
        receta.setTitulo("Paella Valenciana");

        ingrediente = new Ingrediente();
        ingrediente.setNombre("Arroz");
        ingrediente.setDescripcion("Arroz bomba especial para paella");
    }

    @Test
    void testConstructorVacio() {
        assertNotNull(recetaIngrediente);
        assertNull(recetaIngrediente.getId());
        assertNull(recetaIngrediente.getReceta());
        assertNull(recetaIngrediente.getIngrediente());
        assertNull(recetaIngrediente.getCantidad());
        assertNull(recetaIngrediente.getUnidad());
    }

    @Test
    void testConstructorConParametros() {
        String cantidad = "400";
        String unidad = "gramos";

        RecetaIngrediente recetaIngredienteConParametros = new RecetaIngrediente(receta, ingrediente, cantidad, unidad);

        assertEquals(receta, recetaIngredienteConParametros.getReceta());
        assertEquals(ingrediente, recetaIngredienteConParametros.getIngrediente());
        assertEquals(cantidad, recetaIngredienteConParametros.getCantidad());
        assertEquals(unidad, recetaIngredienteConParametros.getUnidad());
    }

    @Test
    void testSettersEGetters() {
        String cantidad = "2";
        String unidad = "tazas";

        recetaIngrediente.setReceta(receta);
        recetaIngrediente.setIngrediente(ingrediente);
        recetaIngrediente.setCantidad(cantidad);
        recetaIngrediente.setUnidad(unidad);

        assertEquals(receta, recetaIngrediente.getReceta());
        assertEquals(ingrediente, recetaIngrediente.getIngrediente());
        assertEquals(cantidad, recetaIngrediente.getCantidad());
        assertEquals(unidad, recetaIngrediente.getUnidad());
    }

    @Test
    void testCantidadesVariadas() {
        String[] cantidades = {
                "1",
                "2.5",
                "1/2",
                "3/4",
                "200",
                "1.25",
                "una pizca",
                "al gusto"
        };

        for (String cantidad : cantidades) {
            recetaIngrediente.setCantidad(cantidad);
            assertEquals(cantidad, recetaIngrediente.getCantidad());
        }
    }

    @Test
    void testUnidadesComunes() {
        String[] unidades = {
                "gramos",
                "kilogramos",
                "litros",
                "mililitros",
                "tazas",
                "cucharadas",
                "cucharaditas",
                "unidades",
                "dientes",
                "hojas",
                "ramas",
                "gotas"
        };

        for (String unidad : unidades) {
            recetaIngrediente.setUnidad(unidad);
            assertEquals(unidad, recetaIngrediente.getUnidad());
        }
    }

    @Test
    void testCantidadConEspacios() {
        String cantidad = "1 1/2";
        recetaIngrediente.setCantidad(cantidad);
        assertEquals(cantidad, recetaIngrediente.getCantidad());
    }

    @Test
    void testUnidadCompuesta() {
        String unidad = "cucharadas soperas";
        recetaIngrediente.setUnidad(unidad);
        assertEquals(unidad, recetaIngrediente.getUnidad());
    }

    @Test
    void testValoresVacios() {
        recetaIngrediente.setCantidad("");
        recetaIngrediente.setUnidad("");

        assertEquals("", recetaIngrediente.getCantidad());
        assertEquals("", recetaIngrediente.getUnidad());
    }

    @Test
    void testValoresNull() {
        recetaIngrediente.setCantidad(null);
        recetaIngrediente.setUnidad(null);

        assertNull(recetaIngrediente.getCantidad());
        assertNull(recetaIngrediente.getUnidad());
    }

    @Test
    void testEqualsComMesmoObjeto() {
        assertTrue(recetaIngrediente.equals(recetaIngrediente));
    }

    @Test
    void testEqualsComObjetoNull() {
        assertFalse(recetaIngrediente.equals(null));
    }

    @Test
    void testEqualsComClasseDiferente() {
        assertFalse(recetaIngrediente.equals("string"));
    }

    @Test
    void testEqualsComIdNull() {
        RecetaIngrediente outro = new RecetaIngrediente();
        assertTrue(recetaIngrediente.equals(outro));
    }

    @Test
    void testHashCode() {
        RecetaIngrediente ri1 = new RecetaIngrediente();
        RecetaIngrediente ri2 = new RecetaIngrediente();

        // Ambos com ID null devem ter o mesmo hashCode
        assertEquals(ri1.hashCode(), ri2.hashCode());
    }

    @Test
    void testToString() {
        recetaIngrediente.setReceta(receta);
        recetaIngrediente.setIngrediente(ingrediente);

        String toString = recetaIngrediente.toString();

        assertTrue(toString.contains("RecetaIngrediente{"));
        assertTrue(toString.contains("receta=1")); // ID da receta
        assertTrue(toString.contains("ingrediente=null")); // Ingrediente não tem ID definido
    }

    @Test
    void testToStringComRecetaIngredienteNull() {
        // receta e ingrediente ficam null
        String toString = recetaIngrediente.toString();

        assertTrue(toString.contains("RecetaIngrediente{"));
        assertTrue(toString.contains("receta=null"));
        assertTrue(toString.contains("ingrediente=null"));
    }

    @Test
    void testRecetaIngredienteCompleto() {
        // Configurar uma relação completa
        recetaIngrediente.setReceta(receta);
        recetaIngrediente.setIngrediente(ingrediente);
        recetaIngrediente.setCantidad("500");
        recetaIngrediente.setUnidad("gramos");

        // Verificações
        assertEquals(receta, recetaIngrediente.getReceta());
        assertEquals(ingrediente, recetaIngrediente.getIngrediente());
        assertEquals("500", recetaIngrediente.getCantidad());
        assertEquals("gramos", recetaIngrediente.getUnidad());
    }

    @Test
    void testIngredientesComCantidadesEspeciais() {
        // Testando casos especiais de quantidades
        String[][] casos = {
                { "sal", "una pizca", "pizca" },
                { "pimienta", "al gusto", "" },
                { "agua", "suficiente", "cantidad necesaria" },
                { "aceite", "un chorrito", "" },
                { "limón", "el jugo de 1", "unidad" },
                { "ajo", "2", "dientes" },
                { "perejil", "un puñado", "hojas" },
                { "mantequilla", "una nuez", "cantidad pequeña" }
        };

        for (String[] caso : casos) {
            Ingrediente ing = new Ingrediente(caso[0], "");
            recetaIngrediente.setIngrediente(ing);
            recetaIngrediente.setCantidad(caso[1]);
            recetaIngrediente.setUnidad(caso[2]);

            assertEquals(ing, recetaIngrediente.getIngrediente());
            assertEquals(caso[1], recetaIngrediente.getCantidad());
            assertEquals(caso[2], recetaIngrediente.getUnidad());
        }
    }

    @Test
    void testCantidadMaxima() {
        // Testando quantidade muito grande (máximo 100 caracteres)
        String cantidadLarga = "A".repeat(100);
        recetaIngrediente.setCantidad(cantidadLarga);
        assertEquals(cantidadLarga, recetaIngrediente.getCantidad());
    }

    @Test
    void testUnidadMaxima() {
        // Testando unidade muito grande (máximo 50 caracteres)
        String unidadLarga = "B".repeat(50);
        recetaIngrediente.setUnidad(unidadLarga);
        assertEquals(unidadLarga, recetaIngrediente.getUnidad());
    }

    @Test
    void testCantidadesNumericas() {
        // Testando diferentes formatos numéricos
        String[] cantidadesNumericas = {
                "0",
                "1",
                "10",
                "100",
                "0.5",
                "1.25",
                "2.75",
                "12.345"
        };

        for (String cantidad : cantidadesNumericas) {
            recetaIngrediente.setCantidad(cantidad);
            assertEquals(cantidad, recetaIngrediente.getCantidad());
        }
    }

    @Test
    void testUnidadesAbreviadas() {
        String[] unidadesAbreviadas = {
                "g",
                "kg",
                "ml",
                "l",
                "taza",
                "cdta",
                "cda",
                "oz",
                "lb",
                "pcs"
        };

        for (String unidad : unidadesAbreviadas) {
            recetaIngrediente.setUnidad(unidad);
            assertEquals(unidad, recetaIngrediente.getUnidad());
        }
    }
}