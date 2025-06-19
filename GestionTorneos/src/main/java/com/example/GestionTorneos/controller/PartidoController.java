package com.example.GestionTorneos.controller;
import com.example.GestionTorneos.model.Partido;
import com.example.GestionTorneos.service.PartidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partidos")
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @GetMapping
    public List<Partido> listarTodos() {
        return partidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partido> buscarPorId(@PathVariable Long id) {
        try {
            Partido partido = partidoService.buscarPorId(id);
            return ResponseEntity.ok(partido);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Partido> crear(@RequestBody @Valid Partido partido) {
        Partido nuevoPartido = partidoService.crear(partido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPartido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partido> actualizar(@PathVariable Long id, @RequestBody @Valid Partido datosActualizados) {
        try {
            Partido partidoActualizado = partidoService.actualizar(id, datosActualizados);
            return ResponseEntity.ok(partidoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            partidoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}