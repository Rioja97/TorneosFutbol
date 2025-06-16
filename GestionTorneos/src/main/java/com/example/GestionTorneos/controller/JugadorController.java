package com.example.GestionTorneos.controller;

import com.example.GestionTorneos.model.Jugador;
import com.example.GestionTorneos.service.JugadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jugador")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @GetMapping
    public List<Jugador> listarTodos() {
        return jugadorService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jugador> buscarPorId(@PathVariable Long id) {
        try {
            Jugador jugador = jugadorService.buscarPorId(id);
            return ResponseEntity.ok(jugador);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Jugador> crear(@RequestBody @Valid Jugador jugador) {
        Jugador nuevoJugador = jugadorService.crear(jugador);
        return ResponseEntity.ok(nuevoJugador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jugador> actualizar(@PathVariable Long id, @RequestBody @Valid Jugador datosActualizados) {
        try {
            Jugador jugadorActualizado = jugadorService.actualizar(id, datosActualizados);
            return ResponseEntity.ok(jugadorActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            jugadorService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}