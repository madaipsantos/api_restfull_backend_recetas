package com.saboresmundo.recetas.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.saboresmundo.recetas.model.Usuario;
import com.saboresmundo.recetas.repository.UsuarioRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Verificamos primero si es una ruta pública que no necesita autenticación
        String path = request.getRequestURI();
        if (path.startsWith("/api/auth/") || path.startsWith("/api/paises/")) {
            // Rutas públicas, continuamos sin validar token
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtUtil.getEmailFromToken(token);
        }

        if (email != null && jwtUtil.validateToken(token)) {
            // Buscar el usuario en la base de datos para obtener su rol
            Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
            if (usuario != null) {
                // Crear una lista de autoridades con el rol del usuario
                String rol = usuario.getRol();
                System.out.println("Usuario: " + usuario.getEmail() + ", Rol: " + rol); // Log para depuración

                List<SimpleGrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority(rol));

                // Crear token de autenticación con las autoridades del usuario
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        email, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                System.out.println("Autenticación establecida con autoridades: " + authorities); // Log para depuración
            } else {
                System.out.println("No se encontró el usuario con email: " + email); // Log para depuración
            }
            filterChain.doFilter(request, response);
        } else if (authHeader != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token JWT inválido o expirado");
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
