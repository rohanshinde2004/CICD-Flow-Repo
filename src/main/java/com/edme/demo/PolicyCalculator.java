package com.edme.demo;

/**
 * Tiny business class used to demonstrate compile, test, QA, and package jobs.
 */
public class PolicyCalculator {

    /**
     * Calculates a simple annual premium.
     *
     * @param basePremium base premium, which must not be negative
     * @param riskLoading additional risk amount, which must not be negative
     * @return base premium plus risk loading
     */
    public int calculatePremium(int basePremium, int riskLoading) {
        if (basePremium < 0 || riskLoading < 0) {
            throw new IllegalArgumentException("Premium values cannot be negative");
        }
        return basePremium + riskLoading;
    }

    /**
     * Returns a simple service health message.
     *
     * @return health status
     */
    public String health() {
        return "Policy Calculator is UP";
    }
}
