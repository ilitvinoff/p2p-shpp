package com.shpp.p2p.cs.ilitvinov.assignment17.collections.test;

import com.shpp.p2p.cs.ilitvinov.assignment17.collections.MyPriorityQueue;
import org.junit.Before;

import java.util.PriorityQueue;

import static org.junit.Assert.*;

public class MyPriorityQueueTest {
    private static MyPriorityQueue<Integer> myPriorityQueue;
    private static PriorityQueue<Integer> priorityQueue;
    private static final int EL_AMOUNT = 65536;

    @Before
    public void setUp(){
        myPriorityQueue = new MyPriorityQueue<>();
        priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < EL_AMOUNT; i++) {
            myPriorityQueue.offer((int) (i * (Math.pow(-1, i))));
            priorityQueue.offer((int) (i * (Math.pow(-1, i))));
        }
    }

    @org.junit.Test
    public void shouldOffer() {
        assertArrayEquals(priorityQueue.toArray(),myPriorityQueue.toArray());
        myPriorityQueue.clear();
    }

    @org.junit.Test
    public void shouldPeekAndPollWhileIsEmptyLOL() {
        int index = 0;
        while (!myPriorityQueue.isEmpty()) {
            assertEquals(priorityQueue.peek(),myPriorityQueue.peek());
            assertEquals(priorityQueue.poll(),myPriorityQueue.poll());
        }
    }

}