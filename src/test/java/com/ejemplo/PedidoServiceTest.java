package com.ejemplo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoServiceTest {

    DescuentoRepository repo = new DescuentoRepository();
    PedidoService service = new PedidoService(repo);

    @Test
    void testSinDescuentoYEnvioNormal() {

        double total = service.calcularTotal(100, "PROMO10", false);
        assertEquals(100.0, total);

    }

    @Test
    void testConDescuentoYEnvioExpress() {

        double total = service.calcularTotal(100, "PROMO10", true);
        assertEquals(110.0, total); // 100 - 10% + 20

    }

    @Test
    void testConDescuentoYEnvioNormal() {

        double total = service.calcularTotal(200, "PROMO10", false);
        assertEquals(190.0, total);

    }

    @Test
    void testSinDescuentoYEnvioExpress() {

        double total = service.calcularTotal(150, "PROMO10", true);
        assertEquals(155.0, total);
    }
}