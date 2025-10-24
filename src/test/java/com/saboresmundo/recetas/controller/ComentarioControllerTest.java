package com.saboresmundo.recetas.controller;

import com.saboresmundo.recetas.model.ComentarioReceta;
import com.saboresmundo.recetas.service.ComentarioService;
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

@WebMvcTest(value = ComentarioController.class, excludeFilters = {
        @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {
                com.saboresmundo.recetas.security.JwtAuthenticationFilter.class
        })
})
@AutoConfigureMockMvc(addFilters = false)
class ComentarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComentarioService comentarioService;

    @Test
    void deveAdicionarComentarioComSucesso() throws Exception {
        ComentarioReceta comentario = new ComentarioReceta();
        // preencha os campos necessários do comentário

        Mockito.when(comentarioService.agregarComentario(Mockito.anyLong(), Mockito.any())).thenReturn(comentario);

        mockMvc.perform(post("/api/recetas/1/comentarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"contenido\":\"Muito bom!\"}"))
                .andExpect(status().isOk());
    }
}
