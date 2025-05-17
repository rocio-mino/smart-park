package com.estacionamiento_smartpark.smart_park.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.estacionamiento_smartpark.smart_park.model.Comuna;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {

    Comuna findByCodigo(int codigo);

    List<Comuna> findByNombre(String nombre);

    List<Comuna> findByRegionId(Long regionId);

}
