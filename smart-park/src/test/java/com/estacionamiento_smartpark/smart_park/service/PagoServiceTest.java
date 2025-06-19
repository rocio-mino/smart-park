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

import com.estacionamiento_smartpark.smart_park.model.Auto;
import com.estacionamiento_smartpark.smart_park.model.Pago;
import com.estacionamiento_smartpark.smart_park.model.Registro;
import com.estacionamiento_smartpark.smart_park.repository.PagoRepository;
import com.estacionamiento_smartpark.smart_park.repository.RegistroRepository;

@SpringBootTest
public class PagoServiceTest {

    @Autowired
    private PagoService pagoService;

    @MockBean
    private PagoRepository pagoRepository;

    @MockBean
    private RegistroRepository registroRepository;

    private Pago createPago() {
        return new Pago(
            1L, 
            LocalDateTime.now(),
            2000,
            "Efectivo",
            new Registro()
        );
    }

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
        when(pagoRepository.findAll()).thenReturn(List.of(createPago()));
        List<Pago> pagos = pagoService.obtenerTodosLosPagos();
        assertNotNull(pagos);
        assertEquals(1, pagos.size());
    }

    @Test
    public void testFindById() {
        when(pagoRepository.findById(1L)).thenReturn(java.util.Optional.of(createPago()));
        Pago pago = pagoService.findById(1L);
        assertNotNull(pago);
        assertEquals(1L, pago.getId());
    }

    @Test
    public void testSave() {
        Long registroId = 1L;
        String metodoPago = "Efectivo";
        Registro registro = createRegistro();

        when(registroRepository.findById(registroId)).thenReturn(java.util.Optional.of(registro));
        when(pagoRepository.existsByRegistroId(registroId)).thenReturn(false);
        when(pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> {
            Pago p = invocation.getArgument(0);
            p.setId(1L); 
            return p;
        });

        Pago savedPago = pagoService.crearPago(registroId, metodoPago);

        assertNotNull(savedPago);
        assertEquals(1L, savedPago.getId());
        assertEquals(metodoPago, savedPago.getMetodo());
        assertEquals(registro, savedPago.getRegistro());
    }


    @Test
    public void testPatchPago() {
        Pago existingPago = createPago();
        Pago patchData = new Pago();
        patchData.setId(1L);

        when(pagoRepository.findById(1L)).thenReturn(java.util.Optional.of(existingPago));
        when(pagoRepository.save(any(Pago.class))).thenReturn(existingPago);

        Pago patchedPago = pagoService.patchPago(1L, patchData);
        assertNotNull(patchedPago);
        assertEquals(1L, patchedPago.getId());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(pagoRepository).deleteById(1L);
        pagoService.eliminarPago(1L);
        verify(pagoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testObtenerPagosConDetallesRegistro() {
        List<Object[]> lista = pagoService.obtenerPagosConDetallesRegistro();
        assertNotNull(lista);
    }

}

