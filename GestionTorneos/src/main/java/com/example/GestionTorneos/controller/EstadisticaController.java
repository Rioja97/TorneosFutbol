package com.example.GestionTorneos.controller;

import com.example.GestionTorneos.model.Estadistica;
import com.example.GestionTorneos.service.EstadisticaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticaController {

    @Autowired
    private EstadisticaService estadisticaService;

    @GetMapping
    public List<Estadistica> listarTodas() {
        return estadisticaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estadistica> buscarPorId(@PathVariable Long id) {
        try {
            Estadistica estadistica = estadisticaService.buscarPorId(id);
            return ResponseEntity.ok(estadistica);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Estadistica> crear(@RequestBody @Valid Estadistica estadistica) {
        Estadistica nuevaEstadistica = estadisticaService.crear(estadistica);
        return ResponseEntity.ok(nuevaEstadistica);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estadistica> actualizar(@PathVariable Long id, @RequestBody @Valid Estadistica datosActualizados) {
        try {
            Estadistica estadisticaActual = estadisticaService.buscarPorId(id);
            estadisticaActual.setGoles(datosActualizados.getGoles());
            estadisticaActual.setAsistencias(datosActualizados.getAsistencias());
            estadisticaActual.setTarjetasAmarillas(datosActualizados.getTarjetasAmarillas());
            estadisticaActual.setTarjetasRojas(datosActualizados.getTarjetasRojas());
            estadisticaActual.setJugador(datosActualizados.getJugador());
            estadisticaActual.setPartido(datosActualizados.getPartido());

            Estadistica estadisticaActualizada = estadisticaService.crear(estadisticaActual);
            return ResponseEntity.ok(estadisticaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            estadisticaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}