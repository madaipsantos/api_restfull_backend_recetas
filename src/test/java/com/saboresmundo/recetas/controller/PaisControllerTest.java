package com.saboresmundo.recetas.controller;

import com.saboresmundo.recetas.model.Pais;
import com.saboresmundo.recetas.service.PaisService;
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

@WebMvcTest(value = PaisController.class, excludeFilters = {
        @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {
                com.saboresmundo.recetas.security.JwtAuthenticationFilter.class
        })
})
@AutoConfigureMockMvc(addFilters = false)
class PaisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaisService paisService;

    @Test
    void deveCriarPaisComSucesso() throws Exception {
        Pais pais = new Pais();
        Mockito.when(paisService.crearPais(Mockito.any())).thenReturn(pais);

        mockMvc.perform(post("/api/paises")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Brasil\"}"))
                .andExpect(status().isOk());
    }
}
