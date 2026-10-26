package com.edme.demo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PolicyCalculatorTest {

    @Test
    public void addsBasePremiumAndRiskLoading() {
        PolicyCalculator calculator = new PolicyCalculator();

        assertEquals(1250, calculator.calculatePremium(1000, 250));
    }

    @Test
    public void rejectsNegativePremiumValues() {
        PolicyCalculator calculator = new PolicyCalculator();

        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculatePremium(-1, 250));
    }

    @Test
    public void returnsHealthMessage() {
        PolicyCalculator calculator = new PolicyCalculator();

        assertEquals("Policy Calculator is UP", calculator.health());
    }
}
