package com.sanjeev.dci;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class CanaryTest {

    @Test
    public void oneTest() {
        Canary canary = new Canary();

        canary.one();

        assertTrue(true);
    }

    @Test
    public void sevenTest() {
        Canary canary = new Canary();

        canary.seven();

        assertTrue(true);
    }

    @Test
    public void eightTest() {
        Canary canary = new Canary();

        canary.eight();

        assertTrue(true);
    }

    @Test
    public void nineTest() {
        Canary canary = new Canary();

        canary.nine();

        assertTrue(true);
    }
}
