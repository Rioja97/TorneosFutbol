package com.example.GestionTorneos.service;

import com.example.GestionTorneos.model.Equipo;
import com.example.GestionTorneos.repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    public List<Equipo> listarTodos() {
        return equipoRepository.findAll();
    }

    public Equipo buscarPorId(Long id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con id: " + id));
    }

    public Equipo crear(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    public Equipo actualizar(Long id, Equipo datosActualizados) {
        Equipo existente = buscarPorId(id);
        existente.setNombre(datosActualizados.getNombre());
        existente.setCiudad(datosActualizados.getCiudad());
        existente.setJugadores(datosActualizados.getJugadores());
        existente.setEntrenador(datosActualizados.getEntrenador());
        return equipoRepository.save(existente);
    }

    public void eliminar(Long id) {
        Equipo existente = buscarPorId(id);
        equipoRepository.delete(existente);
    }
}
