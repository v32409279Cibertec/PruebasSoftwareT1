package com.cibertec.loyalty;

public interface LoyaltyRepository {

    boolean isVipUser(Long userId);

    void addPoints(Long userId, int points);
}
