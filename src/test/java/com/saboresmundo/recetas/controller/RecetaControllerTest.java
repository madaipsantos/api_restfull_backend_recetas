
package com.saboresmundo.recetas.controller;

import com.saboresmundo.recetas.dto.RecetaResponse;
import com.saboresmundo.recetas.service.RecetaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = RecetaController.class, excludeFilters = {
                @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {
                                com.saboresmundo.recetas.security.JwtAuthenticationFilter.class
                })
})
@AutoConfigureMockMvc(addFilters = false)
class RecetaControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private RecetaService recetaService;

        @Test
        void deveCriarRecetaComSucesso() throws Exception {
                // Mock do contexto de segurança
                org.springframework.security.core.Authentication authentication = Mockito
                                .mock(org.springframework.security.core.Authentication.class);
                Mockito.when(authentication.getName()).thenReturn("usuario@email.com");
                org.springframework.security.core.context.SecurityContext securityContext = Mockito
                                .mock(org.springframework.security.core.context.SecurityContext.class);
                Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
                org.springframework.security.core.context.SecurityContextHolder.setContext(securityContext);

                // Mock do usuário retornado pelo repository
                com.saboresmundo.recetas.model.Usuario usuario = new com.saboresmundo.recetas.model.Usuario();
                usuario.setEmail("usuario@email.com");
                usuario.setNombre("Usuário Teste");
                // Mock movido para baixo

                String payload = "{" +
                                "\"titulo\":\"Feijoada\"," +
                                "\"descripcion\":\"Prato típico brasileiro\"," +
                                "\"duracionMinutos\":60," +
                                "\"dificultad\":\"Média\"," +
                                "\"fotoUrl\":\"http://imagem.com/feijoada.jpg\"," +
                                "\"paisId\":1," +
                                "\"ingredientes\":[]," +
                                "\"pasos\":[]" +
                                "}";

                RecetaResponse response = new RecetaResponse("Receta criada com sucesso");
                response.setId(1L); // Definindo o ID para simular sucesso
                Mockito.when(recetaService.crearReceta(any())).thenReturn(response);

                mockMvc.perform(post("/api/recetas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload))
                                .andExpect(status().isCreated());
        }
}
