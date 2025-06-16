package com.example.GestionTorneos.controller;
import com.example.GestionTorneos.model.Entrenador;
import com.example.GestionTorneos.repository.EntrenadorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entrenadores")
public class EntrenadorController {


    @Autowired
    private EntrenadorRepository entrenadorRepository;

    @GetMapping
    public List<Entrenador> listarTodos() {
        return entrenadorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entrenador> buscarPorId(@PathVariable Long id) {
        Optional<Entrenador> entrenador = entrenadorRepository.findById(id);
        return entrenador.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Entrenador> crear(@RequestBody @Valid Entrenador entrenador) {
        Entrenador guardado = entrenadorRepository.save(entrenador);
        return ResponseEntity.ok(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entrenador> actualizar(@PathVariable Long id, @RequestBody @Valid Entrenador datosActualizados) {
        return entrenadorRepository.findById(id).map(entrenadorExistente -> {
            entrenadorExistente.setNombre(datosActualizados.getNombre());
            entrenadorExistente.setExperiencia(datosActualizados.getExperiencia());
            entrenadorExistente.setEquipo(datosActualizados.getEquipo());
            return ResponseEntity.ok(entrenadorRepository.save(entrenadorExistente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (entrenadorRepository.existsById(id)) {
            entrenadorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
//probandooo
