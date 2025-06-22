package com.example.GestionTorneos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Estadio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String nombre;

    @NotNull
    @Min(1000)
    private int capacidad;

    @OneToOne(mappedBy = "estadio")
    @JsonIgnore
    private Equipo equipo;

    public Estadio() {
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public @NotNull @Size(min = 1, max = 100) String getNombre() {
        return nombre;
    }
    public void setNombre(@NotNull @Size(min = 1, max = 100) String nombre) {
        this.nombre = nombre;
    }
    @NotNull
    @Min(1000)
    public int getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(@NotNull @Min(1000) int capacidad) {
        this.capacidad = capacidad;
    }
    public Equipo getEquipo() {
        return equipo;
    }
    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}
