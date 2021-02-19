package com.shpp.p2p.cs.ilitvinov.assignment16.test;

import com.shpp.p2p.cs.ilitvinov.assignment16.MyArrayList;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyArrayListTest {

    private static MyArrayList<Integer> myArrayList;
    private static final int EL_AMOUNT = 5;

    @org.junit.BeforeClass
    public static void initiate() {
        myArrayList = new MyArrayList<>();
    }


    @org.junit.Test
    public void test1_shouldAddToEndOfList() {

        Integer[] pattern = new Integer[]{0, 1, 2, 3, 4};
        for (int i = 0; i < EL_AMOUNT; i++) {
            myArrayList.add(i);
            assertArrayEquals(Arrays.copyOf(pattern, i + 1), myArrayList.toArray());
        }
        assertArrayEquals(pattern, myArrayList.toArray());
    }

    @org.junit.Test
    public void test2_shouldAddToByIndex() {
        Integer[] pattern = new Integer[]{9, 0, 1, 2, 9, 3, 9, 4};
        Integer element = 9;

        myArrayList.add(0, element);
        myArrayList.add(myArrayList.size() - 1, element);
        myArrayList.add((int) Math.ceil(myArrayList.size() / 2.0), element);

        assertArrayEquals(pattern, myArrayList.toArray());
    }

    @org.junit.Test
    public void test3_shouldGet() {
        Integer[] pattern = new Integer[]{9, 0, 1, 2, 9, 3, 9, 4};
        Integer[] container = new Integer[8];
        for (int i = 0; i < 8; i++) {
            container[i] = myArrayList.get(i);
        }
        assertArrayEquals(pattern, container);
    }

    @org.junit.Test(expected = IndexOutOfBoundsException.class)
    public void test4_shouldGetThrowException() {
        myArrayList.get(100);
    }

    @org.junit.Test
    public void test5_shouldSet() {
        Integer[] pattern = new Integer[]{888, 0, 1, 2, 888, 3, 9, 888};
        Integer element = 888;

        myArrayList.set(0, element);
        myArrayList.set(myArrayList.size() - 1, element);
        myArrayList.set((int) Math.ceil(myArrayList.size() / 2.0), element);

        assertArrayEquals(pattern, myArrayList.toArray());
    }

    @org.junit.Test
    public void test6_shouldSize() {
        assert myArrayList.size() == 8;
    }

    @org.junit.Test
    public void test7_shouldRemoveByIndex() {
        Integer[] pattern = new Integer[]{0, 1, 2, 3, 9};

        myArrayList.remove(0);
        myArrayList.remove(myArrayList.size() - 1);
        myArrayList.remove((int) Math.ceil(myArrayList.size() / 2.0));

        assertArrayEquals(pattern, myArrayList.toArray());
    }

    @org.junit.Test
    public void test8_shouldRemoveByElement() {
        Integer[] pattern = new Integer[]{1, 3};
        Integer[] elToRemove = new Integer[]{0, 2, 9};

        for (Integer integer : elToRemove) {
            myArrayList.remove(integer);
        }
        assertArrayEquals(pattern, myArrayList.toArray());
    }

    @org.junit.Test
    public void test9_shouldClear() {
        Integer[] pattern = new Integer[]{};
        myArrayList.clear();
        assertArrayEquals(pattern, myArrayList.toArray());
        assert myArrayList.size() == 0;
    }

    @org.junit.Test
    public void test10_shouldForEach() {
        MyArrayList<Integer> myArrayList = new MyArrayList<>();
        Integer[] pattern = new Integer[]{0, 1, 2, 3, 4};
        for (int i = 0; i < EL_AMOUNT; i++) {
            myArrayList.add(i);
        }

        int index = 0;
        for (Integer el : myArrayList) {
            assert pattern[index++].equals(el);
        }
    }

}