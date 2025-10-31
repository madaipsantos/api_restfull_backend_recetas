package com.saboresmundo.recetas.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasoRecetaTest {

    private PasoReceta pasoReceta;
    private Receta receta;

    @BeforeEach
    void setUp() {
        pasoReceta = new PasoReceta();
        receta = new Receta();
        receta.setId(1L);
        receta.setTitulo("Lasagna Tradicional");
    }

    @Test
    void testConstructorVacio() {
        assertNotNull(pasoReceta);
        assertNull(pasoReceta.getId());
        assertNull(pasoReceta.getReceta());
        assertNull(pasoReceta.getOrden());
        assertNull(pasoReceta.getDescripcion());
        assertNull(pasoReceta.getFotoUrl());
    }

    @Test
    void testConstructorConParametros() {
        Integer orden = 1;
        String descripcion = "Precalentar el horno a 180°C";
        String fotoUrl = "paso1_horno.jpg";

        PasoReceta pasoConParametros = new PasoReceta(receta, orden, descripcion, fotoUrl);

        assertEquals(receta, pasoConParametros.getReceta());
        assertEquals(orden, pasoConParametros.getOrden());
        assertEquals(descripcion, pasoConParametros.getDescripcion());
        assertEquals(fotoUrl, pasoConParametros.getFotoUrl());
    }

    @Test
    void testSettersEGetters() {
        Integer orden = 2;
        String descripcion = "Cortar las verduras en cubos pequeños";
        String fotoUrl = "verduras_cortadas.jpg";

        pasoReceta.setReceta(receta);
        pasoReceta.setOrden(orden);
        pasoReceta.setDescripcion(descripcion);
        pasoReceta.setFotoUrl(fotoUrl);

        assertEquals(receta, pasoReceta.getReceta());
        assertEquals(orden, pasoReceta.getOrden());
        assertEquals(descripcion, pasoReceta.getDescripcion());
        assertEquals(fotoUrl, pasoReceta.getFotoUrl());
    }

    @Test
    void testOrdenesValidas() {
        // Testando diferentes valores de ordem
        pasoReceta.setOrden(1);
        assertEquals(1, pasoReceta.getOrden());

        pasoReceta.setOrden(10);
        assertEquals(10, pasoReceta.getOrden());

        pasoReceta.setOrden(99);
        assertEquals(99, pasoReceta.getOrden());
    }

    @Test
    void testDescripcionDetallada() {
        String descripcionLarga = "En una sartén grande, calentar 2 cucharadas de aceite de oliva a fuego medio. " +
                "Añadir la cebolla picada y cocinar durante 3-4 minutos hasta que esté transparente. " +
                "Agregar el ajo picado y cocinar 1 minuto más, removiendo constantemente para evitar que se queme. " +
                "Incorporar la carne molida y cocinar, desmenuzando con una cuchara de madera, hasta que esté " +
                "completamente dorada, aproximadamente 8-10 minutos.";

        pasoReceta.setDescripcion(descripcionLarga);
        assertEquals(descripcionLarga, pasoReceta.getDescripcion());
    }

    @Test
    void testFotoUrlVariadas() {
        String[] urlsVariadas = {
                "paso1.jpg",
                "https://example.com/images/cooking_step_2.png",
                "/static/images/recipe_steps/lasagna/step3.webp",
                "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQ...",
                "images/recetas/lasagna/paso_final.gif"
        };

        for (String url : urlsVariadas) {
            pasoReceta.setFotoUrl(url);
            assertEquals(url, pasoReceta.getFotoUrl());
        }
    }

    @Test
    void testPasoSinFoto() {
        pasoReceta.setReceta(receta);
        pasoReceta.setOrden(1);
        pasoReceta.setDescripcion("Paso sin foto");
        // fotoUrl fica null

        assertEquals("Paso sin foto", pasoReceta.getDescripcion());
        assertNull(pasoReceta.getFotoUrl());
    }

    @Test
    void testEqualsComMesmoObjeto() {
        assertTrue(pasoReceta.equals(pasoReceta));
    }

    @Test
    void testEqualsComObjetoNull() {
        assertFalse(pasoReceta.equals(null));
    }

    @Test
    void testEqualsComClasseDiferente() {
        assertFalse(pasoReceta.equals("string"));
    }

    @Test
    void testEqualsComIdNull() {
        PasoReceta outroPaso = new PasoReceta();
        assertTrue(pasoReceta.equals(outroPaso));
    }

    @Test
    void testHashCode() {
        PasoReceta paso1 = new PasoReceta();
        PasoReceta paso2 = new PasoReceta();

        // Ambos com ID null devem ter o mesmo hashCode
        assertEquals(paso1.hashCode(), paso2.hashCode());
    }

    @Test
    void testToString() {
        pasoReceta.setReceta(receta);
        pasoReceta.setOrden(3);

        String toString = pasoReceta.toString();

        assertTrue(toString.contains("PasoReceta{"));
        assertTrue(toString.contains("receta=1")); // ID da receta
        assertTrue(toString.contains("orden=3"));
    }

    @Test
    void testToStringComRecetaNull() {
        pasoReceta.setOrden(1);
        // receta fica null

        String toString = pasoReceta.toString();

        assertTrue(toString.contains("PasoReceta{"));
        assertTrue(toString.contains("receta=null"));
        assertTrue(toString.contains("orden=1"));
    }

    @Test
    void testPasosSequenciais() {
        // Criando uma sequência de passos
        String[][] pasosData = {
                { "1", "Preparar todos los ingredientes" },
                { "2", "Precalentar el horno a 180°C" },
                { "3", "Mezclar ingredientes secos" },
                { "4", "Agregar ingredientes húmedos" },
                { "5", "Hornear durante 45 minutos" }
        };

        for (String[] data : pasosData) {
            PasoReceta paso = new PasoReceta();
            paso.setReceta(receta);
            paso.setOrden(Integer.valueOf(data[0]));
            paso.setDescripcion(data[1]);

            assertEquals(Integer.valueOf(data[0]), paso.getOrden());
            assertEquals(data[1], paso.getDescripcion());
            assertEquals(receta, paso.getReceta());
        }
    }

    @Test
    void testOrdenCero() {
        pasoReceta.setOrden(0);
        assertEquals(0, pasoReceta.getOrden());
    }

    @Test
    void testOrdenNegativo() {
        // Testando valor negativo (pode não ser válido no negócio, mas tecnicamente
        // possível)
        pasoReceta.setOrden(-1);
        assertEquals(-1, pasoReceta.getOrden());
    }

    @Test
    void testDescripcionVacia() {
        pasoReceta.setDescripcion("");
        assertEquals("", pasoReceta.getDescripcion());
    }

    @Test
    void testFotoUrlVazia() {
        pasoReceta.setFotoUrl("");
        assertEquals("", pasoReceta.getFotoUrl());
    }

    @Test
    void testPasoCompleto() {
        // Configurar um paso completo
        pasoReceta.setReceta(receta);
        pasoReceta.setOrden(1);
        pasoReceta.setDescripcion("Lavar bem todos os legumes em água corrente");
        pasoReceta.setFotoUrl("legumes_limpos.jpg");

        // Verificações
        assertEquals(receta, pasoReceta.getReceta());
        assertEquals(1, pasoReceta.getOrden());
        assertEquals("Lavar bem todos os legumes em água corrente", pasoReceta.getDescripcion());
        assertEquals("legumes_limpos.jpg", pasoReceta.getFotoUrl());
    }

    @Test
    void testPasosComOrdemAlta() {
        // Testando passos com ordem alta (receitas muito detalhadas)
        pasoReceta.setOrden(25);
        pasoReceta.setDescripcion("Paso muito específico no final da receita");

        assertEquals(25, pasoReceta.getOrden());
        assertEquals("Paso muito específico no final da receita", pasoReceta.getDescripcion());
    }

    @Test
    void testDescripcionComCaracteresEspeciais() {
        String descripcionEspecial = "Añadir 2½ tazas de água à 180°C y mezclar con cuidado. " +
                "¡IMPORTANTE! No sobre-mezclar la masa.";

        pasoReceta.setDescripcion(descripcionEspecial);
        assertEquals(descripcionEspecial, pasoReceta.getDescripcion());
    }
}