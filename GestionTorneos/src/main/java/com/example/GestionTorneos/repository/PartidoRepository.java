package com.example.GestionTorneos.repository;

import com.example.GestionTorneos.model.Equipo;
import com.example.GestionTorneos.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PartidoRepository extends JpaRepository<Partido, Long> {
    boolean existsByFechaAndEquipos(LocalDate fecha, Long equipo1, Long equipo2);
    boolean existsByFechaAndEquiposExcluyendoId(LocalDate fecha, Long equipo1, Long equipo2, Long excluirId);
}
