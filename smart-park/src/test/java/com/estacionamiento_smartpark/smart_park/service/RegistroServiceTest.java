package com.estacionamiento_smartpark.smart_park.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.estacionamiento_smartpark.smart_park.model.Registro;
import com.estacionamiento_smartpark.smart_park.model.Auto;
import com.estacionamiento_smartpark.smart_park.repository.RegistroRepository;

@SpringBootTest
public class RegistroServiceTest {

    @Autowired
    private RegistroService registroService;

    @MockBean
    private RegistroRepository registroRepository;

    private Registro createRegistro() {
        return new Registro(
            1L, 
            LocalDateTime.now(),
            LocalDateTime.now(),
            new Auto()
        );
    }

    @Test
    public void testFindAll() {
        when(registroRepository.findAll()).thenReturn(List.of(createRegistro()));
        List<Registro> registros = registroService.findAll();
        assertNotNull(registros);
        assertEquals(1, registros.size());
    }

    @Test
    public void testFindById() {
        when(registroRepository.findById(1L)).thenReturn(java.util.Optional.of(createRegistro()));
        Registro registro = registroService.findById(1L);
        assertNotNull(registro);
        assertEquals(1L, registro.getId());
    }

    @Test
    public void testSave() {
        Registro registro = createRegistro();
        when(registroRepository.save(registro)).thenReturn(registro);
        Registro savedRegistro = registroService.save(registro);
        assertNotNull(savedRegistro);
        assertEquals(1L, savedRegistro.getId());
    }

    @Test
    public void testPatchRegistro() {
        Registro existingRegistro = createRegistro();
        Registro patchData = new Registro();
        patchData.setId(1L);

        when(registroRepository.findById(1L)).thenReturn(java.util.Optional.of(existingRegistro));
        when(registroRepository.save(any(Registro.class))).thenReturn(existingRegistro);

        Registro patchedRegistro = registroService.patchRegistro(1L, patchData);
        assertNotNull(patchedRegistro);
        assertEquals(1L, patchedRegistro.getId());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(registroRepository).deleteById(1L);
        registroService.deleteById(1L);
        verify(registroRepository, times(1)).deleteById(1L);
    }

}

