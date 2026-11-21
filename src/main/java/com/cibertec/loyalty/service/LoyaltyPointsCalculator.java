package com.cibertec.loyalty.service;

import java.math.BigDecimal;

public class LoyaltyPointsCalculator {

    public int calculateAccumulatedPoints(BigDecimal purchaseAmount, String customerStatus) {
        if (purchaseAmount == null || purchaseAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto de compra debe ser mayor a cero");
        }

        int calculatedPoints = purchaseAmount.intValue();

        if ("VIP".equalsIgnoreCase(customerStatus)) {
            calculatedPoints = purchaseAmount.multiply(new BigDecimal("2")).intValue();
        }

        return calculatedPoints;
    }
}
