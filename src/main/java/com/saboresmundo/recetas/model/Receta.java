package com.saboresmundo.recetas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "recetas")
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "text")
    private String descripcion;

    @Column(name = "duracion_minutos")
    private Integer duracionMinutos;

    @Column(length = 50)
    private String dificultad;

    @Column
    private Float valoracion;

    @Column(name = "foto_url", columnDefinition = "text")
    private String fotoUrl;

    @Column(length = 20)
    private String estado;

    @Column
    private LocalDateTime fechaPublicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pais_id")
    private Pais pais;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PasoReceta> pasos = new ArrayList<>();

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecetaIngrediente> recetaIngredientes = new ArrayList<>();

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComentarioReceta> comentarios = new ArrayList<>();

    public Receta() {
    }

    public Receta(String titulo, String descripcion, Integer duracionMinutos, String dificultad, Float valoracion,
            String fotoUrl, String estado, LocalDateTime fechaPublicacion, Pais pais, Usuario usuario) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.duracionMinutos = duracionMinutos;
        this.dificultad = dificultad;
        this.valoracion = valoracion;
        this.fotoUrl = fotoUrl;
        this.estado = estado;
        this.fechaPublicacion = fechaPublicacion;
        this.pais = pais;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    /**
     * Solo para uso en pruebas unitarias
     */
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

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<PasoReceta> getPasos() {
        return pasos;
    }

    public void setPasos(List<PasoReceta> pasos) {
        this.pasos = pasos;
    }

    public List<RecetaIngrediente> getRecetaIngredientes() {
        return recetaIngredientes;
    }

    public void setRecetaIngredientes(List<RecetaIngrediente> recetaIngredientes) {
        this.recetaIngredientes = recetaIngredientes;
    }

    public List<ComentarioReceta> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioReceta> comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Receta receta = (Receta) o;
        return Objects.equals(id, receta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Receta{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", usuario=" + (usuario != null ? usuario.getId() : null) +
                ", pais=" + (pais != null ? pais.getId() : null) +
                '}';
    }
}
