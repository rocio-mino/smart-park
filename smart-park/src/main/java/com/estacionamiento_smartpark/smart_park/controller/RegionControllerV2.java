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

import com.estacionamiento_smartpark.smart_park.assemblers.RegionModelAssembler;
import com.estacionamiento_smartpark.smart_park.service.RegionService;
import com.estacionamiento_smartpark.smart_park.model.Region;

@RestController
@RequestMapping("/api/v2/regiones")
public class RegionControllerV2 {

    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Region>>> getAllRegiones() {
        List<EntityModel<Region>> regiones = regionService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (regiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                regiones,
                linkTo(methodOn(RegionControllerV2.class).getAllRegiones()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Region>> getRegionById(@PathVariable Long id) {
        Region region = regionService.findById(id);
        if (region == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(region));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Region>> createRegion(@RequestBody Region region) {
        Region newRegion = regionService.save(region);
        return ResponseEntity
                .created(linkTo(methodOn(RegionControllerV2.class).getRegionById(newRegion.getId())).toUri())
                .body(assembler.toModel(newRegion));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Region>> updateRegion(@PathVariable Long id, @RequestBody Region region) {
        region.setId(id);
        Region updatedRegion = regionService.save(region);
        return ResponseEntity.ok(assembler.toModel(updatedRegion));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Region>> patchRegion(@PathVariable Long id, @RequestBody Region region) {
        Region updatedRegion = regionService.patchRegion(id, region);
        if (updatedRegion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedRegion));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        Region region = regionService.findById(id);
        if (region == null) {
            return ResponseEntity.notFound().build();
        }
        regionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
