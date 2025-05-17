package com.estacionamiento_smartpark.smart_park.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;
import com.estacionamiento_smartpark.smart_park.model.Auto;
import com.estacionamiento_smartpark.smart_park.model.Usuario;
import com.estacionamiento_smartpark.smart_park.service.AutoService;
import com.estacionamiento_smartpark.smart_park.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/autos")
public class AutoController {
    @Autowired
    private AutoService autoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Auto>> listar() {
        List<Auto> autos = autoService.findAll();
        if (autos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(autos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auto> buscar(@PathVariable Long id) {
        try {
            Auto auto = autoService.findById(id);
            return ResponseEntity.ok(auto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Auto> guardar(@RequestBody Auto auto) {

        System.out.print(auto.getUsuario());
        Auto autoNuevo = autoService.save(auto);
        return ResponseEntity.status(HttpStatus.CREATED).body(autoNuevo);
    }

     @PutMapping("/{id}")
    public ResponseEntity<Auto> actualizar(@PathVariable Long id, @RequestBody Auto auto) {
        try {
            Auto autoActualizado = autoService.updateAuto(id, auto);
            return ResponseEntity.ok(autoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

     @PatchMapping("/{id}")
    public ResponseEntity<Auto> actualizarParcial(@PathVariable Long id, @RequestBody Auto parcialAuto) {
        Auto autoActualizado = autoService.patchAuto(id, parcialAuto);
        if (autoActualizado != null) {
            return ResponseEntity.ok(autoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

     @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            autoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}
