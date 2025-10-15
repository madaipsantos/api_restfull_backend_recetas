package com.saboresmundo.recetas.dto;

import com.saboresmundo.recetas.model.Receta;

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
    private String mensaje;

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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}