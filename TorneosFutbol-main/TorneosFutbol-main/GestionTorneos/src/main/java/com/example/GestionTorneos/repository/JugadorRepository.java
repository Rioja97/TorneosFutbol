package com.example.GestionTorneos.repository;

import com.example.GestionTorneos.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {
}
