package com.example.GestionTorneos.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Torneo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(nullable = false)
    @Size(min = 2, max = 100)
    private String nombre;
    @NotNull
    @Column(nullable = false)
    @Size(min = 2, max = 100)
    private String categoria;

    @ManyToMany
    @JoinTable(
            name = "torneo_equipos",
            joinColumns = @JoinColumn(name = "torneo_id"),
            inverseJoinColumns = @JoinColumn(name = "equipo_id")
    )
    private List<Equipo> equiposParticipantes;

    @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL)
    private List<Partido> partidos;

    @NotNull
    @Size(min = 5, max = 20)
    private Integer cupo;

    private String ubicacion;

    public Torneo() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public List<Equipo> getEquiposParticipantes() {
        return equiposParticipantes;
    }
    public void setEquiposParticipantes(List<Equipo> equiposParticipantes) {
        this.equiposParticipantes = equiposParticipantes;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    public List<Partido> getPartidos() {
        return partidos;
    }
    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }
    public @NotNull @Size(min = 5, max = 20) Integer getCupo() {
        return cupo;
    }
    public void setCupo(@NotNull @Size(min = 5, max = 20) Integer cupo) {
        this.cupo = cupo;
    }
}