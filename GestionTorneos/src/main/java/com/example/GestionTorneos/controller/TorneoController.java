package main.java.com.example.GestionTorneos.controller;
import com.example.proyectofinal.model.Torneo;
import com.example.proyectofinal.repository.TorneoRepository;
import org.springframework.beans.factory.annotation.Autowired;

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
}
