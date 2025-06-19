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

import com.estacionamiento_smartpark.smart_park.repository.ComunaRepository;
import com.estacionamiento_smartpark.smart_park.model.Comuna;
import com.estacionamiento_smartpark.smart_park.model.Region;

@SpringBootTest
public class ComunaServiceTest {

    @Autowired
    private ComunaService comunaService;

    @MockBean
    private ComunaRepository comunaRepository;

    private Comuna createComuna() {
        return new Comuna(
            1L, 
            1234, 
            "Santiago",
            new Region()
        );
    }

    @Test
    public void testFindAll() {
        when(comunaRepository.findAll()).thenReturn(List.of(createComuna()));
        List<Comuna> comunas = comunaService.findAll();
        assertNotNull(comunas);
        assertEquals(1, comunas.size());
    }

    @Test
    public void testFindById() {
        when(comunaRepository.findById(1L)).thenReturn(java.util.Optional.of(createComuna()));
        Comuna comuna = comunaService.findById(1L);
        assertNotNull(comuna);
        assertEquals("Santiago", comuna.getNombre());
    }

    @Test
    public void testSave() {
        Comuna comuna = createComuna();
        when(comunaRepository.save(comuna)).thenReturn(comuna);
        Comuna savedComuna = comunaService.save(comuna);
        assertNotNull(savedComuna);
        assertEquals("Santiago", savedComuna.getNombre());
    }

    @Test
    public void testPatchComuna() {
        Comuna existingComuna = createComuna();
        Comuna patchData = new Comuna();
        patchData.setNombre("Santiago Actualizado");

        when(comunaRepository.findById(1L)).thenReturn(java.util.Optional.of(existingComuna));
        when(comunaRepository.save(any(Comuna.class))).thenReturn(existingComuna);

        Comuna patchedComuna = comunaService.patchComuna(1L, patchData);
        assertNotNull(patchedComuna);
        assertEquals("Santiago Actualizado", patchedComuna.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(comunaRepository).deleteById(1L);
        comunaService.delete(1L);
        verify(comunaRepository, times(1)).deleteById(1L);
    }
}
