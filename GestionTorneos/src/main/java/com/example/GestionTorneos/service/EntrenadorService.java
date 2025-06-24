package com.example.GestionTorneos.service;

import com.example.GestionTorneos.excepcion.EntidadNoEncontradaException;
import com.example.GestionTorneos.excepcion.ValorPositivoException;
import com.example.GestionTorneos.model.Entrenador;
import com.example.GestionTorneos.model.Equipo;
import com.example.GestionTorneos.repository.EntrenadorRepository;
import com.example.GestionTorneos.repository.EquipoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntrenadorService {

    @Autowired
    private EntrenadorRepository entrenadorRepository;
    @Autowired
    private EquipoRepository equipoRepository;

    public List<Entrenador> listarTodos() {
        return entrenadorRepository.findAll();
    }

    public Entrenador buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new ValorPositivoException("El ID debe ser un valor positivo.");
        }
        return entrenadorRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Entrenador no encontrado con id: " + id));
    }

    @Transactional
    public Entrenador crear(Entrenador entrenador) {
        validarEntrenador(entrenador);

        Equipo equipo = entrenador.getEquipo();
        if (equipo != null) {
            Equipo equipoPersistido = equipoRepository.findById(equipo.getId())
                    .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
            equipoPersistido.setEntrenador(entrenador);
            entrenador.setEquipo(equipoPersistido);
        }

        return entrenadorRepository.save(entrenador);
    }

    public Entrenador actualizar(Long id, Entrenador datosActualizados) {
        Entrenador existente = buscarPorId(id);

        existente.setNombre(datosActualizados.getNombre());
        existente.setExperiencia(datosActualizados.getExperiencia());

        Equipo equipo = datosActualizados.getEquipo();
        existente.setEquipo(equipo);

        if (equipo != null) {
            equipo.setEntrenador(existente);
            equipoRepository.save(equipo);  // Guardar para persistir la relación
        }

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