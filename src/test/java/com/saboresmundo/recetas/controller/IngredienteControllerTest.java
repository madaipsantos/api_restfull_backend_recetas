package com.saboresmundo.recetas.controller;

import com.saboresmundo.recetas.model.Ingrediente;
import com.saboresmundo.recetas.service.IngredienteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = IngredienteController.class, excludeFilters = {
        @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {
                com.saboresmundo.recetas.security.JwtAuthenticationFilter.class
        })
})
@AutoConfigureMockMvc(addFilters = false)
class IngredienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredienteService ingredienteService;

    @Test
    void deveCriarIngredienteComSucesso() throws Exception {
        Ingrediente ingrediente = new Ingrediente();
        Mockito.when(ingredienteService.crearIngrediente(Mockito.any())).thenReturn(ingrediente);

        mockMvc.perform(post("/api/ingredientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Arroz\"}"))
                .andExpect(status().isOk());
    }
}
