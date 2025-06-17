package com.estacionamiento_smartpark.smart_park.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.estacionamiento_smartpark.smart_park.model.Estacionamiento;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstacionamientoRepository extends JpaRepository<Estacionamiento, Long> {

    Optional<Estacionamiento> findByNumero(int numero);

    List<Estacionamiento> findByOcupadoTrue();// lista ocupados

    List<Estacionamiento> findByOcupadoFalse();// lista libres

    List<Estacionamiento> findBySucursalId(Long sucursalId);// lista de todos los estacionamientos con su estado

    List<Estacionamiento> findBySucursalIdAndOcupadoFalse(Long sucursalId);// muestra los libres en la sucursal
                                                                              // elegida

    Optional<Estacionamiento> findByAutoPatente(String patente);   
    
    @Query("SELECT e.numero, a.patente, u.nombreCompleto " +
       "FROM Estacionamiento e " +
       "JOIN e.auto a " +
       "JOIN a.usuario u")
    List<Object[]> findEstacionamientosConAutosYUsuarios();


    
}
