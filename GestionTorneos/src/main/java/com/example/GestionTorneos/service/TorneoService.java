package com.example.GestionTorneos.service;

import com.example.GestionTorneos.excepcion.CupoMaximoException;
import com.example.GestionTorneos.excepcion.EntidadRepetidaException;
import com.example.GestionTorneos.excepcion.NoNuloException;
import com.example.GestionTorneos.model.Equipo;
import com.example.GestionTorneos.model.Torneo;
import com.example.GestionTorneos.repository.TorneoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TorneoService {

    @Autowired
    private TorneoRepository torneoRepository;

    public List<Torneo> listarTodos() {
        return torneoRepository.findAll();
    }

    public Torneo buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un valor positivo.");
        }

        return torneoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado con id: " + id));
    }

    public Torneo crear(Torneo torneo) {
        validarLogicaNegocioCreacion(torneo);
        return torneoRepository.save(torneo);
    }

    public Torneo actualizar(Long id, Torneo datosActualizados) {
        Torneo existente = buscarPorId(id);
        validarLogicaNegocioActualizacion(datosActualizados, existente);

        existente.setNombre(datosActualizados.getNombre());
        existente.setCategoria(datosActualizados.getCategoria());
        existente.setEquiposParticipantes(datosActualizados.getEquiposParticipantes());
        existente.setCupo(datosActualizados.getCupo());

        return torneoRepository.save(existente);
    }

    public void eliminar(Long id) {
        Torneo existente = buscarPorId(id);
        torneoRepository.delete(existente);
    }

    private void validarLogicaNegocioCreacion(Torneo torneo) {
        if (torneo.getCupo() != null && torneo.getEquiposParticipantes() != null &&
                torneo.getEquiposParticipantes().size() > torneo.getCupo()) {
            throw new CupoMaximoException("El número de equipos excede el cupo permitido.");
        }

        if (torneoRepository.existsByNombreAndCategoria(
                torneo.getNombre(), torneo.getCategoria())) {
            throw new EntidadRepetidaException("Ya existe un torneo con ese nombre y categoría.");
        }

        if (torneo.getEquiposParticipantes() == null) {
            throw new NoNuloException("La lista de equipos participantes no puede ser nula.");
        }

        Set<Long> idsUnicos = torneo.getEquiposParticipantes().stream()
                .map(Equipo::getId)
                .collect(Collectors.toSet());

        if (idsUnicos.size() != torneo.getEquiposParticipantes().size()) {
            throw new EntidadRepetidaException("No puede haber equipos duplicados en el torneo.");
        }
    }

    private void validarLogicaNegocioActualizacion(Torneo actualizado, Torneo existente) {
        if (actualizado.getEquiposParticipantes() == null) {
            throw new NoNuloException("La lista de equipos participantes no puede ser nula.");
        }

        if (actualizado.getCupo() != null &&
                actualizado.getEquiposParticipantes().size() > actualizado.getCupo()) {
            throw new CupoMaximoException("La cantidad de equipos supera el nuevo cupo.");
        }

        Set<Long> idsUnicos = actualizado.getEquiposParticipantes().stream()
                .map(Equipo::getId)
                .collect(Collectors.toSet());

        if (idsUnicos.size() != actualizado.getEquiposParticipantes().size()) {
            throw new EntidadRepetidaException("No puede haber equipos duplicados en el torneo.");
        }
    }
}
