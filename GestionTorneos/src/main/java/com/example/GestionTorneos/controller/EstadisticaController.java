package com.example.GestionTorneos.controller;
import com.example.GestionTorneos.model.Estadistica;
import com.example.GestionTorneos.repository.EstadisticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticaController {

    @Autowired
    private EstadisticaRepository estadisticaRepository;

    @GetMapping
    public List<Estadistica> listarTodas() {
        return estadisticaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estadistica> buscarPorId(@PathVariable Long id) {
        Optional<Estadistica> estadistica = estadisticaRepository.findById(id);
        return estadistica.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Estadistica> crear(@RequestBody @Valid Estadistica estadistica) {
        Estadistica guardada = estadisticaRepository.save(estadistica);
        return ResponseEntity.ok(guardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estadistica> actualizar(@PathVariable Long id, @RequestBody @Valid Estadistica datosActualizados) {
        return estadisticaRepository.findById(id).map(estadisticaExistente -> {
            estadisticaExistente.setGoles(datosActualizados.getGoles());
            estadisticaExistente.setAsistencias(datosActualizados.getAsistencias());
            estadisticaExistente.setTarjetasAmarillas(datosActualizados.getTarjetasAmarillas());
            estadisticaExistente.setTarjetasRojas(datosActualizados.getTarjetasRojas());
            estadisticaExistente.setJugador(datosActualizados.getJugador());
            estadisticaExistente.setEquipo(datosActualizados.getEquipo());
            estadisticaExistente.setPartido(datosActualizados.getPartido());
            return ResponseEntity.ok(estadisticaRepository.save(estadisticaExistente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (estadisticaRepository.existsById(id)) {
            estadisticaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


