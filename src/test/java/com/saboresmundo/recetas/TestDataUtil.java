package com.saboresmundo.recetas;

import com.saboresmundo.recetas.model.Usuario;

public class TestDataUtil {
    public static Usuario criarUsuarioPadrao() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Usuário Teste");
        usuario.setEmail("teste@email.com");
        usuario.setPasswordHash("senha123");
        usuario.setRol("USUARIO");
        return usuario;
    }
    // Adicione métodos similares para outros modelos conforme necessário
}
