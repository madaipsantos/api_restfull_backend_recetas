package com.saboresmundo.recetas.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
    }

    @Test
    void testConstructorVacio() {
        assertNotNull(usuario);
        assertNull(usuario.getId());
        assertNull(usuario.getNombre());
        assertNull(usuario.getEmail());
        assertNull(usuario.getPasswordHash());
        assertNull(usuario.getFotoPerfil());
        assertNull(usuario.getRol());
        assertNull(usuario.getFoto());
    }

    @Test
    void testConstructorConParametros() {
        String nombre = "João Silva";
        String email = "joao@example.com";
        String passwordHash = "hashedPassword123";
        String fotoPerfil = "perfil.jpg";

        Usuario usuarioConParametros = new Usuario(nombre, email, passwordHash, fotoPerfil);

        assertEquals(nombre, usuarioConParametros.getNombre());
        assertEquals(email, usuarioConParametros.getEmail());
        assertEquals(passwordHash, usuarioConParametros.getPasswordHash());
        assertEquals(fotoPerfil, usuarioConParametros.getFotoPerfil());
    }

    @Test
    void testSettersEGetters() {
        String nombre = "Maria Santos";
        String email = "maria@example.com";
        String passwordHash = "password123hash";
        String fotoPerfil = "maria_perfil.jpg";
        String rol = "USUARIO";
        String foto = "maria_foto.jpg";

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPasswordHash(passwordHash);
        usuario.setFotoPerfil(fotoPerfil);
        usuario.setRol(rol);
        usuario.setFoto(foto);

        assertEquals(nombre, usuario.getNombre());
        assertEquals(email, usuario.getEmail());
        assertEquals(passwordHash, usuario.getPasswordHash());
        assertEquals(fotoPerfil, usuario.getFotoPerfil());
        assertEquals(rol, usuario.getRol());
        assertEquals(foto, usuario.getFoto());
    }

    @Test
    void testEqualsComMesmoObjeto() {
        assertTrue(usuario.equals(usuario));
    }

    @Test
    void testEqualsComObjetoNull() {
        assertFalse(usuario.equals(null));
    }

    @Test
    void testEqualsComClasseDiferente() {
        assertFalse(usuario.equals("string"));
    }

    @Test
    void testEqualsComIdNull() {
        Usuario outroUsuario = new Usuario();
        assertTrue(usuario.equals(outroUsuario));
    }

    @Test
    void testEqualsComMesmoId() {
        // Simulando que ambos têm o mesmo ID
        Usuario usuario1 = new Usuario("Nome1", "email1@test.com", "hash1", "foto1");
        Usuario usuario2 = new Usuario("Nome2", "email2@test.com", "hash2", "foto2");

        // Como não podemos definir ID diretamente e ambos têm ID null,
        // eles devem ser considerados iguais pelo método equals
        assertEquals(usuario1, usuario2);
    }

    @Test
    void testHashCode() {
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();

        // Ambos com ID null devem ter o mesmo hashCode
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }

    @Test
    void testToString() {
        usuario.setNombre("João");
        usuario.setEmail("joao@test.com");
        usuario.setRol("USUARIO");
        usuario.setFotoPerfil("foto.jpg");

        String toString = usuario.toString();

        assertTrue(toString.contains("Usuario{"));
        assertTrue(toString.contains("nombre='João'"));
        assertTrue(toString.contains("email='joao@test.com'"));
        assertTrue(toString.contains("rol='USUARIO'"));
        assertTrue(toString.contains("fotoPerfil='foto.jpg'"));
    }

    @Test
    void testValoresLimiteBoundary() {
        // Testando valores nos limites das constraints
        String nomeMaximo = "A".repeat(100); // máximo 100 caracteres
        String emailMaximo = "a".repeat(140) + "@test.com"; // máximo 150 caracteres
        String passwordLongo = "A".repeat(255); // máximo 255 caracteres
        String rolMaximo = "A".repeat(20); // máximo 20 caracteres

        usuario.setNombre(nomeMaximo);
        usuario.setEmail(emailMaximo);
        usuario.setPasswordHash(passwordLongo);
        usuario.setRol(rolMaximo);

        assertEquals(nomeMaximo, usuario.getNombre());
        assertEquals(emailMaximo, usuario.getEmail());
        assertEquals(passwordLongo, usuario.getPasswordHash());
        assertEquals(rolMaximo, usuario.getRol());
    }

    @Test
    void testCamposTextoLongo() {
        String textoLongo = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ".repeat(10);

        usuario.setFotoPerfil(textoLongo);
        usuario.setFoto(textoLongo);

        assertEquals(textoLongo, usuario.getFotoPerfil());
        assertEquals(textoLongo, usuario.getFoto());
    }
}