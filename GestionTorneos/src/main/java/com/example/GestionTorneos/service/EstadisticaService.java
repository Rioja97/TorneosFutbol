package com.example.GestionTorneos.service;

import com.example.GestionTorneos.model.Estadistica;
import com.example.GestionTorneos.repository.EstadisticaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadisticaService {

    private final EstadisticaRepository estadisticaRepository;

    public EstadisticaService(EstadisticaRepository estadisticaRepository) {
        this.estadisticaRepository = estadisticaRepository;
    }

    public List<Estadistica> obtenerPorJugador(Long jugadorId) {
        return estadisticaRepository.findByJugadorId(jugadorId);
    }

    public List<Estadistica> obtenerPorJugadorYTorneo(Long jugadorId, Long torneoId) {
        return estadisticaRepository.findByJugadorIdAndPartido_Torneo_Id(jugadorId, torneoId);
    }
}
