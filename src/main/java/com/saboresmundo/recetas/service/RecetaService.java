package com.saboresmundo.recetas.service;

import com.saboresmundo.recetas.dto.RecetaRequest;
import com.saboresmundo.recetas.dto.RecetaResponse;
import com.saboresmundo.recetas.model.Ingrediente;
import com.saboresmundo.recetas.model.Pais;
import com.saboresmundo.recetas.model.PasoReceta;
import com.saboresmundo.recetas.model.Receta;
import com.saboresmundo.recetas.model.RecetaIngrediente;
import com.saboresmundo.recetas.model.Usuario;
import com.saboresmundo.recetas.repository.IngredienteRepository;
import com.saboresmundo.recetas.repository.PaisRepository;
import com.saboresmundo.recetas.repository.PasoRecetaRepository;
import com.saboresmundo.recetas.repository.RecetaIngredienteRepository;
import com.saboresmundo.recetas.repository.RecetaRepository;
import com.saboresmundo.recetas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RecetaService {
    private static final Logger logger = LoggerFactory.getLogger(RecetaService.class);

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private RecetaIngredienteRepository recetaIngredienteRepository;

    @Autowired
    private PasoRecetaRepository pasoRecetaRepository;

    public List<Receta> listarRecetas(String filtro) {
        if (filtro != null && !filtro.isEmpty()) {
            return recetaRepository.findByTituloContainingIgnoreCase(filtro);
        }
        return recetaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Receta findById(Long id) {
        Optional<Receta> receta = recetaRepository.findById(id);
        if (receta.isPresent()) {
            Receta r = receta.get();
            // Forzar carga de relaciones lazy
            if (r.getUsuario() != null) {
                r.getUsuario().getNombre(); // Forzar carga de usuario
            }
            if (r.getPais() != null) {
                r.getPais().getNombre(); // Forzar carga de país
            }
            return r;
        }
        return null;
    }

    @Transactional
    public RecetaResponse crearReceta(RecetaRequest recetaRequest) {
        try {
            logger.info("Iniciando creación de receta: {}", recetaRequest.getTitulo());

            // Obtener el usuario autenticado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

            if (usuario == null) {
                logger.error("No se pudo encontrar el usuario con email: {}", email);
                return new RecetaResponse("Error: Usuario no encontrado");
            }

            // Crear receta
            Receta receta = new Receta();
            receta.setTitulo(recetaRequest.getTitulo());
            receta.setDescripcion(recetaRequest.getDescripcion());
            receta.setDuracionMinutos(recetaRequest.getDuracionMinutos());
            receta.setDificultad(recetaRequest.getDificultad());
            receta.setFotoUrl(recetaRequest.getFotoUrl());
            receta.setUsuario(usuario);
            receta.setFechaPublicacion(LocalDateTime.now());
            receta.setEstado("PUBLICADA");
            receta.setValoracion(0.0f); // Valoración inicial

            // Asignar país si se proporciona
            if (recetaRequest.getPaisId() != null) {
                Optional<Pais> pais = paisRepository.findById(recetaRequest.getPaisId());
                pais.ifPresent(receta::setPais);
            }

            // Guardar la receta primero
            receta = recetaRepository.save(receta);
            logger.info("Receta guardada con ID: {}", receta.getId());

            // Procesar ingredientes
            if (recetaRequest.getIngredientes() != null && !recetaRequest.getIngredientes().isEmpty()) {
                procesarIngredientes(receta, recetaRequest.getIngredientes());
            }

            // Procesar pasos
            if (recetaRequest.getPasos() != null && !recetaRequest.getPasos().isEmpty()) {
                procesarPasos(receta, recetaRequest.getPasos());
            }

            return new RecetaResponse(receta);
        } catch (Exception e) {
            logger.error("Error al crear receta", e);
            return new RecetaResponse("Error al crear receta: " + e.getMessage());
        }
    }

    private void procesarIngredientes(Receta receta, List<RecetaRequest.IngredienteRequest> ingredientesRequest) {
        for (RecetaRequest.IngredienteRequest ir : ingredientesRequest) {
            Ingrediente ingrediente = null;
            if (ir.getIngredienteId() != null) {
                ingrediente = ingredienteRepository.findById(ir.getIngredienteId()).orElse(null);
            } else if (ir.getNombre() != null && !ir.getNombre().trim().isEmpty()) {
                ingrediente = ingredienteRepository.findByNombreIgnoreCase(ir.getNombre())
                        .orElseGet(() -> {
                            Ingrediente nuevoIngrediente = new Ingrediente();
                            nuevoIngrediente.setNombre(ir.getNombre());
                            nuevoIngrediente.setDescripcion(ir.getDescripcion());
                            return ingredienteRepository.save(nuevoIngrediente);
                        });
            } else {
                // Si no hay nombre ni ingredienteId, omitir este ingrediente
                continue;
            }
            if (ingrediente != null) {
                RecetaIngrediente recetaIngrediente = new RecetaIngrediente();
                recetaIngrediente.setReceta(receta);
                recetaIngrediente.setIngrediente(ingrediente);
                recetaIngrediente.setCantidad(ir.getCantidad());
                recetaIngrediente.setUnidad(ir.getUnidad());
                recetaIngredienteRepository.save(recetaIngrediente);
                logger.info("Ingrediente agregado a receta: {}", ingrediente.getNombre());
            }
        }
    }

    private void procesarPasos(Receta receta, List<RecetaRequest.PasoRequest> pasosRequest) {
        for (RecetaRequest.PasoRequest pasoRequest : pasosRequest) {
            PasoReceta paso = new PasoReceta();
            paso.setReceta(receta);
            paso.setOrden(pasoRequest.getOrden());
            paso.setDescripcion(pasoRequest.getDescripcion());
            paso.setFotoUrl(pasoRequest.getFotoUrl());

            pasoRecetaRepository.save(paso);
            logger.info("Paso {} agregado a receta", paso.getOrden());
        }
    }

    @Transactional
    public RecetaResponse editarReceta(Long id, RecetaRequest recetaRequest) {
        try {
            Optional<Receta> existente = recetaRepository.findById(id);
            if (existente.isEmpty()) {
                return new RecetaResponse("Receta no encontrada");
            }

            Receta actual = existente.get();

            // Obtener el usuario autenticado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

            // Verificar que el usuario sea el propietario o admin
            if (usuario == null || (!actual.getUsuario().getId().equals(usuario.getId())
                    && !"ADMIN".equals(usuario.getRol()))) {
                return new RecetaResponse("No tienes permiso para editar esta receta");
            }

            // Actualizar campos
            actual.setTitulo(recetaRequest.getTitulo());
            actual.setDescripcion(recetaRequest.getDescripcion());
            actual.setDuracionMinutos(recetaRequest.getDuracionMinutos());
            actual.setDificultad(recetaRequest.getDificultad());
            actual.setFotoUrl(recetaRequest.getFotoUrl());

            // Actualizar país si se proporciona
            if (recetaRequest.getPaisId() != null) {
                Optional<Pais> pais = paisRepository.findById(recetaRequest.getPaisId());
                pais.ifPresent(actual::setPais);
            }

            // Guardar los cambios
            actual = recetaRepository.save(actual);

            // Actualizar ingredientes
            if (recetaRequest.getIngredientes() != null) {
                // Eliminar los ingredientes existentes
                recetaIngredienteRepository.deleteByReceta(actual);
                // Agregar los nuevos ingredientes
                procesarIngredientes(actual, recetaRequest.getIngredientes());
            }

            // Actualizar pasos
            if (recetaRequest.getPasos() != null) {
                // Eliminar los pasos existentes
                pasoRecetaRepository.deleteByReceta(actual);
                // Agregar los nuevos pasos
                procesarPasos(actual, recetaRequest.getPasos());
            }

            return new RecetaResponse(actual);
        } catch (Exception e) {
            logger.error("Error al editar receta", e);
            return new RecetaResponse("Error al editar receta: " + e.getMessage());
        }
    }

    @Transactional
    public RecetaResponse eliminarReceta(Long id) {
        try {
            Optional<Receta> existente = recetaRepository.findById(id);
            if (existente.isEmpty()) {
                return new RecetaResponse("Receta no encontrada");
            }

            Receta actual = existente.get();

            // Obtener el usuario autenticado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

            // Verificar que el usuario sea el propietario o admin
            if (usuario == null || (!actual.getUsuario().getId().equals(usuario.getId())
                    && !"ADMIN".equals(usuario.getRol()))) {
                return new RecetaResponse("No tienes permiso para eliminar esta receta");
            }

            // Eliminar los ingredientes y pasos relacionados
            recetaIngredienteRepository.deleteByReceta(actual);
            pasoRecetaRepository.deleteByReceta(actual);

            // Eliminar la receta
            recetaRepository.delete(actual);

            return new RecetaResponse("Receta eliminada correctamente");
        } catch (Exception e) {
            logger.error("Error al eliminar receta", e);
            return new RecetaResponse("Error al eliminar receta: " + e.getMessage());
        }
    }
}
