package com.saboresmundo.recetas.dto;

import com.saboresmundo.recetas.model.Receta;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RecetaListItemResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private Integer duracionMinutos;
    private String dificultad;
    private Float valoracion;
    private String fotoUrl;
    private String fechaPublicacion;
    private String paisNombre;
    private String autorNombre;

    public RecetaListItemResponse(Receta receta) {
        this.id = receta.getId();
        this.titulo = receta.getTitulo();
        this.descripcion = receta.getDescripcion() != null
                ? (receta.getDescripcion().length() > 100 ? receta.getDescripcion().substring(0, 100) + "..."
                        : receta.getDescripcion())
                : null;
        this.duracionMinutos = receta.getDuracionMinutos();
        this.dificultad = receta.getDificultad();
        this.valoracion = receta.getValoracion();
        this.fotoUrl = receta.getFotoUrl();

        if (receta.getFechaPublicacion() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.fechaPublicacion = receta.getFechaPublicacion().format(formatter);
        }

        this.paisNombre = receta.getPais() != null ? receta.getPais().getNombre() : null;
        this.autorNombre = receta.getUsuario() != null ? receta.getUsuario().getNombre() : null;
    }

    public static List<RecetaListItemResponse> fromRecetas(List<Receta> recetas) {
        List<RecetaListItemResponse> response = new ArrayList<>();
        for (Receta receta : recetas) {
            response.add(new RecetaListItemResponse(receta));
        }
        return response;
    }

    // Getters y Setters
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

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getPaisNombre() {
        return paisNombre;
    }

    public void setPaisNombre(String paisNombre) {
        this.paisNombre = paisNombre;
    }

    public String getAutorNombre() {
        return autorNombre;
    }

    public void setAutorNombre(String autorNombre) {
        this.autorNombre = autorNombre;
    }
}