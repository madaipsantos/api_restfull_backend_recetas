package com.saboresmundo.recetas.service;

import com.saboresmundo.recetas.dto.RegisterRequest;
import com.saboresmundo.recetas.model.Usuario;
import com.saboresmundo.recetas.repository.UsuarioRepository;
import com.saboresmundo.recetas.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @Test
    void testLoginComCredenciaisInvalidas() {
        AuthService.LoginRequest loginRequest = new AuthService.LoginRequest("email@naoexiste.com", "senhaerrada");
        when(usuarioRepository.findByEmail(loginRequest.getEmail())).thenReturn(java.util.Optional.empty());

        var response = authService.login(loginRequest);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Credenciales inválidas", response.getBody());
        verify(usuarioRepository, times(1)).findByEmail(loginRequest.getEmail());
    }

    @Test
    void testLoginComSucesso() {
        AuthService.LoginRequest loginRequest = new AuthService.LoginRequest("test@email.com", "senha");
        Usuario usuario = new Usuario();
        usuario.setEmail("test@email.com");
        usuario.setPasswordHash("hash");
        usuario.setNombre("Test");
        when(usuarioRepository.findByEmail(loginRequest.getEmail())).thenReturn(java.util.Optional.of(usuario));
        when(passwordEncoder.matches(loginRequest.getPassword(), usuario.getPasswordHash())).thenReturn(true);
        when(jwtUtil.generateToken(usuario.getEmail())).thenReturn("token123");

        var response = authService.login(loginRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(((java.util.Map<?, ?>) response.getBody()).containsKey("token"));
        assertEquals("test@email.com", ((java.util.Map<?, ?>) response.getBody()).get("email"));
        assertEquals("Test", ((java.util.Map<?, ?>) response.getBody()).get("nombre"));
        verify(usuarioRepository, times(1)).findByEmail(loginRequest.getEmail());
        verify(jwtUtil, times(1)).generateToken(usuario.getEmail());
    }

    @Test
    void testRegisterComEmailJaRegistrado() {
        RegisterRequest request = new RegisterRequest();
        request.setNombre("Test");
        request.setEmail("test@email.com");
        request.setPassword("password");
        when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(java.util.Optional.of(new Usuario()));

        var response = authService.register(request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("El email ya está registrado", response.getBody());
        verify(usuarioRepository, times(1)).findByEmail(request.getEmail());
    }

    // Removed testAutenticacaoUsuarioInvalido as it calls a non-existent method
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    public AuthServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUsuario() {
        RegisterRequest request = new RegisterRequest();
        request.setNombre("Test");
        request.setEmail("test@email.com");
        request.setPassword("password");

        when(passwordEncoder.encode(anyString())).thenReturn("hashed");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertDoesNotThrow(() -> authService.register(request));
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
}
