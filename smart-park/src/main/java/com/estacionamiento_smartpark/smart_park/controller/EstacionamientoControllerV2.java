package com.estacionamiento_smartpark.smart_park.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
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

import com.estacionamiento_smartpark.smart_park.assemblers.EstacionamientoModelAssembler;
import com.estacionamiento_smartpark.smart_park.model.Estacionamiento;
import com.estacionamiento_smartpark.smart_park.service.EstacionamientoService;

@RestController
@RequestMapping("/api/v2/estacionamientos")
public class EstacionamientoControllerV2 {

     @Autowired
    private EstacionamientoService estacionamientoService;

    @Autowired
    private EstacionamientoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Estacionamiento>>> getAllEstacionamientos() {
        List<EntityModel<Estacionamiento>> estacionamientos = estacionamientoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (estacionamientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                estacionamientos,
                linkTo(methodOn(EstacionamientoControllerV2.class).getAllEstacionamientos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Estacionamiento>> getEstacionamientoById(@PathVariable Long id) {
        Estacionamiento estacionamiento = estacionamientoService.findById(id);
        if (estacionamiento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(estacionamiento));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Estacionamiento>> createEstacionamiento(@RequestBody Estacionamiento estacionamiento) {
        Estacionamiento nuevo = estacionamientoService.save(estacionamiento);
        return ResponseEntity
                .created(linkTo(methodOn(EstacionamientoControllerV2.class).getEstacionamientoById(nuevo.getId())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Estacionamiento>> updateEstacionamiento(@PathVariable Long id, @RequestBody Estacionamiento estacionamiento) {
        estacionamiento.setId(id);
        Estacionamiento actualizado = estacionamientoService.save(estacionamiento);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Estacionamiento>> patchEstacionamiento(@PathVariable Long id, @RequestBody Estacionamiento estacionamiento) {
        Estacionamiento actualizado = estacionamientoService.patchEstacionamiento(id, estacionamiento);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteEstacionamiento(@PathVariable Long id) {
        Estacionamiento estacionamiento = estacionamientoService.findById(id);
        if (estacionamiento == null) {
            return ResponseEntity.notFound().build();
        }
        estacionamientoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
