package com.estacionamiento_smartpark.smart_park.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.estacionamiento_smartpark.smart_park.model.Region;
import com.estacionamiento_smartpark.smart_park.service.RegionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/regiones")
public class RegionController {
    @Autowired

    private RegionService regionService;

    @GetMapping
    public ResponseEntity<List<Region>> listar(){
        List<Region> regiones = regionService.findAll();
        if (regiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(regiones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> buscar(@PathVariable Long id) {
        try {
            Region region = regionService.findById(id);
            return ResponseEntity.ok(region);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Region> guardar(@RequestBody Region region) {
        Region regionNueva = regionService.save(region);
        return ResponseEntity.status(HttpStatus.CREATED).body(regionNueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Region> actualizar(@PathVariable Long id, @RequestBody Region region) {
        try {
            Region regionActualizado = regionService.updateRegion(id, region);
            return ResponseEntity.ok(regionActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Region> actualizarParcial(@PathVariable Long id, @RequestBody Region parcialRegion) {
        Region regionActualizado = regionService.patchRegion(id, parcialRegion);
        if (regionActualizado != null) {
            return ResponseEntity.ok(regionActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            regionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
