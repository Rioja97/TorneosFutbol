package com.example.GestionTorneos.service;

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
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + id));
    }

    public Jugador crear(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    public Jugador actualizar(Long id, Jugador datosActualizados) {
        Jugador existente = buscarPorId(id);
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
}
