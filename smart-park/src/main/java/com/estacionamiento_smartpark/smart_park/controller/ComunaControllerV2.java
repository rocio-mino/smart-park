package com.estacionamiento_smartpark.smart_park.controller;

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

import com.estacionamiento_smartpark.smart_park.assemblers.ComunaModelAssembler;
import com.estacionamiento_smartpark.smart_park.model.Comuna;
import com.estacionamiento_smartpark.smart_park.service.ComunaService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/comunas")
public class ComunaControllerV2 {

    @Autowired
    private ComunaService comunaService;

    @Autowired
    private ComunaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Comuna>>> getAllComunas() {
        List<EntityModel<Comuna>> comunas = comunaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (comunas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                comunas,
                linkTo(methodOn(ComunaControllerV2.class).getAllComunas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Comuna>> getComunaById(@PathVariable Long id) {
        Comuna comuna = comunaService.findById(id);
        if (comuna == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(comuna));
    }


    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Comuna>> createComuna(@RequestBody Comuna comuna) {
        Comuna newComuna = comunaService.save(comuna);
        return ResponseEntity
                .created(linkTo(methodOn(ComunaControllerV2.class).getComunaById(newComuna.getId())).toUri())
                .body(assembler.toModel(newComuna));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Comuna>> updateComuna(@PathVariable Long id, @RequestBody Comuna comuna) {
        comuna.setId(id);
        Comuna updatedComuna = comunaService.save(comuna);
        return ResponseEntity.ok(assembler.toModel(updatedComuna));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Comuna>> patchComuna(@PathVariable Long id, @RequestBody Comuna comuna) {
        Comuna updatedComuna = comunaService.patchComuna(id, comuna);
        if (updatedComuna == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedComuna));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteComuna(@PathVariable Long id) {
        Comuna comuna = comunaService.findById(id);
        if (comuna == null) {
            return ResponseEntity.notFound().build();
        }
        comunaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
