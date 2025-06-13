package com.example.GestionTorneos.service;

import com.example.GestionTorneos.model.Torneo;
import com.example.GestionTorneos.repository.TorneoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TorneoService {

    @Autowired
    private TorneoRepository torneoRepository;

    public List<Torneo> listarTodos() {
        return torneoRepository.findAll();
    }

    public Torneo buscarPorId(Long id) {
        return torneoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado con id: " + id));
    }

    public Torneo crear(Torneo torneo) {
        return torneoRepository.save(torneo);
    }

    public Torneo actualizar(Long id, Torneo datosActualizados) {
        Torneo existente = buscarPorId(id);
        existente.setNombre(datosActualizados.getNombre());
        existente.setCategoria(datosActualizados.getCategoria());
        existente.setUbicacion(datosActualizados.getUbicacion());
        existente.setEquiposParticipantes(datosActualizados.getEquiposParticipantes());
        return torneoRepository.save(existente);
    }

    public void eliminar(Long id) {
        Torneo existente = buscarPorId(id);
        torneoRepository.delete(existente);
    }
}
