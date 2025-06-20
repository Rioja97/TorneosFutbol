package com.example.GestionTorneos.service;
import com.example.GestionTorneos.model.Partido;
import com.example.GestionTorneos.repository.PartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PartidoService {

    @Autowired
    private PartidoRepository partidoRepository;

    public List<Partido> listarTodos() {
        return partidoRepository.findAll();
    }

    public Partido buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un valor positivo.");
        }

        return partidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado con id: " + id));
    }

    public Partido crear(Partido partido) {
        validarLogicaNegocioCreacion(partido);
        return partidoRepository.save(partido);
    }

    public Partido actualizar(Long id, Partido datosActualizados) {
        Partido existente = buscarPorId(id);
        validarLogicaNegocioActualizacion(datosActualizados, existente);

        existente.setEquipoLocal(datosActualizados.getEquipoLocal());
        existente.setEquipoVisitante(datosActualizados.getEquipoVisitante());
        existente.setFecha(datosActualizados.getFecha());
        existente.setResultado(datosActualizados.getResultado());

        return partidoRepository.save(existente);
    }

    public void eliminar(Long id) {
        Partido existente = buscarPorId(id);
        partidoRepository.delete(existente);
    }

    private void validarLogicaNegocioCreacion(Partido partido) {
        if (partido.getEquipoLocal().getId().equals(partido.getEquipoVisitante().getId())) {
            throw new IllegalArgumentException("El equipo local y visitante no pueden ser el mismo.");
        }

        if (partido.getFecha().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("No se puede programar un partido en una fecha pasada.");
        }

        boolean yaJuegaEseDia = partidoRepository.existsByFechaAndEquipos(
                partido.getFecha(),
                partido.getEquipoLocal().getId(),
                partido.getEquipoVisitante().getId()
        );

        if (yaJuegaEseDia) {
            throw new IllegalArgumentException("Uno de los equipos ya tiene un partido en esa fecha.");
        }
    }

    private void validarLogicaNegocioActualizacion(Partido actualizado, Partido existente) {
        if (actualizado.getEquipoLocal().getId().equals(actualizado.getEquipoVisitante().getId())) {
            throw new IllegalArgumentException("El equipo local y visitante no pueden ser el mismo.");
        }

        if (actualizado.getResultado() != null && existente.getFecha().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("No se puede asignar un resultado a un partido que aún no se jugó.");
        }

        boolean yaJuegaEseDia = partidoRepository.existsByFechaAndEquiposExcluyendoId(
                actualizado.getFecha(),
                actualizado.getEquipoLocal().getId(),
                actualizado.getEquipoVisitante().getId(),
                existente.getId()
        );

        if (yaJuegaEseDia) {
            throw new IllegalArgumentException("Uno de los equipos ya tiene un partido en esa fecha.");
        }
    }
}

