package com.shpp.p2p.cs.ilitvinov.assignment16;

public class MyQueue<T> extends MyLinkedList<T> {

    private final MyLinkedList<T> queue;

    private int size = 0;

    public MyQueue() {
        this.queue = new MyLinkedList<>();
    }

    public void offer(T element) {
        queue.addLast(element);
        size++;
    }

    public T poll(){
        if (this.isEmpty())
            throw new IllegalArgumentException("Queue already empty, can't poll from empty queue!");
        size--;
        return queue.removeFirst();
    }

    public T peek(){
        if (this.isEmpty())
            throw new IllegalArgumentException("Queue already empty, can't peek from empty queue!");
        return queue.getFirst();
    }

    public boolean isEmpty(){
        return queue.size()==0;
    }

    public T[] toArray(){
        return queue.toArray();
    }

    @Override
    public String toString() {
        return "MyQueue{" +
                "queue=" + queue +
                ", size=" + size +
                '}';
    }
}
