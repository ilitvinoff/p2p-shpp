package com.shpp.p2p.cs.ilitvinov.assignment16.test;

import com.shpp.p2p.cs.ilitvinov.assignment16.MyLinkedList;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyLinkedListTest {

    private static MyLinkedList<Integer> myLinkedList;
    private static final int EL_AMOUNT = 65535;

    @org.junit.BeforeClass
    public static void initiate() {
        myLinkedList = new MyLinkedList<>();
    }


    @org.junit.Test
    public void test1_shouldAddToEndOfList() {

        Integer[] pattern = new Integer[]{0, 1, 2, 3, 4};
        for (int i = 0; i < EL_AMOUNT; i++) {
            myLinkedList.add(i);
            assertArrayEquals(Arrays.copyOf(pattern, i + 1), myLinkedList.toArray());
        }
        assertArrayEquals(pattern, myLinkedList.toArray());
    }

    @org.junit.Test
    public void test2_shouldAddToByIndex() {
        Integer[] pattern = new Integer[]{9, 0, 1, 2, 9, 3, 9, 4};
        Integer element = 9;

        myLinkedList.add(0, element);
        myLinkedList.add(myLinkedList.size() - 1, element);
        myLinkedList.add((int) Math.ceil(myLinkedList.size() / 2.0), element);

        assertArrayEquals(pattern, myLinkedList.toArray());
    }

    @org.junit.Test
    public void test3_shouldGet() {
        Integer[] pattern = new Integer[]{9, 0, 1, 2, 9, 3, 9, 4};
        Integer[] container = new Integer[8];
        for (int i = 0; i < 8; i++) {
            container[i] = myLinkedList.get(i);
        }
        assertArrayEquals(pattern, container);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void test4_shouldGetThrowException() {
        myLinkedList.get(100);
    }

    @org.junit.Test
    public void test5_shouldSet() {
        Integer[] pattern = new Integer[]{888, 0, 1, 2, 888, 3, 9, 888};
        Integer element = 888;

        myLinkedList.set(0, element);
        myLinkedList.set(myLinkedList.size() - 1, element);
        myLinkedList.set((int) Math.ceil(myLinkedList.size() / 2.0), element);

        assertArrayEquals(pattern, myLinkedList.toArray());
    }

    @org.junit.Test
    public void test6_shouldSize() {
        assert myLinkedList.size() == 8;
    }

    @org.junit.Test
    public void test7_shouldRemoveByIndex() {
        Integer[] pattern = new Integer[]{0, 1, 2, 3, 9};

        myLinkedList.remove(0);
        myLinkedList.remove(myLinkedList.size() - 1);
        myLinkedList.remove((int) Math.ceil(myLinkedList.size() / 2.0));

        assertArrayEquals(pattern, myLinkedList.toArray());
    }

    @org.junit.Test
    public void test8_shouldRemoveByElement() {
        Integer[] pattern = new Integer[]{1, 3};
        Integer[] elToRemove = new Integer[]{0, 2, 9};

        for (Integer integer : elToRemove) {
            myLinkedList.remove(integer);
        }
        assertArrayEquals(pattern, myLinkedList.toArray());
    }

    @org.junit.Test
    public void test9_shouldClear() {
        Integer[] pattern = new Integer[]{};
        myLinkedList.clear();
        assertArrayEquals(pattern, myLinkedList.toArray());
        assert myLinkedList.size() == 0;
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void test10_shouldAddByIndexThrowException() {
        myLinkedList.add(100, 100);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void test11_shouldSetByIndexThrowException() {
        myLinkedList.set(100, 100);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void test12_shouldRemoveByIndexThrowException() {
        myLinkedList.remove(100);
    }

    @org.junit.Test
    public void test13_shouldAddFirst() {
        Integer[] pattern = new Integer[]{4, 3, 2, 1, 0};
        for (int i = 0; i < EL_AMOUNT; i++) {
            myLinkedList.addFirst(i);
        }
        assertArrayEquals(pattern, myLinkedList.toArray());
    }

    @org.junit.Test
    public void test14_shouldRemoveFirst() {
        Integer[] pattern = new Integer[]{4, 3, 2, 1, 0};
        for (int i = 0; i < EL_AMOUNT; i++) {
            assert pattern[i].equals(myLinkedList.removeFirst());
        }
    }

    @org.junit.Test
    public void test15_shouldAddLast() {
        Integer[] pattern = new Integer[]{0, 1, 2, 3, 4};
        for (int i = 0; i < EL_AMOUNT; i++) {
            myLinkedList.addLast(i);
        }
        assertArrayEquals(pattern, myLinkedList.toArray());
    }

    @org.junit.Test
    public void test16_shouldRemoveLast() {
        Integer[] pattern = new Integer[]{4, 3, 2, 1, 0};
        for (int i = 0; i < EL_AMOUNT; i++) {
            assert pattern[i].equals(myLinkedList.removeLast());
        }
    }

    @org.junit.Test
    public void test17_shouldForEach() {
        MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();
        Integer[] pattern = new Integer[]{0, 1, 2, 3, 4};
        for (int i = 0; i < EL_AMOUNT; i++) {
            myLinkedList.add(i);
        }

        int index = 0;
        for (Integer el : myLinkedList) {
            assert pattern[index++].equals(el);
        }
    }
}