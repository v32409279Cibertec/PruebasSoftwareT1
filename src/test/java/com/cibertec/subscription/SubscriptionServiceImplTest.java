package com.cibertec.subscription;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private PaymentGatewayClient paymentGatewayClient;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Test
    @DisplayName("Debe suspender la suscripcion y evitar el cobro cuando la tarjeta esta vencida")
    void debeSuspenderSuscripcionSinProcesarCobroCuandoLaTarjetaEstaVencida() {
        Long subscriptionId = 100L;
        Subscription subscription = new Subscription(
                subscriptionId, "ACTIVE", LocalDate.of(2026, 1, 31));

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(subscription);
        when(paymentGatewayClient.isCardValid(subscriptionId)).thenReturn(false);

        ExpiredCardException exception = assertThrows(ExpiredCardException.class,
                () -> subscriptionService.renewSubscription(subscriptionId));

        assertEquals("La tarjeta asociada ha expirado", exception.getMessage());
        verify(subscriptionRepository).updateStatus(subscriptionId, "SUSPENDED");
        verify(paymentGatewayClient).isCardValid(subscriptionId);
        verify(paymentGatewayClient, never()).chargeMonthlyFee(subscriptionId);
        verify(subscriptionRepository, never())
                .updateBillingDate(eq(subscriptionId), any(LocalDate.class));
    }

    @Test
    @DisplayName("Debe cobrar y extender la fecha de renovacion exactamente un mes cuando la tarjeta es valida")
    void debeRenovarSuscripcionCuandoLaTarjetaEsValida() {
        Long subscriptionId = 200L;
        LocalDate currentDate = LocalDate.of(2026, 1, 31);
        LocalDate expectedDate = currentDate.plusMonths(1);
        Subscription subscription = new Subscription(subscriptionId, "ACTIVE", currentDate);

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(subscription);
        when(paymentGatewayClient.isCardValid(subscriptionId)).thenReturn(true);

        Subscription renewedSubscription = subscriptionService.renewSubscription(subscriptionId);

        assertEquals(subscriptionId, renewedSubscription.id());
        assertEquals("ACTIVE", renewedSubscription.status());
        assertEquals(expectedDate, renewedSubscription.nextBillingDate());
        verify(paymentGatewayClient).chargeMonthlyFee(subscriptionId);
        verify(subscriptionRepository).updateBillingDate(subscriptionId, expectedDate);
        verify(subscriptionRepository, never()).updateStatus(subscriptionId, "SUSPENDED");
    }
}
