package com.estacionamiento_smartpark.smart_park.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamiento_smartpark.smart_park.model.Sucursal;
import com.estacionamiento_smartpark.smart_park.repository.SucursalRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SucursalService {
    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> findAll() {
        return sucursalRepository.findAll();
    }

    public Optional<Sucursal> findById(Long id) {
        return sucursalRepository.findById(id);
    }

    public Sucursal save(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

     public void delete(Long id) {
        sucursalRepository.deleteById(id);
    }

    public Sucursal actualizarSucursal(Long id, Sucursal sucursalActualizada) {
        Sucursal sucursalExistente = sucursalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(id + " no encontrado"));
        
        sucursalExistente.setNombre(sucursalActualizada.getNombre());
        sucursalExistente.setDireccion(sucursalActualizada.getDireccion());
        sucursalExistente.setTelefono(sucursalActualizada.getTelefono());
        sucursalExistente.setComuna(sucursalActualizada.getComuna());
        
        return sucursalRepository.save(sucursalExistente);
    }

    public Sucursal patchSucursal(Long id, Sucursal parcialSucursal) {
        Optional<Sucursal> sucursalOptional = sucursalRepository.findById(id);
        if (sucursalOptional.isPresent()) {            
            Sucursal sucursalToUpdate = sucursalOptional.get();
            
            if (parcialSucursal.getNombre() != null) {
                sucursalToUpdate.setNombre(parcialSucursal.getNombre()); 
            }
            if (parcialSucursal.getDireccion() != null) {
                sucursalToUpdate.setDireccion(parcialSucursal.getDireccion());
            }
            if (parcialSucursal.getTelefono() != 0) {
                sucursalToUpdate.setTelefono(parcialSucursal.getTelefono());
            }
            if (parcialSucursal.getComuna() != null) {
                sucursalToUpdate.setComuna(parcialSucursal.getComuna());
            }
            return sucursalRepository.save(sucursalToUpdate);
        } else {
            return null;
        }
    }

    public List<Sucursal> findByDireccion(String direccion) {
        return sucursalRepository.findByDireccion(direccion);
    }

    public List<Sucursal> findByNombre(String nombre) {
        return sucursalRepository.findByNombre(nombre);
    }

    public List<Sucursal> findByComunaId(Long comunaId) {
        return sucursalRepository.findByComunaId(comunaId);
    }
}
