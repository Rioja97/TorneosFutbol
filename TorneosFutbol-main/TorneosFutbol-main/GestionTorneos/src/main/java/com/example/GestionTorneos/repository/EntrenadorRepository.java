package com.example.GestionTorneos.repository;

import com.example.GestionTorneos.model.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {
}
