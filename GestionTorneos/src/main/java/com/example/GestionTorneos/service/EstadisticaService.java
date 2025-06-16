package com.example.GestionTorneos.service;

import com.example.GestionTorneos.model.Estadistica;
import com.example.GestionTorneos.repository.EstadisticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadisticaService {

    @Autowired
    private EstadisticaRepository estadisticaRepository;

    public List<Estadistica> listarTodas() {
        return estadisticaRepository.findAll();
    }

    public Estadistica buscarPorId(Long id) {
        return estadisticaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estadística no encontrada con id: " + id));
    }

    public Estadistica crear(Estadistica estadistica) {
        return estadisticaRepository.save(estadistica);
    }

    public void eliminar(Long id) {
        Estadistica existente = buscarPorId(id);
        estadisticaRepository.delete(existente);
    }
}
