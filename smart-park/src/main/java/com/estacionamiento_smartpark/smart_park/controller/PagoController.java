package com.estacionamiento_smartpark.smart_park.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.estacionamiento_smartpark.smart_park.model.Pago;
import com.estacionamiento_smartpark.smart_park.service.PagoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping
    public ResponseEntity<Pago> crearPago(@RequestParam Long registroId,
                                          @RequestParam String metodoPago) {
        return ResponseEntity.ok(pagoService.crearPago(registroId, metodoPago));
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<Pago>> obtenerTodos() {
        return ResponseEntity.ok(pagoService.obtenerTodosLosPagos());
    }
    @GetMapping
    public ResponseEntity<Pago> obtenerPorId(@PathVariable Long id){
        Optional<Pago> pago = pagoService.obtenerPagoPorId(id);
        return pago.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/metodo/{metodo}")
    public ResponseEntity<List<Pago>> obtenerPorMetodo(@PathVariable String metodo) {
        return ResponseEntity.ok(pagoService.obtenerPagosPorMetodo(metodo));
    }
    @GetMapping("/rango")
    public ResponseEntity<List<Pago>>obtenerPagosPorFechas(@RequestParam String inicio,@RequestParam String fin){
        LocalDateTime fechaInicio = LocalDateTime.parse(inicio);
        LocalDateTime fechaFin = LocalDateTime.parse(fin);
        return ResponseEntity.ok(pagoService.obtenerPagosEntreFechas(fechaInicio, fechaFin));
    }
    @GetMapping("/registro/{registroId}")
    public ResponseEntity<Pago> obtenerPorRegistroId(@PathVariable Long registroId){
        Optional<Pago> pago = pagoService.obtenerPagoPorRegistroId(registroId);
        return pago.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id){
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping
    public ResponseEntity<Pago> actualizarPago(@RequestBody Pago pago){
        return ResponseEntity.ok(pagoService.actualizarPago(pago));
    }
}
