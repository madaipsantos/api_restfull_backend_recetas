package com.saboresmundo.recetas.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "paises")
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(name = "codigo_iso", unique = true, length = 10)
    private String codigoIso;

    @Column(length = 50)
    private String continente;

    public Pais() {
    }

    public Pais(String nombre, String codigoIso, String continente) {
        this.nombre = nombre;
        this.codigoIso = codigoIso;
        this.continente = continente;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoIso() {
        return codigoIso;
    }

    public void setCodigoIso(String codigoIso) {
        this.codigoIso = codigoIso;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pais pais = (Pais) o;
        return Objects.equals(id, pais.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pais{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", codigoIso='" + codigoIso + '\'' +
                ", continente='" + continente + '\'' +
                '}';
    }
}
