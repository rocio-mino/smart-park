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

import com.estacionamiento_smartpark.smart_park.assemblers.PagoModelAssembler;
import com.estacionamiento_smartpark.smart_park.model.Pago;
import com.estacionamiento_smartpark.smart_park.service.PagoService;

@RestController
@RequestMapping("/api/v2/pagos")
public class PagoControllerV2 {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Pago>>> getAllPagos() {
        List<EntityModel<Pago>> pagos = pagoService.obtenerTodosLosPagos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                pagos,
                linkTo(methodOn(PagoControllerV2.class).getAllPagos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pago>> getPagoById(@PathVariable Long id) {
        Pago pago = pagoService.findById(id);
        if (pago == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(pago));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pago>> createPago(@RequestBody Pago pago) {
        Pago newPago = pagoService.actualizarPago(pago);
        return ResponseEntity
                .created(linkTo(methodOn(PagoControllerV2.class).getPagoById(newPago.getId())).toUri())
                .body(assembler.toModel(newPago));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pago>> updatePago(@PathVariable Long id, @RequestBody Pago pago) {
        pago.setId(id);
        Pago updatedPago = pagoService.actualizarPago(pago);
        return ResponseEntity.ok(assembler.toModel(updatedPago));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pago>> patchPago(@PathVariable Long id, @RequestBody Pago pago) {
        Pago updatedPago = pagoService.patchPago(id, pago);
        if (updatedPago == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedPago));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        Pago pago = pagoService.findById(id);
        if (pago == null) {
            return ResponseEntity.notFound().build();
        }
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }

}
