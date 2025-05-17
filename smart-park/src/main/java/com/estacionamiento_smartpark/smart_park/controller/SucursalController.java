package com.estacionamiento_smartpark.smart_park.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.estacionamiento_smartpark.smart_park.model.Sucursal;
import com.estacionamiento_smartpark.smart_park.service.SucursalService;


@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<Sucursal>> listar() {
        List<Sucursal> sucursales = sucursalService.findAll();
        if (sucursales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sucursales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Sucursal>> buscar(@PathVariable Long id) {
        try {
            Optional<Sucursal> sucursal = sucursalService.findById(id);
            return ResponseEntity.ok(sucursal);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Sucursal> guardar(@RequestBody Sucursal sucursal) {
        Sucursal sucursalNuevo = sucursalService.save(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizar(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        try {
            Sucursal sucursalActualizado = sucursalService.actualizarSucursal(id, sucursal);
            return ResponseEntity.ok(sucursalActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Sucursal> actualizarParcial(@PathVariable Long id, @RequestBody Sucursal parcialSucursal) {
        Sucursal sucursalActualizado = sucursalService.patchSucursal(id, parcialSucursal);
        if (sucursalActualizado != null) {
            return ResponseEntity.ok(sucursalActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            sucursalService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/direccion")
    public List<Sucursal> getByDireccion(@RequestParam String direccion) {
        return sucursalService.findByDireccion(direccion);
    }

    @GetMapping("/nombre")
    public List<Sucursal> getByNombre(@RequestParam String nombre) {
        return sucursalService.findByNombre(nombre);
    }

    @GetMapping("/comuna")
    public List<Sucursal> getByComunaId(@RequestParam Long comunaId) {
        return sucursalService.findByComunaId(comunaId);
    }

}
