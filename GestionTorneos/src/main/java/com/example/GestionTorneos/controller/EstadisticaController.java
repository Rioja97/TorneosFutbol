package main.java.com.example.GestionTorneos.controller;
import com.example.proyectofinal.model.Estadistica;
import com.example.proyectofinal.repository.EstadisticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estadistica")
public class EstadisticaController {

    @Autowired
    private EstadisticaRepository estadisticaRepository;

    @GetMapping
    public List<Estadistica>ListarTodas(){
        return estadisticaRepository.findAll();
    }
    @PostMapping
    public Estadistica crear(Estadistica estadistica){
        return estadisticaRepository.save(estadistica);
    }
}

