package com.example.GestionTorneos.controller;

import com.example.GestionTorneos.model.Torneo;
import com.example.GestionTorneos.service.TorneoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/torneo")
public class TorneoController {

    @Autowired
    private TorneoService torneoService;

    @GetMapping
    public List<Torneo> listar() {
        return torneoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Torneo> buscarPorId(@PathVariable Long id) {
        try {
            Torneo torneo = torneoService.buscarPorId(id);
            return ResponseEntity.ok(torneo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Torneo> crear(@RequestBody @Valid Torneo torneo) {
        Torneo nuevoTorneo = torneoService.crear(torneo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTorneo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Torneo> actualizar(@PathVariable Long id, @RequestBody @Valid Torneo datosActualizados) {
        try {
            Torneo torneoActualizado = torneoService.actualizar(id, datosActualizados);
            return ResponseEntity.ok(torneoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            torneoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}