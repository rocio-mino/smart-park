package com.estacionamiento_smartpark.smart_park.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.estacionamiento_smartpark.smart_park.model.Estacionamiento;
import com.estacionamiento_smartpark.smart_park.repository.EstacionamientoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstacionamientoService {
    @Autowired
    private EstacionamientoRepository estacionamientoRepository;

    public List<Estacionamiento> findAll() {
        return estacionamientoRepository.findAll();
    }

    public Optional<Estacionamiento> findById(Long id) {
        return estacionamientoRepository.findById(id);
    }

    public Estacionamiento save(Estacionamiento estacionamiento) {
        return estacionamientoRepository.save(estacionamiento);
    }

    public void deleteById(Long id) {
        estacionamientoRepository.deleteById(id);
    }

    public Estacionamiento actualizarEstacionamiento(Long id, Estacionamiento estacionamientoActualizado) {
    Estacionamiento estacionamientoExistente = estacionamientoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Estacionamiento con ID " + id + " no encontrado"));
    
    estacionamientoExistente.setNumero(estacionamientoActualizado.getNumero());
    estacionamientoExistente.setOcupado(estacionamientoActualizado.isOcupado());
    estacionamientoExistente.setSucursal(estacionamientoActualizado.getSucursal());
    estacionamientoExistente.setAuto(estacionamientoActualizado.getAuto());
    
    return estacionamientoRepository.save(estacionamientoExistente);
}

public Estacionamiento patchEstacionamiento(Long id, Estacionamiento parcialEstacionamiento) {
    Estacionamiento estacionamiento = estacionamientoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Estacionamiento no encontrado"));
    
    if (parcialEstacionamiento.getNumero() != 0) {
        estacionamiento.setNumero(parcialEstacionamiento.getNumero());
    }
    
    estacionamiento.setOcupado(parcialEstacionamiento.isOcupado());
    
    if (parcialEstacionamiento.getSucursal() != null) {
        estacionamiento.setSucursal(parcialEstacionamiento.getSucursal());
    }
    if (parcialEstacionamiento.getAuto() != null) {
        estacionamiento.setAuto(parcialEstacionamiento.getAuto());
    }
    
    return estacionamientoRepository.save(estacionamiento);
}

    public Optional<Estacionamiento> findByNumero(int numero) {
        return estacionamientoRepository.findByNumero(numero);
    }

    public List<Estacionamiento> findOcupados() {
        return estacionamientoRepository.findByOcupadoTrue();
    }

    public List<Estacionamiento> findLibres() {
        return estacionamientoRepository.findByOcupadoFalse();
    }

    public List<Estacionamiento> findBySucursalId(Long sucursalId) {
        return estacionamientoRepository.findBySucursalId(sucursalId);
    }

    public List<Estacionamiento> findLibresBySucursalId(Long sucursalId) {
        return estacionamientoRepository.findBySucursalIdAndOcupadoFalse(sucursalId);
    }

    public Optional<Estacionamiento> findByAutoPatente(String patente) {
        return estacionamientoRepository.findByAutoPatente(patente);
    }

}
