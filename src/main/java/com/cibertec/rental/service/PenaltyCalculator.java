package com.cibertec.rental.service;

public class PenaltyCalculator {

    private static final int DAILY_RATE = 15;
    private static final int SEVERITY_RATE = 10;
    private static final int MAX_PENALTY = 300;

    public int calculateLatePenalty(int daysLate) {
        if (daysLate < 0) {
            throw new IllegalStateException("Los dias de retraso no pueden ser negativos");
        }

        if (daysLate == 0) {
            return 0;
        }

        int totalPenalty = daysLate * DAILY_RATE;

        if (daysLate > 7) {
            int extraDays = daysLate - 7;
            totalPenalty += extraDays * SEVERITY_RATE;
        }

        return Math.min(totalPenalty, MAX_PENALTY);
    }
}
