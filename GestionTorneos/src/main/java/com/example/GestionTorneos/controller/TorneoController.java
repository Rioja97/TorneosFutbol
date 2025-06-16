package com.example.GestionTorneos.controller;
import com.example.GestionTorneos.model.Torneo;
import com.example.GestionTorneos.repository.TorneoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/torneo")
public class TorneoController {
    @Autowired
    private TorneoRepository torneoRepository;
    @GetMapping
    public List<Torneo> listar(){
        return torneoRepository.findAll();
    }
    @GetMapping("/{id}")
    public Torneo buscarPorId(@PathVariable Long id) {
        return torneoRepository.findById(id).orElse(null);
    }
    @PostMapping
    public Torneo crear(@RequestBody Torneo torneo) {
        return torneoRepository.save(torneo);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Torneo> actualizar(@PathVariable Long id, @RequestBody @Valid Torneo datosActualizados) {
        return torneoRepository.findById(id).map(torneoExistente -> {
            torneoExistente.setNombre(datosActualizados.getNombre());
            torneoExistente.setCategoria(datosActualizados.getCategoria());
            torneoExistente.setEquiposParticipantes(datosActualizados.getEquiposParticipantes());
            torneoExistente.setUbicacion(datosActualizados.getUbicacion());
            return ResponseEntity.ok(torneoRepository.save(torneoExistente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (torneoRepository.existsById(id)) {
            torneoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
