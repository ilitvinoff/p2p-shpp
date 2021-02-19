package com.shpp.p2p.cs.ilitvinov.assignment17.collections;

public class MyPriorityQueue<T extends Comparable<T>> extends MyArrayList<T> {

    public MyPriorityQueue() {
        super();
    }

    public MyPriorityQueue(int capacity) {
        super(capacity);
    }

    /**
     * To sort nodes when add element
     */
    private void bubbleUp() {
        //start index - is index of the last element in the list
        int indexBubble = this.size() - 1;
        int indexParent = Math.max((indexBubble - 1) / 2, 0);
        T bubble = this.get(indexBubble);
        T parent = this.get(indexParent);

        while (bubble.compareTo(parent) < 0 && indexBubble > 0) {
            this.set(indexParent, bubble);
            this.set(indexBubble, parent);
            indexBubble = indexParent;
            indexParent = Math.max((indexBubble - 1) / 2, 0);
            bubble = this.get(indexBubble);
            parent = this.get(indexParent);
        }
    }

    /**
     * To sort nodes when extract element
     */
    private void bubbleDown() {
        //start index - index of the  first element in the list
        int indexBubble = 0;
        int indexChild = indexBubble + 1;
        T bubble = this.get(indexBubble);
        T child = null;

        if (indexChild < this.size()) {
            child = this.get(indexChild);

            if (indexChild + 1 < this.size() && child.compareTo(this.get(indexChild + 1)) > 0) {
                child = this.get(++indexChild);
            }
        }

        while (indexChild < this.size() && child.compareTo(bubble) < 0) {
            this.set(indexBubble, child);
            this.set(indexChild, bubble);
            indexBubble = indexChild;
            indexChild = indexBubble * 2 + 1;
            bubble = this.get(indexBubble);

            if (indexChild < this.size()) {
                child = this.get(indexChild);

                if (indexChild + 1 < this.size() && child.compareTo(this.get(indexChild + 1)) > 0) {
                    child = this.get(++indexChild);
                }
            }

        }
    }


    /**
     * Inserts the specified element into this priority queue.
     */
    public void offer(T element) {
        this.add(element);
        bubbleUp();
    }

    /**
     * Return value of the first element in the queue.
     */
    public T peek() {
        return this.get(0);
    }

    /**
     * Extract first element from the queue and return it's value
     */
    public T poll() {
        if (this.size() - 1 == 0) {
            return this.remove(0);
        }

        T res = this.get(0);
        this.set(0, this.remove(this.size() - 1));
        bubbleDown();
        return res;
    }

    /**
     * Return (@code true) if no elements it the queue
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }
}
