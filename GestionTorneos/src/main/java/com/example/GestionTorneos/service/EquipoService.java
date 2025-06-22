package com.example.GestionTorneos.service;

import com.example.GestionTorneos.model.Equipo;
import com.example.GestionTorneos.model.Jugador;
import com.example.GestionTorneos.repository.EquipoRepository;
import com.example.GestionTorneos.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    public List<Equipo> listarTodos() {
        return equipoRepository.findAll();
    }

    public Equipo buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un valor positivo.");
        }

        return equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con id: " + id));
    }

    public Equipo crear(Equipo equipo) {
        validarEquipo(equipo);
        return equipoRepository.save(equipo);
    }

    public Equipo actualizar(Long id, Equipo datosActualizados) {
        Equipo equipoExistente = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        validarEquipo(datosActualizados);

        equipoExistente.setNombre(datosActualizados.getNombre());
        equipoExistente.setCiudad(datosActualizados.getCiudad());
        return equipoRepository.save(equipoExistente);
    }

    public void eliminar(Long id) {
        Equipo existente = buscarPorId(id);
        equipoRepository.delete(existente);
    }

    private void validarEquipo(Equipo equipo) {
        if (equipo == null) throw new IllegalArgumentException("El equipo no puede ser nulo.");

        if (equipo.getNombre() == null || equipo.getNombre().trim().isEmpty()) throw new IllegalArgumentException("El nombre del equipo es obligatorio.");

        if (equipo.getCiudad() == null || equipo.getCiudad().trim().isEmpty()) throw new IllegalArgumentException("La ciudad del equipo es obligatoria.");

        boolean existeEquipoDuplicado = equipoRepository.existsByNombreIgnoreCaseAndCiudadIgnoreCase(
                equipo.getNombre(), equipo.getCiudad());

        //if (existeEquipoDuplicado) throw new IllegalArgumentException("Ya existe un equipo con ese nombre en esa ciudad.");

        if (equipo.getJugadores() != null) {
            equipo.getJugadores().stream()
                    .filter(jugador -> jugador.getId() != null)
                    .filter(jugador -> jugadorRepository.existsByIdAndEquipoIsNotNull(jugador.getId()))
                    .findFirst()
                    .ifPresent(jugador -> {
                        throw new IllegalArgumentException("El jugador " + jugador.getNombre() + " ya pertenece a otro equipo.");
                    });
        }
    }
}
