package com.saboresmundo.recetas.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredienteTest {

    private Ingrediente ingrediente;

    @BeforeEach
    void setUp() {
        ingrediente = new Ingrediente();
    }

    @Test
    void testConstructorVacio() {
        assertNotNull(ingrediente);
        assertNull(ingrediente.getId());
        assertNull(ingrediente.getNombre());
        assertNull(ingrediente.getDescripcion());
    }

    @Test
    void testConstructorConParametros() {
        String nombre = "Arroz";
        String descripcion = "Arroz blanco grano largo";

        Ingrediente ingredienteConParametros = new Ingrediente(nombre, descripcion);

        assertEquals(nombre, ingredienteConParametros.getNombre());
        assertEquals(descripcion, ingredienteConParametros.getDescripcion());
    }

    @Test
    void testSettersEGetters() {
        String nombre = "Frijoles";
        String descripcion = "Frijoles negros cocidos";

        ingrediente.setNombre(nombre);
        ingrediente.setDescripcion(descripcion);

        assertEquals(nombre, ingrediente.getNombre());
        assertEquals(descripcion, ingrediente.getDescripcion());
    }

    @Test
    void testEqualsComMesmoObjeto() {
        assertTrue(ingrediente.equals(ingrediente));
    }

    @Test
    void testEqualsComObjetoNull() {
        assertFalse(ingrediente.equals(null));
    }

    @Test
    void testEqualsComClasseDiferente() {
        assertFalse(ingrediente.equals("string"));
    }

    @Test
    void testEqualsComIdNull() {
        Ingrediente outroIngrediente = new Ingrediente();
        assertTrue(ingrediente.equals(outroIngrediente));
    }

    @Test
    void testHashCode() {
        Ingrediente ingrediente1 = new Ingrediente();
        Ingrediente ingrediente2 = new Ingrediente();

        // Ambos com ID null devem ter o mesmo hashCode
        assertEquals(ingrediente1.hashCode(), ingrediente2.hashCode());
    }

    @Test
    void testToString() {
        ingrediente.setNombre("Tomate");

        String toString = ingrediente.toString();

        assertTrue(toString.contains("Ingrediente{"));
        assertTrue(toString.contains("nombre='Tomate'"));
    }

    @Test
    void testValoresLimiteBoundary() {
        // Testando valores nos limites das constraints
        String nombreMaximo = "A".repeat(100); // máximo 100 caracteres
        String descripcionLarga = "Lorem ipsum dolor sit amet. ".repeat(50); // texto longo

        ingrediente.setNombre(nombreMaximo);
        ingrediente.setDescripcion(descripcionLarga);

        assertEquals(nombreMaximo, ingrediente.getNombre());
        assertEquals(descripcionLarga, ingrediente.getDescripcion());
    }

    @Test
    void testIngredientesComuns() {
        String[][] ingredientesComuns = {
                { "Arroz", "Cereal básico para muchas comidas" },
                { "Pollo", "Carne blanca rica en proteínas" },
                { "Tomate", "Fruta/vegetal rico en licopeno" },
                { "Cebolla", "Bulbo aromático usado como condimento" },
                { "Ajo", "Bulbo pequeño con sabor intenso" },
                { "Aceite de oliva", "Grasa líquida extraída de aceitunas" },
                { "Sal", "Condimento mineral básico" },
                { "Pimienta negra", "Especia picante molida" }
        };

        for (String[] datos : ingredientesComuns) {
            Ingrediente ing = new Ingrediente(datos[0], datos[1]);

            assertEquals(datos[0], ing.getNombre());
            assertEquals(datos[1], ing.getDescripcion());
        }
    }

    @Test
    void testDescripcionVaciaENull() {
        ingrediente.setNombre("Sal");
        ingrediente.setDescripcion("");
        assertEquals("", ingrediente.getDescripcion());

        ingrediente.setDescripcion(null);
        assertNull(ingrediente.getDescripcion());
    }

    @Test
    void testNombreComCaracteresEspeciais() {
        String[] nombresEspeciales = {
                "Açúcar",
                "Pimentão",
                "Coração de frango",
                "Água-de-coco",
                "Óleo de coco",
                "Café (grão moído)"
        };

        for (String nombre : nombresEspeciales) {
            ingrediente.setNombre(nombre);
            assertEquals(nombre, ingrediente.getNombre());
        }
    }

    @Test
    void testDescripcionDetallada() {
        String descripcionDetallada = "Arroz integral de grano largo, rico en fibras y vitaminas del complejo B, " +
                "cultivado sin pesticidas en campos orgánicos certificados. Ideal para dietas saludables y " +
                "personas con diabetes, ya que tiene un índice glucémico más bajo que el arroz blanco convencional.";

        ingrediente.setNombre("Arroz integral orgánico");
        ingrediente.setDescripcion(descripcionDetallada);

        assertEquals("Arroz integral orgánico", ingrediente.getNombre());
        assertEquals(descripcionDetallada, ingrediente.getDescripcion());
    }

    @Test
    void testIngredienteMinimalista() {
        ingrediente.setNombre("Sal");
        // Descripción queda null

        assertEquals("Sal", ingrediente.getNombre());
        assertNull(ingrediente.getDescripcion());

        String toString = ingrediente.toString();
        assertTrue(toString.contains("nombre='Sal'"));
    }
}