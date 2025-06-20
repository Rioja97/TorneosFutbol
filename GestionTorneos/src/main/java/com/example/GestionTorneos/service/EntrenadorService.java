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
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un valor positivo.");
        }
        return entrenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado con id: " + id));
    }

    public Entrenador crear(Entrenador entrenador) {
        validarEntrenador(entrenador);
        return entrenadorRepository.save(entrenador);
    }

    public Entrenador actualizar(Long id, Entrenador datosActualizados) {
        Entrenador existente = buscarPorId(id);

        existente.setNombre(datosActualizados.getNombre());
        existente.setExperiencia(datosActualizados.getExperiencia());
        existente.setEquipo(datosActualizados.getEquipo());

        validarEntrenador(existente);

        return entrenadorRepository.save(existente);
    }

    public void eliminar(Long id) {
        Entrenador existente = buscarPorId(id);
        entrenadorRepository.delete(existente);
    }

    private void validarEntrenador(Entrenador entrenador) {
        if (entrenador.getEquipo() != null) {
            Long entrenadorId = entrenador.getId() != null ? entrenador.getId() : -1L;
            boolean entrenadorAsignado = entrenadorRepository.existsByEquipoIdAndIdNot(
                    entrenador.getEquipo().getId(), entrenadorId
            );
            if (entrenadorAsignado) {
                throw new IllegalArgumentException("El entrenador ya está asignado a otro equipo.");
            }
        }
    }
}