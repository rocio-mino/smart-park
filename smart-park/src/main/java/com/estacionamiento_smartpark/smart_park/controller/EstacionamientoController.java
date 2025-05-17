package com.estacionamiento_smartpark.smart_park.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamiento_smartpark.smart_park.model.Estacionamiento;
import com.estacionamiento_smartpark.smart_park.service.EstacionamientoService;

@RestController
@RequestMapping("/api/v1/estacionamientos")
public class EstacionamientoController {

    @Autowired
    private EstacionamientoService estacionamientoService;

    @GetMapping
    public ResponseEntity<List<Estacionamiento>> obtenerTodos() {
        return ResponseEntity.ok(estacionamientoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estacionamiento> obtenerPorId(@PathVariable Long id) {
        Optional<Estacionamiento> est = estacionamientoService.findById(id);
        return est.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<Estacionamiento> obtenerPorNumero(@PathVariable int numero) {
        return estacionamientoService.findByNumero(numero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ocupados")
    public ResponseEntity<List<Estacionamiento>> obtenerOcupados() {
        return ResponseEntity.ok(estacionamientoService.findOcupados());
    }

    @GetMapping("/libres")
    public ResponseEntity<List<Estacionamiento>> obtenerLibres() {
        return ResponseEntity.ok(estacionamientoService.findLibres());
    }

    @GetMapping("/sucursal/{sucursalId}")
    public ResponseEntity<List<Estacionamiento>> obtenerPorSucursal(@PathVariable Long sucursalId) {
        return ResponseEntity.ok(estacionamientoService.findBySucursalId(sucursalId));
    }

    @GetMapping("/sucursal/{sucursalId}/libres")
    public ResponseEntity<List<Estacionamiento>> obtenerLibresPorSucursal(@PathVariable Long sucursalId) {
        return ResponseEntity.ok(estacionamientoService.findLibresBySucursalId(sucursalId));
    }

    @GetMapping("/patente/{patente}")
    public ResponseEntity<Estacionamiento> obtenerPorPatenteAuto(@PathVariable String patente) {
        return estacionamientoService.findByAutoPatente(patente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Estacionamiento> crear(@RequestBody Estacionamiento est) {
        return ResponseEntity.ok(estacionamientoService.save(est));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estacionamiento> actualizar(@PathVariable Long id, @RequestBody Estacionamiento est) {
        return ResponseEntity.ok(estacionamientoService.actualizarEstacionamiento(id, est));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Estacionamiento> patch(@PathVariable Long id, @RequestBody Estacionamiento parcial) {
        return ResponseEntity.ok(estacionamientoService.patchEstacionamiento(id, parcial));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        estacionamientoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

