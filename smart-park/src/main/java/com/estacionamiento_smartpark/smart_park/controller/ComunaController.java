package com.estacionamiento_smartpark.smart_park.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.estacionamiento_smartpark.smart_park.model.Comuna;
import com.estacionamiento_smartpark.smart_park.service.ComunaService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/comunas")
public class ComunaController {
    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<Comuna>> listar(){
        List<Comuna> comuna = comunaService.findAll();
        if (comuna.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comuna);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Comuna> buscar(@PathVariable Long id) {
        try {
            Comuna comuna = comunaService.findById(id);
            return ResponseEntity.ok(comuna);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Comuna> guardar(@RequestBody Comuna comuna) {
        Comuna comunaNueva = comunaService.save(comuna);
        return ResponseEntity.status(HttpStatus.CREATED).body(comunaNueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comuna> actualizar(@PathVariable Long id, @RequestBody Comuna comuna) {
        try {
            Comuna comunaActualizado = comunaService.updateComuna(id, comuna);
            return ResponseEntity.ok(comunaActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comuna> actualizarParcial(@PathVariable Long id, @RequestBody Comuna parcialComuna) {
        Comuna comunaActualizado = comunaService.patchComuna(id, parcialComuna);
        if (comunaActualizado != null) {
            return ResponseEntity.ok(comunaActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            comunaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
