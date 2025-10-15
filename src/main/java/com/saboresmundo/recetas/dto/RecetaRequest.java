package com.saboresmundo.recetas.dto;

import java.util.List;

public class RecetaRequest {
    private String titulo;
    private String descripcion;
    private Integer duracionMinutos;
    private String dificultad;
    private String fotoUrl;
    private Long paisId;
    private List<IngredienteRequest> ingredientes;
    private List<PasoRequest> pasos;

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

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Long getPaisId() {
        return paisId;
    }

    public void setPaisId(Long paisId) {
        this.paisId = paisId;
    }

    public List<IngredienteRequest> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteRequest> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<PasoRequest> getPasos() {
        return pasos;
    }

    public void setPasos(List<PasoRequest> pasos) {
        this.pasos = pasos;
    }

    public static class IngredienteRequest {
        private String nombre;
        private String cantidad;
        private String unidad;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCantidad() {
            return cantidad;
        }

        public void setCantidad(String cantidad) {
            this.cantidad = cantidad;
        }

        public String getUnidad() {
            return unidad;
        }

        public void setUnidad(String unidad) {
            this.unidad = unidad;
        }
    }

    public static class PasoRequest {
        private Integer orden;
        private String descripcion;
        private String fotoUrl;

        public Integer getOrden() {
            return orden;
        }

        public void setOrden(Integer orden) {
            this.orden = orden;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getFotoUrl() {
            return fotoUrl;
        }

        public void setFotoUrl(String fotoUrl) {
            this.fotoUrl = fotoUrl;
        }
    }
}