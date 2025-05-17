package com.estacionamiento_smartpark.smart_park.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.estacionamiento_smartpark.smart_park.model.Estacionamiento;
import com.estacionamiento_smartpark.smart_park.model.Registro;
import com.estacionamiento_smartpark.smart_park.service.RegistroService;

@RestController
@RequestMapping("/api/v1/registros")
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    @GetMapping
    public ResponseEntity<List<Registro>> obtenerTodos() {
        return ResponseEntity.ok(registroService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registro> obtenerPorId(@PathVariable Long id) {
        return registroService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patente/{patente}")
    public ResponseEntity<List<Registro>> obtenerPorPatente(@PathVariable String patente) {
        return ResponseEntity.ok(registroService.obtenerRegistrosPorPatente(patente));
    }

    @GetMapping("/rango")
    public ResponseEntity<List<Registro>> obtenerPorRangoFechas(
            @RequestParam("inicio") String inicio,
            @RequestParam("fin") String fin) {
        LocalDateTime fechaInicio = LocalDateTime.parse(inicio);
        LocalDateTime fechaFin = LocalDateTime.parse(fin);
        return ResponseEntity.ok(registroService.obtenerRegistrosEntreFechas(fechaInicio, fechaFin));
    }

    @PostMapping
    public ResponseEntity<Registro> crearRegistro(@RequestBody Registro registro) {
        return ResponseEntity.ok(registroService.save(registro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegistro(@PathVariable Long id) {
        registroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/entrada")
    public ResponseEntity<Estacionamiento> registrarEntrada(
            @RequestParam String patente,
            @RequestParam int numeroEstacionamiento) {
        return ResponseEntity.ok(registroService.registrarEntrada(patente, numeroEstacionamiento));
    }

    @PostMapping("/salida")
    public ResponseEntity<Estacionamiento> registrarSalida(@RequestParam String patente) {
        return ResponseEntity.ok(registroService.registrarSalida(patente));
    }
}