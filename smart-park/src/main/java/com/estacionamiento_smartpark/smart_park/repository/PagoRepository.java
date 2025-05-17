package com.estacionamiento_smartpark.smart_park.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.estacionamiento_smartpark.smart_park.model.Pago;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByMetodo(String metodo);

    List<Pago> findByFechaPagoBetween(LocalDateTime inicio, LocalDateTime fin);

    Optional<Pago> findByRegistroId(Long registroId);// en caso de que no exista un resultado evita errores por null

    boolean existsByRegistroId(Long registroId);// validar si ya se pago un registro

}