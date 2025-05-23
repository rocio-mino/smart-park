package com.estacionamiento_smartpark.smart_park.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estacionamiento_smartpark.smart_park.model.Auto;
import com.estacionamiento_smartpark.smart_park.model.Estacionamiento;
import com.estacionamiento_smartpark.smart_park.model.Registro;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface RegistroRepository extends JpaRepository<Registro, Long> {

    @Query("SELECT COUNT(r) > 0 FROM Registro r WHERE r.auto.id = :autoId AND r.horaSalida IS NULL")
    boolean tieneRegistroActivo(@Param("autoId") Long autoId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
           "FROM Registro r WHERE r.auto.patente = :patente AND r.horaSalida IS NULL")
    boolean estaEstacionado(@Param("patente") String patente);

    List<Registro> findByAuto_Patente(String patente);

    List<Registro> findByHoraLlegadaBetween(LocalDateTime inicio, LocalDateTime fin);

    Optional<Registro> findByAutoAndHoraSalidaIsNull(Auto auto);

    Optional<Estacionamiento> findByAutoPatente(String patente);

    @Query("SELECT r FROM Registro r WHERE r.horaSalida IS NULL")
    List<Registro> findRegistrosActivos();

    @Query("SELECT r FROM Registro r WHERE DATE(r.horaLlegada) = :fecha")
    List<Registro> findByFecha(@Param("fecha") LocalDate fecha);
}
