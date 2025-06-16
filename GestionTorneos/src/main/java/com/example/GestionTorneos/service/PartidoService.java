package com.example.GestionTorneos.service;
import com.example.GestionTorneos.model.Partido;
import com.example.GestionTorneos.repository.PartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartidoService {

    @Autowired
    private PartidoRepository partidoRepository;

    public List<Partido> listarTodos() {
        return partidoRepository.findAll();
    }

    public Partido buscarPorId(Long id) {
        return partidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado con id: " + id));
    }

    public Partido crear(Partido partido) {
        return partidoRepository.save(partido);
    }

    public Partido actualizar(Long id, Partido datosActualizados) {
        Partido existente = buscarPorId(id);
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
}

