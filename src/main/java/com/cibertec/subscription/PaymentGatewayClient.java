package com.cibertec.subscription;

public interface PaymentGatewayClient {

    boolean isCardValid(Long id);

    void chargeMonthlyFee(Long id);
}
