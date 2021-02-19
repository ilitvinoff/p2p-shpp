package com.shpp.p2p.cs.ilitvinov.assignment17.collections;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Analog of ArrayList Class
 */

public class MyArrayList<T> implements MyList<T> {

    /**
     * Default starting capacity of array-container
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Coefficient to change size of container if need
     */
    private static final double SIZE_CHANGER_COEFFICIENT = 1.5;

    /**
     * To store current capacity of array-container
     */
    private int capacity = 0;

    /**
     * Current element's amount in the list
     */
    private int size = 0;

    /**
     * Array-container to store elements
     */
    private T[] container;

    @SuppressWarnings("unchecked")
    public MyArrayList(int capacity) {

        if (capacity >= 0 && capacity < Integer.MAX_VALUE) {
            container = (T[]) new Object[capacity];
            this.capacity = capacity;
        } else {
            throw new IllegalArgumentException(String.format("Illegal capacity: %s", capacity));
        }
    }

    @SuppressWarnings("unchecked")
    public MyArrayList() {
        container = (T[]) new Object[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
    }

    /**
     * Add element to the end of the list.
     *
     * @param element - element has type of the list
     */
    @Override
    public void add(T element) {
        increaseArray();
        container[size] = element;
        size++;
    }

    /**
     * Insert element to position(index).
     *
     * @param index   - int value of position
     * @param element - element has type of the list
     */
    @Override
    @SuppressWarnings("unchecked")
    public void add(int index, T element) {
        if (size < index)
            size = index;
        increaseArray();
        T[] tempDest = (T[]) new Object[container.length + 1];
        System.arraycopy(container, 0, tempDest, 0, index);
        tempDest[index] = element;
        System.arraycopy(container, index, tempDest, index + 1, container.length - index);
        container = tempDest;
        size++;
    }

    /**
     * Return the value of element at indexed position
     */
    @Override
    public T get(int index) {
        if (index >= container.length)
            throw new IndexOutOfBoundsException("illegal index to get: " + index);
        return container[index];
    }

    /**
     * Set value to element at indexed position
     */
    @Override
    public void set(int index, T element) {
        if (size < index)
            size = index;
        increaseArray();
        container[index] = element;

    }

    /**
     * Return size value(int)
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Delete element at indexed position.
     *
     * @return element's value
     */
    @Override
    public T remove(int index) {
        decreaseArray();
        T res = container[index];
        System.arraycopy(container, index + 1, container, index, container.length - index - 1);
        size--;
        return res;
    }

    /**
     * Delete element from li`st by element's value.
     *
     * @return true - if found and deleted element with such value, false - if not.
     */
    @Override
    public boolean remove(T element) {
        decreaseArray();
        for (int i = 0; i < container.length; i++) {
            if (container[i] != null && container[i].equals(element)) {
                System.arraycopy(container, i + 1, container, i, container.length - i - 1);
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * Delete all elements from the list.
     * Back to default or fixed by user capacity.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        container = (T[]) new Object[capacity];
        size = 0;
    }

    /**
     * Increase container-array's size if needed.
     */
    private void increaseArray() {
        if (size < 0)
            throw new IllegalArgumentException(String.format("Size of ArrayList is out of range! Max size value is: %s", Integer.MAX_VALUE));
        if (size >= container.length - 1) {
            container = Arrays.copyOf(container, (int) (size * SIZE_CHANGER_COEFFICIENT));
        }
    }

    /**
     * Decrease container-array's size if needed.
     */
    private void decreaseArray() {
        if (size < container.length / SIZE_CHANGER_COEFFICIENT / 2 && size > capacity) {
            container = Arrays.copyOf(container, (int) (size * SIZE_CHANGER_COEFFICIENT));
        }
    }

    /**
     * Return elements of the list as array/
     */
    public T[] toArray() {
        return Arrays.copyOf(container, size);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (int i = 0; i < size; i++) {
            sb.append(container[i].toString());
            if (i + 1 != size)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public T next() {
                if (hasNext())
                    return MyArrayList.this.container[cursor++];
                throw new RuntimeException("Hvatit tut vam iterirovat' :)");
            }
        };
    }

}
