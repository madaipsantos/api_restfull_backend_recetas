package com.saboresmundo.recetas.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "pasos_receta")
public class PasoReceta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receta_id")
    private Receta receta;

    @Column(nullable = false)
    private Integer orden;

    @Column(nullable = false, columnDefinition = "text")
    private String descripcion;

    @Column(name = "foto_url", columnDefinition = "text")
    private String fotoUrl;

    public PasoReceta() {
    }

    public PasoReceta(Receta receta, Integer orden, String descripcion, String fotoUrl) {
        this.receta = receta;
        this.orden = orden;
        this.descripcion = descripcion;
        this.fotoUrl = fotoUrl;
    }

    public Long getId() {
        return id;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PasoReceta that = (PasoReceta) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PasoReceta{" +
                "id=" + id +
                ", receta=" + (receta != null ? receta.getId() : null) +
                ", orden=" + orden +
                '}';
    }
}
