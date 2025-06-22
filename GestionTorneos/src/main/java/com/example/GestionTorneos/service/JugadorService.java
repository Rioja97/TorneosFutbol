package com.example.GestionTorneos.service;

import com.example.GestionTorneos.excepcion.EntidadNoEncontradaException;
import com.example.GestionTorneos.excepcion.EntidadRepetidaException;
import com.example.GestionTorneos.model.Jugador;
import com.example.GestionTorneos.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    public List<Jugador> listarTodos() {
        return jugadorRepository.findAll();
    }

    public Jugador buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un valor positivo.");
        }

        return jugadorRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Jugador no encontrado con id: " + id));
    }

    public Jugador crear(Jugador jugador) {
        validarLogicaNegocioCreacion(jugador);
        jugador.setEquipo(jugador.getEquipo());
        return jugadorRepository.save(jugador);
    }

    public Jugador actualizar(Long id, Jugador datosActualizados) {
        Jugador existente = buscarPorId(id);
        validarLogicaNegocioActualizacion(datosActualizados, existente);

        existente.setNombre(datosActualizados.getNombre());
        existente.setEdad(datosActualizados.getEdad());
        existente.setPosicion(datosActualizados.getPosicion());
        existente.setDorsal(datosActualizados.getDorsal());
        existente.setEquipo(datosActualizados.getEquipo());

        return jugadorRepository.save(existente);
    }

    public void eliminar(Long id) {
        Jugador existente = buscarPorId(id);
        jugadorRepository.delete(existente);
    }


    private void validarLogicaNegocioCreacion(Jugador jugador) {
        if (jugador.getEdad() < 15) {
            throw new IllegalArgumentException("El jugador debe tener al menos 15 años.");
        }

        if (jugadorRepository.existsByEquipoIdAndDorsal(
                jugador.getEquipo().getId(),
                jugador.getDorsal()
        )) {
            throw new EntidadRepetidaException("Ya existe un jugador con ese dorsal en el equipo.");
        }
    }

    private void validarLogicaNegocioActualizacion(Jugador actualizado, Jugador existente) {
        if (actualizado.getEdad() < 15) {
            throw new IllegalArgumentException("El jugador debe tener al menos 15 años.");
        }

        boolean dorsalOcupado = jugadorRepository.existsByEquipoIdAndDorsalAndIdNot(
                actualizado.getEquipo().getId(),
                actualizado.getDorsal(),
                existente.getId()
        );

        if (dorsalOcupado) {
            throw new EntidadRepetidaException("Ya existe otro jugador con ese dorsal en el equipo.");
        }
    }

}
