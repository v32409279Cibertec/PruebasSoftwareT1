package com.cibertec.loyalty.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoyaltyPointsCalculatorTest {

    private final LoyaltyPointsCalculator calculator = new LoyaltyPointsCalculator();

    @Test
    @DisplayName("Debe lanzar IllegalArgumentException cuando el monto de compra es nulo")
    void debeLanzarExcepcionCuandoElMontoEsNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateAccumulatedPoints(null, "VIP"));
    }

    @Test
    @DisplayName("Debe lanzar IllegalArgumentException cuando el monto es cero o negativo")
    void debeLanzarExcepcionCuandoElMontoEsCeroONegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateAccumulatedPoints(BigDecimal.ZERO, "REGULAR"));

        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateAccumulatedPoints(new BigDecimal("-10"), "REGULAR"));
    }

    @Test
    @DisplayName("Debe asignar un punto por cada unidad entera a un cliente regular")
    void debeCalcularPuntosParaClienteRegular() {
        int points = calculator.calculateAccumulatedPoints(
                new BigDecimal("125.75"), "REGULAR");

        assertEquals(125, points);
    }

    @Test
    @DisplayName("Debe duplicar los puntos y truncar decimales para un cliente VIP")
    void debeDuplicarPuntosParaClienteVip() {
        int points = calculator.calculateAccumulatedPoints(
                new BigDecimal("125.75"), "VIP");

        assertEquals(251, points);
    }
}
