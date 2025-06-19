package com.ejemplo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    DescuentoRepository mockRepository = mock(DescuentoRepository.class);
    PedidoService service = new PedidoService(mockRepository);

    @Test
    void testSinDescuentoYEnvioNormal() {
        when(mockRepository.obtenerPorcentaje(anyString())).thenReturn(0.0);
        double total = service.calcularTotal(100.0, null, false);
        assertEquals(110.0, total);
    }

    @Test
    void testConDescuentoYEnvioExpress() {
        when(mockRepository.obtenerPorcentaje("DESC10")).thenReturn(0.10);
        double total = service.calcularTotal(100.0, "DESC10", true);
        assertEquals(110.0, total);
    }

    @Test
    void testConDescuentoYEnvioNormal() {
        when(mockRepository.obtenerPorcentaje("DESC10")).thenReturn(0.10);
        double total = service.calcularTotal(200.0, "DESC10", false);
        assertEquals(190.0, total);
    }

    @Test
    void testSinDescuentoYEnvioExpress() {
        when(mockRepository.obtenerPorcentaje(anyString())).thenReturn(0.0);
        double total = service.calcularTotal(150.0, null, true);
        assertEquals(170.0, total);
    }

    @Test
    void testConMockDeDescuento() {
        when(mockRepository.obtenerPorcentaje("PROMO10")).thenReturn(0.10);
        double total = service.calcularTotal(100, "PROMO10", true);
        assertEquals(110.0, total);
    }
}