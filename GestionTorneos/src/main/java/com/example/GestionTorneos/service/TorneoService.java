package com.example.GestionTorneos.service;

import com.example.GestionTorneos.excepcion.CupoMaximoException;
import com.example.GestionTorneos.excepcion.EntidadRepetidaException;
import com.example.GestionTorneos.excepcion.NoNuloException;
import com.example.GestionTorneos.model.Equipo;
import com.example.GestionTorneos.model.Partido;
import com.example.GestionTorneos.model.Torneo;
import com.example.GestionTorneos.repository.EquipoRepository;
import com.example.GestionTorneos.repository.PartidoRepository;
import com.example.GestionTorneos.repository.TorneoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TorneoService {

    @Autowired
    private TorneoRepository torneoRepository;
    @Autowired
    private EquipoRepository equipoRepository;
    private PartidoService partidoService;

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
        List<Equipo> equiposValidos = obtenerEquiposValidos(torneo.getEquiposParticipantes());
        torneo.setEquiposParticipantes(equiposValidos);
        return torneoRepository.save(torneo);
    }

    public Torneo actualizar(Long id, Torneo datosActualizados) {
        Torneo existente = buscarPorId(id);
        validarLogicaNegocioActualizacion(datosActualizados, existente);

        existente.setNombre(datosActualizados.getNombre());
        existente.setCategoria(datosActualizados.getCategoria());

        List<Equipo> equiposValidos = obtenerEquiposValidos(datosActualizados.getEquiposParticipantes());
        existente.setEquiposParticipantes(equiposValidos);

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

    private List<Equipo> obtenerEquiposValidos(List<Equipo> equipos) {
        List<Long> ids = equipos.stream()
                .map(Equipo::getId)
                .collect(Collectors.toList());

        List<Equipo> equiposValidos = equipoRepository.findAllById(ids);

        if (equiposValidos.size() != ids.size()) {
            throw new RuntimeException("Uno o más equipos no existen en la base de datos");
        }
        return equiposValidos;
    }

    public Torneo eliminarEquipoDeTorneo(Long idTorneo, Long idEquipo) {
        Torneo torneo = buscarPorId(idTorneo);

        List<Equipo> equiposActuales = torneo.getEquiposParticipantes();

        boolean removed = equiposActuales.removeIf(equipo -> equipo.getId().equals(idEquipo));
        if (!removed) {
            throw new RuntimeException("El equipo no pertenece al torneo.");
        }

        torneo.setEquiposParticipantes(equiposActuales);

        return torneoRepository.save(torneo);
    }

    public Torneo agregarEquiposAlTorneo(Long idTorneo, List<Long> idsEquipos) {
        Torneo torneo = buscarPorId(idTorneo);

        // Traer los equipos válidos por ID
        List<Equipo> equiposParaAgregar = equipoRepository.findAllById(idsEquipos);

        if (equiposParaAgregar.size() != idsEquipos.size()) {
            throw new RuntimeException("Uno o más equipos no existen en la base de datos");
        }

        // Obtener los equipos actuales
        List<Equipo> equiposActuales = torneo.getEquiposParticipantes();

        // Agregar sin duplicados
        Set<Long> idsActuales = equiposActuales.stream()
                .map(Equipo::getId)
                .collect(Collectors.toSet());

        for (Equipo e : equiposParaAgregar) {
            if (!idsActuales.contains(e.getId())) {
                equiposActuales.add(e);
            }
        }

        torneo.setEquiposParticipantes(equiposActuales);

        return torneoRepository.save(torneo);
    }

    @Transactional
    public Partido agregarPartido(Long idTorneo, Partido partido) {
        Torneo torneo = torneoRepository.findById(idTorneo)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        partido.setTorneo(torneo); // si Partido tiene una relación con Torneo
        Partido partidoGuardado = partidoService.crear(partido);

        torneo.getPartidos().add(partidoGuardado);
        torneoRepository.save(torneo); // opcional si usás cascade

        return partidoGuardado;
    }

    @Transactional
    public void eliminarPartidoDeTorneo(Long idTorneo, Long idPartido) {
        Torneo torneo = torneoRepository.findById(idTorneo)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        Partido partido = partidoService.buscarPorId(idPartido);

        if (!torneo.getPartidos().contains(partido)) {
            throw new RuntimeException("El partido no pertenece a este torneo");
        }

        torneo.getPartidos().remove(partido);
        partidoService.eliminar(idPartido);
    }

}
