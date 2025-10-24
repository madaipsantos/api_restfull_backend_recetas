package com.saboresmundo.recetas.controller;

import com.saboresmundo.recetas.dto.LoginRequest;
import com.saboresmundo.recetas.dto.RegisterRequest;
import com.saboresmundo.recetas.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@WebMvcTest(value = AuthController.class, excludeFilters = {
        @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {
                com.saboresmundo.recetas.security.JwtAuthenticationFilter.class
        })
})
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    void deveRegistrarUsuarioComSucesso() throws Exception {
        RegisterRequest request = new RegisterRequest();
        // preencha os campos necessários do request

        Mockito.when(authService.register(Mockito.any())).thenReturn(null); // ajuste conforme retorno real

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"teste@email.com\",\"password\":\"senha123\",\"nombre\":\"Teste\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deveFazerLoginComSucesso() throws Exception {
        LoginRequest request = new LoginRequest();
        // preencha os campos necessários do request

        Mockito.when(authService.login(Mockito.any())).thenReturn(null); // ajuste conforme retorno real

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"teste@email.com\",\"password\":\"senha123\"}"))
                .andExpect(status().isOk());
    }
}
