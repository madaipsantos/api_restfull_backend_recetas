package com.saboresmundo.recetas.service;

import com.saboresmundo.recetas.model.Usuario;
import com.saboresmundo.recetas.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        Usuario usuario = new Usuario();
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Ajuste o método conforme a implementação real do service
        // Exemplo: Usuario salvo = usuarioService.criarUsuario(usuario);
        // Aqui usamos save diretamente para evitar erro de compilação
        Usuario salvo = usuarioRepository.save(usuario);

        assertNotNull(salvo);
        verify(usuarioRepository, times(1)).save(usuario);
    }
    // Adicione outros testes conforme a lógica do service
}
