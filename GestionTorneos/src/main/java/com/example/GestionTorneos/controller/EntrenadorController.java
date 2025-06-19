package com.example.GestionTorneos.controller;

import com.example.GestionTorneos.model.Entrenador;
import com.example.GestionTorneos.service.EntrenadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entrenadores")
public class EntrenadorController {

    @Autowired
    private EntrenadorService entrenadorService;

    @GetMapping
    public List<Entrenador> listarTodos() {
        return entrenadorService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entrenador> buscarPorId(@PathVariable Long id) {
        try {
            Entrenador entrenador = entrenadorService.buscarPorId(id);
            return ResponseEntity.ok(entrenador);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Entrenador> crear(@RequestBody @Valid Entrenador entrenador) {
        Entrenador nuevoEntrenador = entrenadorService.crear(entrenador);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEntrenador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entrenador> actualizar(@PathVariable Long id, @RequestBody @Valid Entrenador datosActualizados) {
        try {
            Entrenador entrenadorActualizado = entrenadorService.actualizar(id, datosActualizados);
            return ResponseEntity.ok(entrenadorActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            entrenadorService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}