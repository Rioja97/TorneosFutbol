package com.example.GestionTorneos.controller;
import com.example.GestionTorneos.model.Equipo;
import com.example.GestionTorneos.repository.EquipoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipos")
public class EquipoController {
    @Autowired
    private EquipoRepository equipoRepository;
    @GetMapping
    public List<Equipo> listar() {
        return equipoRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Equipo>  buscarPorId(@PathVariable Long id) {
        Optional<Equipo> equipo = equipoRepository.findById(id);
        return equipo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Equipo> crear(@RequestBody @Valid Equipo equipo) {
        Equipo guardado = equipoRepository.save(equipo);
        return ResponseEntity.ok(guardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (equipoRepository.existsById(id)) {
            equipoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
