package com.shpp.p2p.cs.ilitvinov.assignment16.test;

import com.shpp.p2p.cs.ilitvinov.assignment16.MyStack;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class MyStackTest {
    private static MyStack<Integer> myStack;
    private static final int EL_AMOUNT = 5;

    @Before
    public void initialize() {
        myStack = new MyStack<>();
    }

    @Test
    public void shouldPush() {
        Integer[] pattern = new Integer[]{0, 1, 2, 3, 4};
        for (int i = 0; i < EL_AMOUNT; i++) {
            myStack.push(i);
            assertArrayEquals(Arrays.copyOf(pattern, i + 1), myStack.toArray());
        }
    }

    @Test
    public void shouldPollPeekAndIsEmpty() {
        Integer[] pattern = new Integer[]{0, 1, 2, 3, 4};
        for (int i = 0; i < EL_AMOUNT; i++) {
            myStack.push(i);
        }
        int i = 4;
        while (!myStack.isEmpty()){
            assert pattern[i].equals(myStack.peek());
            assert pattern[i--].equals(myStack.pop());
        }
        assert myStack.isEmpty();
    }

    @Test
    public void shouldForEach(){
        Integer[] pattern = new Integer[]{0, 1, 2, 3, 4};
        for (int i = 0; i < EL_AMOUNT; i++) {
            myStack.push(i);
        }

        int index = 0;
        for (Integer el: myStack) {
            assert el.equals(pattern[index++]);
        }
    }

}