package com.saboresmundo.recetas.controller;

import com.saboresmundo.recetas.repository.ComentarioRecetaRepository;

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

        @MockBean
        private ComentarioRecetaRepository comentarioRecetaRepository;

        @MockBean
        private com.saboresmundo.recetas.repository.RecetaRepository recetaRepository;

        @MockBean
        private com.saboresmundo.recetas.repository.UsuarioRepository usuarioRepository;

        @MockBean
        private com.saboresmundo.recetas.service.ComentarioService comentarioService;

        @Test
        void deveEditarComentarioComSucesso() throws Exception {
                Long recetaId = 1L;
                Long comentarioId = 10L;
                String payload = "{" +
                                "\"comentario\":\"Nuevo comentario editado\"," +
                                "\"valoracion\":5" +
                                "}";

                // Simular que la receta y el comentario existen
                com.saboresmundo.recetas.model.Receta receta = new com.saboresmundo.recetas.model.Receta();
                receta.setId(recetaId);
                com.saboresmundo.recetas.model.ComentarioReceta comentario = new com.saboresmundo.recetas.model.ComentarioReceta();
                comentario.setId(comentarioId);
                comentario.setReceta(receta);

                Mockito.when(recetaRepository.findById(recetaId)).thenReturn(java.util.Optional.of(receta));
                Mockito.when(comentarioRecetaRepository.findById(comentarioId))
                                .thenReturn(java.util.Optional.of(comentario));
                Mockito.when(comentarioService.editarComentario(comentarioId, "Nuevo comentario editado", 5))
                                .thenReturn(comentario);
                Mockito.when(comentarioRecetaRepository.findByReceta(receta))
                                .thenReturn(java.util.Collections.singletonList(comentario));

                mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .put("/api/recetas/" + recetaId + "/comentarios/" + comentarioId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload))
                                .andExpect(status().isOk());
        }

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
