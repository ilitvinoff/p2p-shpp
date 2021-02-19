package com.shpp.p2p.cs.ilitvinov.assignment17.calc;

import com.shpp.p2p.cs.ilitvinov.assignment17.calc.postcalc.PostCalc;
import com.shpp.p2p.cs.ilitvinov.assignment17.calc.precalc.PreCalc;

/**
 * Test calc application with standard java collections and my collections. Logging work time.
 */

public class TestCalc {

    /**
     * Calculation's iterations amount
     */
    private static final int REPEAT_AMOUNT = 100;

    /**
     * Default args's values
     */
    private static final String[] TEST_CALC_EXPRESSION = new String[]{"sqrt(a+b-2*3/aa^a1+a_b+sin(a+b*log10(10))-cos(90)+tan(a)*atan(2)/log2(10))",
            "a=1", "b=2", "aa=3", "a1=4", "a_b=5"};

    /**
     * Printout testing's result.
     */
    public void testCalc() throws Exception {
        PreCalc preCalc = new PreCalc();
        PostCalc postCalc = new PostCalc();

        long preCalcTime = countWorkTime(preCalc);
        long postCalcTime = countWorkTime(postCalc);

        System.out.println("CALC RESULTS:");
        if (preCalcTime < postCalcTime)
            System.out.printf("Standard collections won!\nStandard collection's time: %dms\nMy collections time: %dms\nDifference in time: %dms\n",
                    preCalcTime, postCalcTime, postCalcTime - preCalcTime);
        else
            System.out.printf("My collections won!\nStandard collection's time: %dms\nMy collections time: %dms\nDifference in time: %dms\n",
                    preCalcTime, postCalcTime, preCalcTime - postCalcTime);
    }

    /**
     * Log work time of calculation.
     *
     * @param calcObj - Testing object
     * @return work time in milliseconds as long value
     */
    private long countWorkTime(Testing calcObj) throws Exception {
        long[] timeArray = new long[REPEAT_AMOUNT];

        for (int i = 0; i < REPEAT_AMOUNT; i++) {
            long start = System.currentTimeMillis();
            calcObj.proceed(TEST_CALC_EXPRESSION);
            long end = System.currentTimeMillis();
            timeArray[i] = end - start;
        }

        return average(timeArray);
    }

    /**
     * Return average value from long[] array.
     */
    private long average(long[] valuesArray) {
        long res = 0;
        for (long value : valuesArray) {
            res += value;
        }
        return res / valuesArray.length;
    }
}
