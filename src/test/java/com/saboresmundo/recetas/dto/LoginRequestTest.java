package com.saboresmundo.recetas.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe LoginRequest
 * 
 * Testa funcionalidades básicas de DTO sem validações Jakarta:
 * - Construtores (vazio e com parâmetros)
 * - Getters e setters
 * - Casos de uso típicos
 */
class LoginRequestTest {

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
    }

    @Test
    void testConstructorVazio() {
        // Verifica se o construtor vazio inicializa corretamente
        assertNotNull(loginRequest);
        assertNull(loginRequest.getEmail());
        assertNull(loginRequest.getPassword());
    }

    @Test
    void testConstructorComParametros() {
        // Teste do construtor com parâmetros
        String email = "user@example.com";
        String password = "password123";

        LoginRequest loginComParametros = new LoginRequest(email, password);

        assertNotNull(loginComParametros);
        assertEquals(email, loginComParametros.getEmail());
        assertEquals(password, loginComParametros.getPassword());
    }

    @Test
    void testSettersEGetters() {
        // Teste dos métodos getters e setters
        String email = "test@email.com";
        String password = "mySecretPassword";

        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        assertEquals(email, loginRequest.getEmail());
        assertEquals(password, loginRequest.getPassword());
    }

    @Test
    void testSetEmailNull() {
        // Teste com email null
        loginRequest.setEmail(null);
        assertNull(loginRequest.getEmail());
    }

    @Test
    void testSetPasswordNull() {
        // Teste com password null
        loginRequest.setPassword(null);
        assertNull(loginRequest.getPassword());
    }

    @Test
    void testSetEmailVazio() {
        // Teste com email vazio
        loginRequest.setEmail("");
        assertEquals("", loginRequest.getEmail());
    }

    @Test
    void testSetPasswordVazio() {
        // Teste com password vazio
        loginRequest.setPassword("");
        assertEquals("", loginRequest.getPassword());
    }

    @Test
    void testEmailsValidos() {
        // Teste com diferentes formatos de email válidos
        String[] emailsValidos = {
                "user@domain.com",
                "test.email@example.org",
                "user+tag@domain.co.uk",
                "123@numbers.com",
                "a@b.co"
        };

        for (String email : emailsValidos) {
            loginRequest.setEmail(email);
            assertEquals(email, loginRequest.getEmail());
        }
    }

    @Test
    void testPasswordsComDiferentesTamanhos() {
        // Teste com passwords de diferentes tamanhos
        String[] passwords = {
                "123", // Muito curta
                "123456", // Tamanho mínimo comum
                "password123", // Tamanho médio
                "A".repeat(50), // Longa
                "A".repeat(100) // Muito longa
        };

        for (String password : passwords) {
            loginRequest.setPassword(password);
            assertEquals(password, loginRequest.getPassword());
        }
    }

    @Test
    void testCaracteresEspeciaisEmEmail() {
        // Teste com caracteres especiais em email
        String emailComEspeciais = "user+test.123@domain-name.com";
        loginRequest.setEmail(emailComEspeciais);
        assertEquals(emailComEspeciais, loginRequest.getEmail());
    }

    @Test
    void testCaracteresEspeciaisEmPassword() {
        // Teste com caracteres especiais em password
        String passwordComEspeciais = "Pass@123!#$%";
        loginRequest.setPassword(passwordComEspeciais);
        assertEquals(passwordComEspeciais, loginRequest.getPassword());
    }

    @Test
    void testCasoUsoCompleto() {
        // Teste de um caso de uso completo
        String email = "usuario@recetas.com";
        String password = "MinhaSenhaSecreta123!";

        // Usando construtor com parâmetros
        LoginRequest login = new LoginRequest(email, password);

        // Verificando valores
        assertEquals(email, login.getEmail());
        assertEquals(password, login.getPassword());

        // Modificando valores via setters
        String novoEmail = "novousuario@recetas.com";
        String novaSenha = "NovaSenha456@";

        login.setEmail(novoEmail);
        login.setPassword(novaSenha);

        assertEquals(novoEmail, login.getEmail());
        assertEquals(novaSenha, login.getPassword());
    }

    @Test
    void testEmailsComEspacos() {
        // Teste com emails que podem ter espaços (caso edge)
        String emailComEspacos = " user@domain.com ";
        loginRequest.setEmail(emailComEspacos);
        assertEquals(emailComEspacos, loginRequest.getEmail());
    }

    @Test
    void testPasswordComEspacos() {
        // Teste com password que pode ter espaços
        String passwordComEspacos = " password with spaces ";
        loginRequest.setPassword(passwordComEspacos);
        assertEquals(passwordComEspacos, loginRequest.getPassword());
    }
}