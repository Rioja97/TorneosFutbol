package com.example.GestionTorneos.controller;
import com.example.GestionTorneos.model.Partido;
import com.example.GestionTorneos.repository.PartidoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/partidos")
public class PartidoController {

    @Autowired
    private PartidoRepository partidoRepository;

    @GetMapping
    public List<Partido> listarTodos() {
        return partidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partido> buscarPorId(@PathVariable Long id) {
        Optional<Partido> partido = partidoRepository.findById(id);
        return partido.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Partido> crear(@RequestBody @Valid Partido partido) {
        Partido guardado = partidoRepository.save(partido);
        return ResponseEntity.ok(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partido> actualizar(@PathVariable Long id, @RequestBody @Valid Partido datosActualizados) {
        return partidoRepository.findById(id).map(partidoExistente -> {
            partidoExistente.setEquipoLocal(datosActualizados.getEquipoLocal());
            partidoExistente.setEquipoVisitante(datosActualizados.getEquipoVisitante());
            partidoExistente.setFecha(datosActualizados.getFecha());
            partidoExistente.setResultado(datosActualizados.getResultado());
            return ResponseEntity.ok(partidoRepository.save(partidoExistente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (partidoRepository.existsById(id)) {
            partidoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


