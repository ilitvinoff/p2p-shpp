package com.shpp.p2p.cs.ilitvinov.assignment17.collections.test;

import com.shpp.p2p.cs.ilitvinov.assignment17.collections.MyHashMap;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class MyHashMapTest {

    private static final int EL_AMOUNT = 65536;
    private MyHashMap<Integer, Integer> myHashMap;

    @Before
    public void setUp() {
        myHashMap = new MyHashMap<>();
        for (int i = 0; i < EL_AMOUNT; i++) {
            myHashMap.put(i, i);
        }
    }

    @Test
    public void shouldPutAndGetAndSize() {
        for (Integer i = 0; i < EL_AMOUNT; i++) {
            assertEquals(i,myHashMap.get(i));
        }

        assertEquals(EL_AMOUNT, myHashMap.size());
    }

    @Test
    public void shouldContainsKey() {
        for (int i = 0; i < EL_AMOUNT; i++) {
            assertTrue(myHashMap.containsKey(i));
        }
    }

    @Test
    public void shouldClearAndIsEmpty() {
        myHashMap.clear();
        assertTrue(myHashMap.isEmpty());
    }

    @Test
    public void shouldKeySet() {
        for (Integer k : myHashMap.keySet()) {
            assertEquals(k, myHashMap.get(k));
        }
    }

    @Test
    public void shouldRemove(){

        for (Integer i = 0; i < EL_AMOUNT; i++) {
            assertEquals(i,myHashMap.remove(i));
            assertEquals(EL_AMOUNT-i-1,myHashMap.size());
        }

        assertTrue(myHashMap.isEmpty());

    }
}