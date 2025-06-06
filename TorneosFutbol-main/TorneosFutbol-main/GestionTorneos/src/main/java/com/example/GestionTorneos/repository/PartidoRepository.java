package com.example.GestionTorneos.repository;

import com.example.GestionTorneos.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidoRepository extends JpaRepository<Partido, Long> {
}
