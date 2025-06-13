package com.example.GestionTorneos.controller;

import com.example.GestionTorneos.model.Jugador;
import com.example.GestionTorneos.repository.JugadorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jugador")
public class JugadorController {
    @Autowired
    private JugadorRepository jugadorRepository;

    @GetMapping
    public List<Jugador>listarTodos(){
        return jugadorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jugador> buscarPorId(@PathVariable Long id){
        Optional<Jugador> jugador = jugadorRepository.findById(id);
        return jugador.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Jugador> crear(@RequestBody @Valid Jugador jugador){
        Jugador guardado = jugadorRepository.save(jugador);
        return ResponseEntity.ok(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jugador> actualizar(@PathVariable Long id, @RequestBody @Valid Jugador datosActualizados) {
        return jugadorRepository.findById(id).map(jugadorExistente -> {
            jugadorExistente.setNombre(datosActualizados.getNombre());
            jugadorExistente.setEdad(datosActualizados.getEdad());
            jugadorExistente.setPosicion(datosActualizados.getPosicion());
            jugadorExistente.setDorsal(datosActualizados.getDorsal());
            jugadorExistente.setEquipo(datosActualizados.getEquipo());
            return ResponseEntity.ok(jugadorRepository.save(jugadorExistente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>eliminar(@PathVariable Long id){
        if(jugadorRepository.existsById(id)){
            jugadorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
