package com.example.GestionTorneos.controller;

import com.example.GestionTorneos.model.Equipo;
import com.example.GestionTorneos.service.EquipoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipos")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @GetMapping
    public List<Equipo> listar() {
        return equipoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> buscarPorId(@PathVariable Long id) {
        try {
            Equipo equipo = equipoService.buscarPorId(id);
            return ResponseEntity.ok(equipo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Equipo> crear(@RequestBody @Valid Equipo equipo) {
        Equipo nuevoEquipo = equipoService.crear(equipo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEquipo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizar(@PathVariable Long id, @RequestBody @Valid Equipo datosActualizados) {
        try {
            Equipo equipoActualizado = equipoService.actualizar(id, datosActualizados);
            return ResponseEntity.ok(equipoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            equipoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}