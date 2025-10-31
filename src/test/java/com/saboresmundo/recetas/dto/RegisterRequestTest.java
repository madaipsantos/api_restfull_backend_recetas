package com.saboresmundo.recetas.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestTest {

    private RegisterRequest registerRequest;
    private Validator validator;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testConstructorVacio() {
        assertNotNull(registerRequest);
        assertNull(registerRequest.getNombre());
        assertNull(registerRequest.getEmail());
        assertNull(registerRequest.getPassword());
        assertNull(registerRequest.getFoto());
    }

    @Test
    void testSettersEGetters() {
        String nombre = "João Silva";
        String email = "joao@example.com";
        String password = "password123";
        String foto = "foto_perfil.jpg";

        registerRequest.setNombre(nombre);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setFoto(foto);

        assertEquals(nombre, registerRequest.getNombre());
        assertEquals(email, registerRequest.getEmail());
        assertEquals(password, registerRequest.getPassword());
        assertEquals(foto, registerRequest.getFoto());
    }

    @Test
    void testValidacaoComDadosValidos() {
        registerRequest.setNombre("Maria Santos");
        registerRequest.setEmail("maria@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFoto("maria.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertTrue(violations.isEmpty(), "Não deveria haver violações com dados válidos");
    }

    @Test
    void testValidacaoNomeVazio() {
        registerRequest.setNombre("");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @Test
    void testValidacaoNomeNull() {
        registerRequest.setNombre(null);
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @Test
    void testValidacaoNomeMuitoPequeno() {
        registerRequest.setNombre("A"); // Menos que 2 caracteres
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre") &&
                (v.getMessage().contains("size") || v.getMessage().contains("tamaño"))));
    }

    @Test
    void testValidacaoNomeMuitoGrande() {
        registerRequest.setNombre("A".repeat(101)); // Mais que 100 caracteres
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre") &&
                (v.getMessage().contains("size") || v.getMessage().contains("tamaño"))));
    }

    @Test
    void testValidacaoEmailInvalido() {
        registerRequest.setNombre("João Silva");
        registerRequest.setEmail("email_invalido");
        registerRequest.setPassword("password123");
        registerRequest.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email") &&
                (v.getMessage().contains("email") || v.getMessage().contains("correo")
                        || v.getMessage().contains("válido"))));
    }

    @Test
    void testValidacaoEmailNull() {
        registerRequest.setNombre("João Silva");
        registerRequest.setEmail(null);
        registerRequest.setPassword("password123");
        registerRequest.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testValidacaoEmailVazio() {
        registerRequest.setNombre("João Silva");
        registerRequest.setEmail("");
        registerRequest.setPassword("password123");
        registerRequest.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testValidacaoEmailMuitoGrande() {
        String emailLongo = "a".repeat(140) + "@example.com"; // Mais que 150 caracteres
        registerRequest.setNombre("João Silva");
        registerRequest.setEmail(emailLongo);
        registerRequest.setPassword("password123");
        registerRequest.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email") &&
                (v.getMessage().contains("size") || v.getMessage().contains("tamaño"))));
    }

    @Test
    void testValidacaoPasswordMuitoPequena() {
        registerRequest.setNombre("João Silva");
        registerRequest.setEmail("joao@example.com");
        registerRequest.setPassword("12345"); // Menos que 6 caracteres
        registerRequest.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password") &&
                (v.getMessage().contains("size") || v.getMessage().contains("tamaño"))));
    }

    @Test
    void testValidacaoPasswordMuitoGrande() {
        registerRequest.setNombre("João Silva");
        registerRequest.setEmail("joao@example.com");
        registerRequest.setPassword("A".repeat(101)); // Mais que 100 caracteres
        registerRequest.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password") &&
                (v.getMessage().contains("size") || v.getMessage().contains("tamaño"))));
    }

    @Test
    void testValidacaoPasswordNull() {
        registerRequest.setNombre("João Silva");
        registerRequest.setEmail("joao@example.com");
        registerRequest.setPassword(null);
        registerRequest.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void testValidacaoPasswordVazia() {
        registerRequest.setNombre("João Silva");
        registerRequest.setEmail("joao@example.com");
        registerRequest.setPassword("");
        registerRequest.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void testValidacaoFotoNull() {
        registerRequest.setNombre("João Silva");
        registerRequest.setEmail("joao@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFoto(null);

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("foto")));
    }

    @Test
    void testValidacaoFotoVazia() {
        registerRequest.setNombre("João Silva");
        registerRequest.setEmail("joao@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFoto("");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("foto")));
    }

    @Test
    void testValidacaoNomeTamanhoMinimo() {
        registerRequest.setNombre("Ab"); // Exatamente 2 caracteres (mínimo)
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertTrue(violations.isEmpty(), "Nome com 2 caracteres deveria ser válido");
    }

    @Test
    void testValidacaoNomeTamanhoMaximo() {
        registerRequest.setNombre("A".repeat(100)); // Exatamente 100 caracteres (máximo)
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertTrue(violations.isEmpty(), "Nome com 100 caracteres deveria ser válido");
    }

    @Test
    void testValidacaoPasswordTamanhoMinimo() {
        registerRequest.setNombre("João Silva");
        registerRequest.setEmail("joao@example.com");
        registerRequest.setPassword("123456"); // Exatamente 6 caracteres (mínimo)
        registerRequest.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertTrue(violations.isEmpty(), "Password com 6 caracteres deveria ser válida");
    }

    @Test
    void testValidacaoPasswordTamanhoMaximo() {
        registerRequest.setNombre("João Silva");
        registerRequest.setEmail("joao@example.com");
        registerRequest.setPassword("A".repeat(100)); // Exatamente 100 caracteres (máximo)
        registerRequest.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);

        assertTrue(violations.isEmpty(), "Password com 100 caracteres deveria ser válida");
    }

    @Test
    void testEmailsValidos() {
        String[] emailsValidos = {
                "test@example.com",
                "user.name@domain.com",
                "user+tag@domain.co.uk",
                "123@numbers.org",
                "a@b.co"
        };

        for (String email : emailsValidos) {
            registerRequest.setNombre("João Silva");
            registerRequest.setEmail(email);
            registerRequest.setPassword("password123");
            registerRequest.setFoto("foto.jpg");

            Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);
            assertTrue(violations.isEmpty(), "Email " + email + " deveria ser válido");
        }
    }
}