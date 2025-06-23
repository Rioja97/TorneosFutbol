package com.example.GestionTorneos.service;

import com.example.GestionTorneos.excepcion.SinEstadisticasException;
import com.example.GestionTorneos.model.Estadistica;
import com.example.GestionTorneos.model.Jugador;
import com.example.GestionTorneos.model.Torneo;
import com.example.GestionTorneos.repository.EstadisticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadisticaService {

    private final EstadisticaRepository estadisticaRepository;
    @Autowired
    private JugadorService jugadorService;
    @Autowired
    private TorneoService torneoService;

    public EstadisticaService(EstadisticaRepository estadisticaRepository) {
        this.estadisticaRepository = estadisticaRepository;
    }

    public List<Estadistica> obtenerPorJugador(Long jugadorId) {
        List<Estadistica> estadisticas = estadisticaRepository.findByJugadorId(jugadorId);
        if(estadisticas.isEmpty()) throw new SinEstadisticasException("No existen estadisticas para el jugador");
        return estadisticas;
    }

    public List<Estadistica> obtenerPorJugadorYTorneo(Long jugadorId, Long torneoId) {
        List <Estadistica> estadistica = estadisticaRepository.findByJugadorIdAndPartido_Torneo_Id(jugadorId, torneoId);
        if (estadistica.isEmpty()) throw new SinEstadisticasException("No existen estadisticas para el jugador");
        return estadistica;
    }
}
