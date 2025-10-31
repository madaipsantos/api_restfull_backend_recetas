package com.saboresmundo.recetas.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaisTest {

    private Pais pais;

    @BeforeEach
    void setUp() {
        pais = new Pais();
    }

    @Test
    void testConstructorVacio() {
        assertNotNull(pais);
        assertNull(pais.getId());
        assertNull(pais.getNombre());
        assertNull(pais.getCodigoIso());
        assertNull(pais.getContinente());
    }

    @Test
    void testConstructorConParametros() {
        String nombre = "Brasil";
        String codigoIso = "BR";
        String continente = "América del Sur";

        Pais paisConParametros = new Pais(nombre, codigoIso, continente);

        assertEquals(nombre, paisConParametros.getNombre());
        assertEquals(codigoIso, paisConParametros.getCodigoIso());
        assertEquals(continente, paisConParametros.getContinente());
    }

    @Test
    void testSettersEGetters() {
        String nombre = "Argentina";
        String codigoIso = "AR";
        String continente = "América del Sur";

        pais.setNombre(nombre);
        pais.setCodigoIso(codigoIso);
        pais.setContinente(continente);

        assertEquals(nombre, pais.getNombre());
        assertEquals(codigoIso, pais.getCodigoIso());
        assertEquals(continente, pais.getContinente());
    }

    @Test
    void testEqualsComMesmoObjeto() {
        assertTrue(pais.equals(pais));
    }

    @Test
    void testEqualsComObjetoNull() {
        assertFalse(pais.equals(null));
    }

    @Test
    void testEqualsComClasseDiferente() {
        assertFalse(pais.equals("string"));
    }

    @Test
    void testEqualsComIdNull() {
        Pais outroPais = new Pais();
        assertTrue(pais.equals(outroPais));
    }

    @Test
    void testHashCode() {
        Pais pais1 = new Pais();
        Pais pais2 = new Pais();

        // Ambos com ID null devem ter o mesmo hashCode
        assertEquals(pais1.hashCode(), pais2.hashCode());
    }

    @Test
    void testToString() {
        pais.setNombre("México");
        pais.setCodigoIso("MX");
        pais.setContinente("América del Norte");

        String toString = pais.toString();

        assertTrue(toString.contains("Pais{"));
        assertTrue(toString.contains("nombre='México'"));
        assertTrue(toString.contains("codigoIso='MX'"));
        assertTrue(toString.contains("continente='América del Norte'"));
    }

    @Test
    void testValoresLimiteBoundary() {
        // Testando valores nos limites das constraints
        String nombreMaximo = "A".repeat(100); // máximo 100 caracteres
        String codigoIsoMaximo = "A".repeat(10); // máximo 10 caracteres
        String continenteMaximo = "A".repeat(50); // máximo 50 caracteres

        pais.setNombre(nombreMaximo);
        pais.setCodigoIso(codigoIsoMaximo);
        pais.setContinente(continenteMaximo);

        assertEquals(nombreMaximo, pais.getNombre());
        assertEquals(codigoIsoMaximo, pais.getCodigoIso());
        assertEquals(continenteMaximo, pais.getContinente());
    }

    @Test
    void testCodigosIsoReais() {
        // Testando com códigos ISO reais
        String[][] paisesReais = {
                { "Brasil", "BR", "América del Sur" },
                { "Estados Unidos", "US", "América del Norte" },
                { "Francia", "FR", "Europa" },
                { "Japón", "JP", "Asia" },
                { "Australia", "AU", "Oceanía" },
                { "Sudáfrica", "ZA", "África" }
        };

        for (String[] dadosPais : paisesReais) {
            Pais paisReal = new Pais(dadosPais[0], dadosPais[1], dadosPais[2]);

            assertEquals(dadosPais[0], paisReal.getNombre());
            assertEquals(dadosPais[1], paisReal.getCodigoIso());
            assertEquals(dadosPais[2], paisReal.getContinente());
        }
    }

    @Test
    void testCodigoIsoFormatoValido() {
        // Códigos ISO de 2 caracteres (mais comuns)
        pais.setCodigoIso("BR");
        assertEquals("BR", pais.getCodigoIso());

        // Códigos ISO de 3 caracteres
        pais.setCodigoIso("BRA");
        assertEquals("BRA", pais.getCodigoIso());

        // Código com números (caso especial)
        pais.setCodigoIso("US1");
        assertEquals("US1", pais.getCodigoIso());
    }

    @Test
    void testContinentesValidos() {
        String[] continentesValidos = {
                "África",
                "América del Norte",
                "América del Sur",
                "Asia",
                "Europa",
                "Oceanía",
                "Antártida"
        };

        for (String continente : continentesValidos) {
            pais.setContinente(continente);
            assertEquals(continente, pais.getContinente());
        }
    }

    @Test
    void testNombrePaisComCaracteresEspeciais() {
        // Testando nomes com caracteres especiais e acentos
        String[] nomesEspeciais = {
                "Côte d'Ivoire",
                "São Tomé e Príncipe",
                "Burkina Faso",
                "Bosnia y Herzegovina",
                "República Democrática del Congo"
        };

        for (String nome : nomesEspeciais) {
            pais.setNombre(nome);
            assertEquals(nome, pais.getNombre());
        }
    }

    @Test
    void testCamposVazios() {
        pais.setNombre("");
        pais.setCodigoIso("");
        pais.setContinente("");

        assertEquals("", pais.getNombre());
        assertEquals("", pais.getCodigoIso());
        assertEquals("", pais.getContinente());
    }
}