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

import com.estacionamiento_smartpark.smart_park.assemblers.SucursalModelAssembler;
import com.estacionamiento_smartpark.smart_park.model.Sucursal;
import com.estacionamiento_smartpark.smart_park.service.SucursalService;

@RestController
@RequestMapping("/api/v2/sucursales")
public class SucursalControllerV2 {
    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Sucursal>>> getAllSucursales() {
        List<EntityModel<Sucursal>> sucursales = sucursalService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (sucursales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                sucursales,
                linkTo(methodOn(SucursalControllerV2.class).getAllSucursales()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> getSucursalById(@PathVariable Long id) {
        Sucursal sucursal = sucursalService.findById(id);
        if (sucursal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(sucursal));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> createSucursal(@RequestBody Sucursal sucursal) {
        Sucursal nuevaSucursal = sucursalService.save(sucursal);
        return ResponseEntity
                .created(linkTo(methodOn(SucursalControllerV2.class).getSucursalById(nuevaSucursal.getId())).toUri())
                .body(assembler.toModel(nuevaSucursal));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> updateSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        sucursal.setId(id);
        Sucursal sucursalActualizada = sucursalService.save(sucursal);
        return ResponseEntity.ok(assembler.toModel(sucursalActualizada));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> patchSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        Sucursal sucursalActualizada = sucursalService.patchSucursal(id, sucursal);
        if (sucursalActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(sucursalActualizada));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteSucursal(@PathVariable Long id) {
        Sucursal sucursal = sucursalService.findById(id);
        if (sucursal == null) {
            return ResponseEntity.notFound().build();
        }
        sucursalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
