package com.cibertec.loyalty;

public record LoyaltyResponse(Long userId, int pointsEarned, boolean isVip) {
}
