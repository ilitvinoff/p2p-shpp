package com.shpp.p2p.cs.ilitvinov.assignment17.silhouettes;

import com.shpp.p2p.cs.ilitvinov.assignment17.silhouettes.postsilhouettes.PostSil;
import com.shpp.p2p.cs.ilitvinov.assignment17.silhouettes.presilhouettes.PreSil;

/**
 * Test silhouettes's counter application with standard java collections and my collections. Logging work time.
 */

public class TestSilhouettes {

    /**
     * Default args's values
     */
    private static final String IMAGE_PATH = "assets/10man.jpg";

    /**
     * Counting's iterations amount
     */
    private static final int REPEAT_AMOUNT = 100;

    /**
     * Printout testing result for search in depth, search in wide, recursive search in depth algorithms/
     */
    public void testSilhouettes() {
        PreSil preSil = new PreSil();
        PostSil postSil = new PostSil();

        long[] preSilTimeArray = countWorkTime(preSil);
        long[] postSilTimeArray = countWorkTime(postSil);

        printoutResult("Queue", preSilTimeArray[0], postSilTimeArray[0]);
        printoutResult("Stack", preSilTimeArray[1], postSilTimeArray[1]);
        printoutResult("Recursive", preSilTimeArray[2], postSilTimeArray[2]);
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

    /**
     * Log work time of archiving.
     *
     * @param silhouettes - Testing object
     * @return work time in milliseconds as long value
     */
    private long[] countWorkTime(Testing silhouettes) {
        long[] timeArrayQueue = new long[REPEAT_AMOUNT];
        long[] timeArrayStack = new long[REPEAT_AMOUNT];
        long[] timeArrayRecursive = new long[REPEAT_AMOUNT];

        for (int i = 0; i < REPEAT_AMOUNT; i++) {
            long[] temp = silhouettes.proceed(IMAGE_PATH);
            timeArrayQueue[i] = temp[0];
            timeArrayStack[i] = temp[1];
            timeArrayRecursive[i] = temp[2];
        }

        return new long[]{average(timeArrayQueue), average(timeArrayStack), average(timeArrayRecursive)};
    }

    private void printoutResult(String algorithmName, long StandardCollectionsTime, long MyCollectionsTime) {

        System.out.println("SILHOUETTES RESULT:");
        if (StandardCollectionsTime > MyCollectionsTime)
            System.out.printf("My collections won for using %s!\nStandard collection's time: %dms\nMy collections time: %dms\nDifference in time: %dms\n",
                    algorithmName, StandardCollectionsTime, MyCollectionsTime, StandardCollectionsTime - MyCollectionsTime);
        else
            System.out.printf("Standard collections won for using %s!\nStandard collection's time: %dms\nMy collections time: %dms\nDifference in time: %dms\n",
                    algorithmName, StandardCollectionsTime, MyCollectionsTime, MyCollectionsTime - StandardCollectionsTime);
    }

}
