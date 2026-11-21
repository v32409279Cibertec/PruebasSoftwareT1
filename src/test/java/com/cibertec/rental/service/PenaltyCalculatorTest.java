package com.cibertec.rental.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PenaltyCalculatorTest {

    private final PenaltyCalculator calculator = new PenaltyCalculator();

    @Test
    @DisplayName("Debe lanzar IllegalStateException cuando los dias de retraso son negativos")
    void debeLanzarExcepcionCuandoLosDiasSonNegativos() {
        assertThrows(IllegalStateException.class,
                () -> calculator.calculateLatePenalty(-1));
    }

    @Test
    @DisplayName("Debe calcular penalizacion cero cuando la devolucion es puntual")
    void debeRetornarCeroCuandoNoHayRetraso() {
        assertEquals(0, calculator.calculateLatePenalty(0));
    }

    @Test
    @DisplayName("Debe cobrar la tarifa diaria cuando el retraso es de hasta siete dias")
    void debeCalcularPenalizacionLeveHastaSieteDias() {
        assertEquals(105, calculator.calculateLatePenalty(7));
    }

    @Test
    @DisplayName("Debe aplicar la penalidad adicional desde el octavo dia")
    void debeAplicarPenalidadDeSeveridadDesdeElOctavoDia() {
        assertEquals(130, calculator.calculateLatePenalty(8));
    }

    @Test
    @DisplayName("No debe superar el tope maximo de trescientos dolares")
    void debeAplicarElTopeMaximoDePenalizacion() {
        assertEquals(300, calculator.calculateLatePenalty(30));
    }
}
