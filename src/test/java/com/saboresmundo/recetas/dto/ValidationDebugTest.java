package com.saboresmundo.recetas.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class ValidationDebugTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidacaoNomeMuitoGrande() {
        RegisterRequest request = new RegisterRequest();
        request.setNombre("A".repeat(101)); // Mais que 100 caracteres
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setFoto("foto.jpg");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        System.out.println("Violations count: " + violations.size());
        for (ConstraintViolation<RegisterRequest> violation : violations) {
            System.out.println("Property: " + violation.getPropertyPath());
            System.out.println("Message: " + violation.getMessage());
            System.out.println("Invalid value: " + violation.getInvalidValue());
            System.out.println("---");
        }
    }
}