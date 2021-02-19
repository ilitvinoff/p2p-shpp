package com.shpp.p2p.cs.ilitvinov.assignment3;

import com.shpp.cs.a.console.TextProgram;


/**
 * Program calculates raising to power
 */

public class Assignment3Part3 extends TextProgram {

    private static final double BASE = 0;
    private static final int EXPONENT = -2;

    public void run() {

        try {

            println("" + raiseToPower(BASE, EXPONENT));
        }catch(NullPointerException e){
            System.out.println("Cant divide by zero!");
        }
    }

    /*Method calculates raising to power*/
    private double raiseToPower(double base, int exponent) {

        double buffer = 1;
        if (exponent > 0) {

            buffer = nTimesMultiplying(base, exponent);
        }

        try {
            if (exponent < 0) {
                if (base == 0) {
                    buffer = 1 / 0;
                }

                exponent = exponent * (-1);
                buffer = 1 / nTimesMultiplying(base, exponent);
            }

        } catch (Exception excptn) {
            buffer = Double.parseDouble(null);
        }

        return (buffer);
    }

    /*Multiply's "base*base" for "n" times*/
    private double nTimesMultiplying(double base, int n) {

        double buffer = 1;

        for (int i = n; i > 0; --i) {
            buffer *= base;
        }
        return (buffer);
    }
}