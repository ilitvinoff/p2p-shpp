package com.shpp.p2p.cs.ilitvinov.assignment16.test;

import com.shpp.p2p.cs.ilitvinov.assignment16.MyQueue;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class MyQueueTest {

    private static MyQueue<Integer> myQueue;
    private static final int EL_AMOUNT = 5;

    @Before
    public void initialize() {
        myQueue = new MyQueue<>();
    }

    @Test
    public void shouldOffer() {
        Integer[] pattern = new Integer[]{0, 1, 2, 3, 4};
        for (int i = 0; i < EL_AMOUNT; i++) {
            myQueue.offer(i);
            assertArrayEquals(Arrays.copyOf(pattern, i + 1), myQueue.toArray());
        }
    }

    @Test
    public void shouldPollPeekAndIsEmpty() {
        Integer[] pattern = new Integer[]{0, 1, 2, 3, 4};
        for (int i = 0; i < EL_AMOUNT; i++) {
            myQueue.offer(i);
        }
        for (int i = 0; i < EL_AMOUNT; i++) {
            assert pattern[i].equals(myQueue.peek());
            assert pattern[i].equals(myQueue.poll());
        }
        assert myQueue.isEmpty();
    }

    @Test
    public void shouldForEach(){
        Integer[] pattern = new Integer[]{0, 1, 2, 3, 4};
        for (int i = 0; i < EL_AMOUNT; i++) {
            myQueue.offer(i);
        }

        int index = 0;
        for (Integer el:myQueue) {
            assert el.equals(pattern[index++]);
        }
    }

}