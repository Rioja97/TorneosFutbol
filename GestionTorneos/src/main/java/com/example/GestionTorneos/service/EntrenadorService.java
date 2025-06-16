package com.example.GestionTorneos.service;

import com.example.GestionTorneos.model.Entrenador;
import com.example.GestionTorneos.repository.EntrenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntrenadorService {

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    public List<Entrenador> listarTodos() {
        return entrenadorRepository.findAll();
    }

    public Entrenador buscarPorId(Long id) {
        return entrenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado con id: " + id));
    }

    public Entrenador crear(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    public Entrenador actualizar(Long id, Entrenador datosActualizados) {
        Entrenador existente = buscarPorId(id);
        existente.setNombre(datosActualizados.getNombre());
        existente.setExperiencia(datosActualizados.getExperiencia());
        existente.setEquipo(datosActualizados.getEquipo());
        return entrenadorRepository.save(existente);
    }

    public void eliminar(Long id) {
        Entrenador existente = buscarPorId(id);
        entrenadorRepository.delete(existente);
    }
}
