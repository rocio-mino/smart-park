package com.estacionamiento_smartpark.smart_park.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.estacionamiento_smartpark.smart_park.model.Auto;
import com.estacionamiento_smartpark.smart_park.model.Estacionamiento;
import com.estacionamiento_smartpark.smart_park.model.Sucursal;
import com.estacionamiento_smartpark.smart_park.repository.EstacionamientoRepository;

@SpringBootTest
public class EstacionamientoServiceTest {

    @Autowired
    private EstacionamientoService estacionamientoService;

    @MockBean
    private EstacionamientoRepository estacionamientoRepository;

    private Estacionamiento createEstacionamieno(){
        return new Estacionamiento (
            1L,
            123,
            false,
            new Auto(),
            new Sucursal()
        );
    }

    @Test
    public void testFindAll() {
        when(estacionamientoRepository.findAll()).thenReturn(List.of(createEstacionamieno()));
        List<Estacionamiento> estacionamiento = estacionamientoService.findAll();
        assertNotNull(estacionamiento);
        assertEquals(1, estacionamiento.size());
    }

    @Test
    public void testFindById() {
        when(estacionamientoRepository.findById(1L)).thenReturn(java.util.Optional.of(createEstacionamieno()));
        Estacionamiento estacionamiento = estacionamientoService.findById(1L);
        assertNotNull(estacionamiento);
        assertEquals(123, estacionamiento.getNumero());
    }

    @Test
    public void testSave() {
        Estacionamiento estacionamiento = createEstacionamieno();
        when(estacionamientoRepository.save(estacionamiento)).thenReturn(estacionamiento);
        Estacionamiento savedEstacionamiento = estacionamientoService.save(estacionamiento);
        assertNotNull(savedEstacionamiento);
        assertEquals(123, savedEstacionamiento.getNumero());
    }

    @Test
    public void testPatchEstacionamiento() {
        Estacionamiento existingEstacionamiento = createEstacionamieno();
        Estacionamiento patchData = new Estacionamiento();
        patchData.setNumero(111);

        when(estacionamientoRepository.findById(1L)).thenReturn(java.util.Optional.of(existingEstacionamiento));
        when(estacionamientoRepository.save(any(Estacionamiento.class))).thenReturn(existingEstacionamiento);

        Estacionamiento patchedEstacionamiento = estacionamientoService.patchEstacionamiento(1L, patchData);
        assertNotNull(patchedEstacionamiento);
        assertEquals(111, patchedEstacionamiento.getNumero());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(estacionamientoRepository).deleteById(1L);
        estacionamientoService.deleteById(1L);
        verify(estacionamientoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testObtenerEstacionamientosConAutosYUsuarios() {
        List<Object[]> lista = estacionamientoService.obtenerEstacionamientosConAutosYUsuarios();
        assertNotNull(lista);
    }


}

