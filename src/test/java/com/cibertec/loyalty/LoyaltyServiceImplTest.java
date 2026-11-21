package com.cibertec.loyalty;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class LoyaltyServiceImplTest {

    @Mock
    private LoyaltyRepository loyaltyRepository;

    @InjectMocks
    private LoyaltyServiceImpl loyaltyService;

    @Test
    @DisplayName("Debe rechazar un monto invalido y no registrar puntos en el repositorio")
    void debeRechazarMontoInvalidoSinRegistrarPuntos() {
        Long userId = 10L;

        assertThrows(IllegalArgumentException.class,
                () -> loyaltyService.calculateAndAssignPoints(userId, BigDecimal.ZERO));

        verify(loyaltyRepository, never()).addPoints(eq(userId), anyInt());
        verify(loyaltyRepository, never()).isVipUser(userId);
    }

    @Test
    @DisplayName("Debe duplicar los puntos VIP y actualizar el repositorio con el valor exacto")
    void debeAsignarPuntosDuplicadosAlClienteVip() {
        Long userId = 25L;
        BigDecimal purchaseAmount = new BigDecimal("125.75");
        when(loyaltyRepository.isVipUser(userId)).thenReturn(true);

        LoyaltyResponse response = loyaltyService.calculateAndAssignPoints(userId, purchaseAmount);

        assertEquals(userId, response.userId());
        assertEquals(250, response.pointsEarned());
        assertEquals(true, response.isVip());
        verify(loyaltyRepository).addPoints(userId, 250);
    }
}
