package com.estacionamiento_smartpark.smart_park.service;

import com.estacionamiento_smartpark.smart_park.model.Auto;
import com.estacionamiento_smartpark.smart_park.model.Estacionamiento;
import com.estacionamiento_smartpark.smart_park.model.Registro;
import com.estacionamiento_smartpark.smart_park.repository.AutoRepository;
import com.estacionamiento_smartpark.smart_park.repository.EstacionamientoRepository;
import com.estacionamiento_smartpark.smart_park.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RegistroService {

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private EstacionamientoRepository estacionamientoRepository;

    public Registro save(Registro registro) {
        return registroRepository.save(registro);
    }

    public List<Registro> findAll() {
        return registroRepository.findAll();
    }

    public Optional<Registro> findById(Long id) {
        return registroRepository.findById(id);
    }

    public void deleteById(Long id) {
        registroRepository.deleteById(id);
    }

    public List<Registro> obtenerRegistrosPorPatente(String patente) {
        return registroRepository.findByAuto_Patente(patente);
    }

    public List<Registro> obtenerRegistrosEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
        if (inicio.isAfter(fin)) {
            throw new IllegalArgumentException("La fecha inicial debe ser anterior a la de fin");
        }
        return registroRepository.findByHoraLlegadaBetween(inicio, fin);
    }

    public Estacionamiento registrarEntrada(String patente, int numeroEstacionamiento) {
        if (registroRepository.estaEstacionado(patente)) {
            throw new IllegalStateException("El auto ya está estacionado");
        }

        Auto auto = autoRepository.findByPatente(patente)
                .orElseThrow(() -> new RuntimeException("Auto no encontrado"));

        Estacionamiento estacionamiento = estacionamientoRepository.findByNumero(numeroEstacionamiento)
                .orElseThrow(() -> new RuntimeException("Estacionamiento no existe"));

        if (estacionamiento.isOcupado()) {
            throw new IllegalStateException(numeroEstacionamiento + " ya está ocupado");
        }

        estacionamiento.setOcupado(true);
        estacionamiento.setAuto(auto);
        estacionamientoRepository.save(estacionamiento);

        Registro registro = new Registro();
        registro.setAuto(auto);
        registro.setHoraLlegada(LocalDateTime.now());
        registroRepository.save(registro);

        return estacionamiento;
    }

    public Estacionamiento registrarSalida(String patente) {
    Auto auto = autoRepository.findByPatente(patente)
            .orElseThrow(() -> new RuntimeException("Auto no encontrado"));

    Registro registro = registroRepository.findByAutoAndHoraSalidaIsNull(auto)
            .orElseThrow(() -> new RuntimeException("No hay registro activo para este auto"));

    Estacionamiento estacionamiento = estacionamientoRepository.findByAutoPatente(patente)
            .orElseThrow(() -> new RuntimeException("No hay estacionamiento ocupado por un auto con patente " + patente));

    registro.setHoraSalida(LocalDateTime.now());
    registroRepository.save(registro);

    estacionamiento.setOcupado(false);
    estacionamiento.setAuto(null);
    estacionamientoRepository.save(estacionamiento);

    return estacionamiento;
}

}
