package com.shpp.p2p.cs.ilitvinov.assignment17.collections;

public class MyStack<T> extends MyLinkedList<T> {

    private MyArrayList<T> stack;

    private int size = 0;

    public MyStack() {
        stack = new MyArrayList<>();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void push(T element) {
        stack.add(size, element);
        size++;
    }

    public T pop() {
        if (size < 1)
            throw new IllegalArgumentException("Stack already empty, can't pop from empty stack!");
        return stack.remove(--size);
    }

    public T peek() {
        if (size < 1)
            throw new IllegalArgumentException("Stack already empty, can't peek from empty stack!");
        return stack.get(size - 1);
    }

    @Override
    public T[] toArray(){
        return stack.toArray();
    }

    @Override
    public String toString() {
        return "MyStack{" +
                "stack=" + stack +
                ", size=" + size +
                '}';
    }
}
