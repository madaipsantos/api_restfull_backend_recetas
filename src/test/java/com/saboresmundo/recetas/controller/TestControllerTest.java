package com.saboresmundo.recetas.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testUser", roles = { "USER", "ADMIN" })
    void testAuth_WhenUserIsAuthenticated_ShouldReturnAuthenticationDetails() throws Exception {
        mockMvc.perform(get("/api/test/auth"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isAuthenticated").value(true))
                .andExpect(jsonPath("$.principal.username").value("testUser"))
                .andExpect(jsonPath("$.authorities", hasSize(2)))
                .andExpect(jsonPath("$.authorities", containsInAnyOrder("ROLE_USER", "ROLE_ADMIN")));
    }

    @Test
    void testAuth_WhenUserIsNotAuthenticated_ShouldReturnFalseAuthentication() throws Exception {
        mockMvc.perform(get("/api/test/auth"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isAuthenticated").value(false))
                .andExpect(jsonPath("$.authorities").isArray())
                .andExpect(jsonPath("$.authorities").isEmpty());
    }

    @Test
    @WithMockUser(username = "basicUser", roles = { "USER" })
    void testAuth_WithBasicUser_ShouldReturnCorrectAuthorities() throws Exception {
        mockMvc.perform(get("/api/test/auth"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isAuthenticated").value(true))
                .andExpect(jsonPath("$.principal.username").value("basicUser"))
                .andExpect(jsonPath("$.authorities", hasSize(1)))
                .andExpect(jsonPath("$.authorities", containsInAnyOrder("ROLE_USER")));
    }
}
