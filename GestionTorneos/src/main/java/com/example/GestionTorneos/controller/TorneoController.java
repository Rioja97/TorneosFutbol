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
    public ResponseEntity<List<Torneo>> listar() {
        return ResponseEntity.ok(torneoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Torneo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(torneoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Torneo> crear(@RequestBody @Valid Torneo torneo) {
        Torneo nuevoTorneo = torneoService.crear(torneo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTorneo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Torneo> actualizar(@PathVariable Long id, @RequestBody @Valid Torneo datosActualizados) {
        return ResponseEntity.ok(torneoService.actualizar(id, datosActualizados));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        Torneo torneo = torneoService.buscarPorId(id);
        torneoService.eliminar(id);
    }
}