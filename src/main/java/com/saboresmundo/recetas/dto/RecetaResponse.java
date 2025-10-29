package com.saboresmundo.recetas.dto;

import com.saboresmundo.recetas.model.Receta;
import com.saboresmundo.recetas.model.RecetaIngrediente;
import com.saboresmundo.recetas.model.PasoReceta;
import java.util.List;
import java.util.stream.Collectors;

public class RecetaResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private Integer duracionMinutos;
    private String dificultad;
    private Float valoracion;
    private String fotoUrl;
    private String estado;
    private String fechaPublicacion;
    private String pais;
    private String autor;
    private Long autorId;
    private String mensaje;
    private List<IngredienteDTO> ingredientes;
    private List<PasoDTO> pasos;
    private List<ComentarioDTO> comentarios;

    public RecetaResponse(Receta receta) {
        this.id = receta.getId();
        this.titulo = receta.getTitulo();
        this.descripcion = receta.getDescripcion();
        this.duracionMinutos = receta.getDuracionMinutos();
        this.dificultad = receta.getDificultad();
        this.valoracion = receta.getValoracion();
        this.fotoUrl = receta.getFotoUrl();
        this.estado = receta.getEstado();
        this.fechaPublicacion = receta.getFechaPublicacion() != null ? receta.getFechaPublicacion().toString() : null;
        this.pais = receta.getPais() != null ? receta.getPais().getNombre() : null;
        this.autor = receta.getUsuario() != null ? receta.getUsuario().getNombre() : null;
        this.autorId = receta.getUsuario() != null ? receta.getUsuario().getId() : null;

        if (receta.getRecetaIngredientes() != null) {
            this.ingredientes = receta.getRecetaIngredientes().stream()
                    .map(IngredienteDTO::new)
                    .collect(Collectors.toList());
        }

        if (receta.getPasos() != null) {
            this.pasos = receta.getPasos().stream()
                    .map(PasoDTO::new)
                    .sorted((p1, p2) -> p1.getOrden().compareTo(p2.getOrden()))
                    .collect(Collectors.toList());
        }

        if (receta.getComentarios() != null) {
            this.comentarios = receta.getComentarios().stream()
                    .map(ComentarioDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }

    public static class ComentarioDTO {
        private Long id;
        private String usuario;
        private String texto;
        private Integer valoracion;
        private String fecha;

        public ComentarioDTO(com.saboresmundo.recetas.model.ComentarioReceta comentario) {
            this.id = comentario.getId();
            this.usuario = comentario.getUsuario() != null ? comentario.getUsuario().getNombre() : "";
            this.texto = comentario.getComentario();
            this.valoracion = comentario.getValoracion();
            this.fecha = comentario.getFecha() != null ? comentario.getFecha().toString() : null;
        }

        public Long getId() {
            return id;
        }

        public String getUsuario() {
            return usuario;
        }

        public String getTexto() {
            return texto;
        }

        public Integer getValoracion() {
            return valoracion;
        }

        public String getFecha() {
            return fecha;
        }
    }

    public RecetaResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public Float getValoracion() {
        return valoracion;
    }

    public void setValoracion(Float valoracion) {
        this.valoracion = valoracion;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<IngredienteDTO> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteDTO> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<PasoDTO> getPasos() {
        return pasos;
    }

    public void setPasos(List<PasoDTO> pasos) {
        this.pasos = pasos;
    }

    public static class IngredienteDTO {
        private Long id;
        private String nombre;
        private String descripcion;
        private String cantidad;
        private String unidad;

        public IngredienteDTO(RecetaIngrediente ri) {
            this.id = ri.getIngrediente().getId();
            this.nombre = ri.getIngrediente().getNombre();
            this.cantidad = ri.getCantidad();
            this.unidad = ri.getUnidad();
        }

        public Long getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public String getCantidad() {
            return cantidad;
        }

        public String getUnidad() {
            return unidad;
        }
    }

    public static class PasoDTO {
        private Integer orden;
        private String descripcion;
        private String fotoUrl;

        public PasoDTO(PasoReceta paso) {
            this.orden = paso.getOrden();
            this.descripcion = paso.getDescripcion();
            this.fotoUrl = paso.getFotoUrl();
        }

        public Integer getOrden() {
            return orden;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public String getFotoUrl() {
            return fotoUrl;
        }
    }
}