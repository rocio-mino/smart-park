package com.estacionamiento_smartpark.smart_park.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.estacionamiento_smartpark.smart_park.model.Pago;
import com.estacionamiento_smartpark.smart_park.model.Registro;
import com.estacionamiento_smartpark.smart_park.repository.PagoRepository;
import com.estacionamiento_smartpark.smart_park.repository.RegistroRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private RegistroRepository registroRepository;

    public Pago crearPago(Long registroId, String metodoPago) {
        Registro registro = registroRepository.findById(registroId)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

        if (registro.getHoraSalida() == null) {
            throw new IllegalStateException("El auto aún no ha salido del estacionamiento");
        }

        if (pagoRepository.existsByRegistroId(registroId)) {
            throw new IllegalStateException("Este registro ya tiene un pago asociado");
        }

        long minutosEstacionado = Duration.between(registro.getHoraLlegada(), registro.getHoraSalida()).toMinutes();
        int monto = (int) (minutosEstacionado * 30);

        Pago pago = new Pago();
        pago.setFechaPago(LocalDateTime.now());
        pago.setMonto(monto);
        pago.setMetodo(metodoPago);
        pago.setRegistro(registro);

        return pagoRepository.save(pago);
    }

    public List<Pago> obtenerTodosLosPagos() {
        return pagoRepository.findAll();
    }

    public Optional<Pago> obtenerPagoPorId(Long id) {
        return pagoRepository.findById(id);
    }

    public Pago actualizarPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    public void eliminarPago(Long id) {
        pagoRepository.deleteById(id);
    }

    public List<Pago> obtenerPagosPorMetodo(String metodo) {
        return pagoRepository.findByMetodo(metodo);
    }

    public List<Pago> obtenerPagosEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
        return pagoRepository.findByFechaPagoBetween(inicio, fin);
    }

    public Optional<Pago> obtenerPagoPorRegistroId(Long registroId) {
        return pagoRepository.findByRegistroId(registroId);
    }

    public boolean existePagoParaRegistro(Long registroId) {
        return pagoRepository.existsByRegistroId(registroId);
    }

    public List<Object[]> obtenerPagosConDetallesRegistro() {
        return pagoRepository.findPagosConDetallesRegistro();
    }

}
