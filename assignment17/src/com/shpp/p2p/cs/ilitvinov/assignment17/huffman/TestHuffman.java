package com.shpp.p2p.cs.ilitvinov.assignment17.huffman;

import com.shpp.p2p.cs.ilitvinov.assignment17.huffman.posthuffman.PostHuffman;
import com.shpp.p2p.cs.ilitvinov.assignment17.huffman.prehuffman.PreHuffman;

/**
 * Test archiver application with standard java collections and my collections. Logging work time.
 */

public class TestHuffman {

    /**
     * Default args's values
     */
    private static final String[] DEFAULT_ARGS = {"-a", "assets/test.txt", "assets/test_archive.par"};

    /**
     * Archiving's iterations amount
     */
    private static final int REPEAT_AMOUNT = 5;

    /**
     * Printout testing's result.
     */
    public void testHuffman() throws Exception {
        PreHuffman preHuffman = new PreHuffman();
        PostHuffman postHuffman = new PostHuffman();

        long preHuffmanTime = countWorkTime(preHuffman);
        long postHuffmanTime = countWorkTime(postHuffman);

        System.out.println("HUFFMAN RESULTS:");
        if (preHuffmanTime < postHuffmanTime)
            System.out.printf("Standard collections won!\nStandard collection's time: %dms\nMy collections time: %dms\nDifference in time: %dms\n",
                    preHuffmanTime, postHuffmanTime, postHuffmanTime - preHuffmanTime);
        else
            System.out.printf("My collections won!\nStandard collection's time: %dms\nMy collections time: %dms\nDifference in time: %dms\n",
                    preHuffmanTime, postHuffmanTime, preHuffmanTime - postHuffmanTime);
    }

    /**
     * Log work time of archiving.
     *
     * @param huffmanObj - Testing object
     * @return work time in milliseconds as long value
     */
    private long countWorkTime(Testing huffmanObj) {
        long[] timeArray = new long[REPEAT_AMOUNT];

        for (int i = 0; i < REPEAT_AMOUNT; i++) {
            timeArray[i] = huffmanObj.proceed(DEFAULT_ARGS);
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
