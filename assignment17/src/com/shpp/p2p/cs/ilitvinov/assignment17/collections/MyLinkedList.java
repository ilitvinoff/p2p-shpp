package com.shpp.p2p.cs.ilitvinov.assignment17.collections;

import java.util.Iterator;

/**
 * Analog of LinkedList Class
 */

public class MyLinkedList<T> implements MyList<T>{


    /**
     * Anchor(link) to first element in list
     */
    private Node<T> first = null;

    /**
     * Anchor(link) to the last element in list
     */
    private Node<T> last = null;

    /**
     * Current size of the list
     */
    private int size = 0;

    public MyLinkedList() {
    }

    /**
     * Add element to the end of the list.
     *
     * @param element - element has type of the list
     */
    @Override
    public void add(T element) {
        if (first != null && last != null) {
            last = last.setChild(new Node<>(element, last, null));
        } else if (first == null)
            first = new Node<>(element, null, null);
        else {
            Node<T> node = new Node<>(element, first, null);
            first.child = node;
            last = node;
        }

        size++;
    }

    /**
     * Insert element to position(index).
     *
     * @param index   - int value of position
     * @param element - element has type of the list
     */
    @Override
    public void add(int index, T element) {
        checkIndex(index);
        if (index == 0) {
            Node<T> node = new Node<>(element, null, first);
            if (first != null)
                first.parent = node;
            first = node;
        } else {
            Node<T> nodeOnIndex = goTo(index);
            Node<T> node = new Node<>(element, nodeOnIndex.parent, nodeOnIndex);
            nodeOnIndex.parent.child = node;
            nodeOnIndex.parent = node;
        }
        size++;
    }

    /**
     * Add element to the first position in the list.
     *
     * @param element - element's value
     */
    public void addFirst(T element) {
        this.add(0, element);
    }

    /**
     * Add element to the last position in the list.
     *
     * @param element - element's value
     */
    public void addLast(T element) {
        if (last == null)
            if (first == null)
                first = new Node<>(element, null, null);
            else {
                Node<T> node = new Node<>(element, first, null);
                last = node;
                first.child = node;
            }
        else
            last = last.setChild(new Node<>(element, last, null));
        size++;
    }

    /**
     * Return the value of element at indexed position
     */
    @Override
    public T get(int index) {
        checkIndex(index);
        return goTo(index).value;
    }

    /**
     * Return the value of element at first position in the list
     */
    public T getFirst() {
        return this.get(0);
    }

    /**
     * Set value to element at indexed position
     */
    @Override
    public void set(int index, T element) {
        checkIndex(index);
        goTo(index).value = element;
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
        checkIndex(index);
        if (index == 0) {
            T res = first.value;
            if (first.child != null)
                first = first.child;
            else first = null;
            size--;
            return res;
        }
        if (index == size - 1) {
            T res = last.value;
            if (index == 1) {
                last = null;
                first.child = null;

            } else {
                last = last.parent;
                last.child = null;
            }
            size--;
            return res;
        }
        Node<T> node = goTo(index);
        node.parent.child = node.child;
        node.child.parent = node.parent;
        size--;
        return node.value;
    }

    /**
     * Delete element at the first position in the list
     */
    public T removeFirst() {
        return this.remove(0);
    }

    /**
     * Delete element at the last position in the list
     */
    public T removeLast() {
        return this.remove(size - 1);
    }

    /**
     * Delete element from list by element's value.
     *
     * @return true - if found and deleted element with such value, false - if not.
     */
    @Override
    public boolean remove(T element) {
        Node<T> temp = first;
        for (int i = 0; i < size; i++) {
            if (temp.value.equals(element))
                break;
            temp = temp.child;
        }
        if (temp == null)
            return false;
        if (temp != first && temp != last) {
            temp.parent.child = temp.child;
            temp.child.parent = temp.parent;
            size--;
            return true;
        }
        if (temp == first) {
            if (temp.child != null && temp.child.parent != null)
                temp.child.parent = null;
            first = temp.child;
            size--;
            return true;
        }
        temp.parent.child = null;
        last = temp.parent;
        size--;
        return true;
    }

    /**
     * Delete all elements from the list
     */
    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] res = (T[]) new Object[size];
        int index = 0;
        for (Node<T> i = first; i != null; i = i.child) {
            res[index++] = i.value;
        }
        if (index != size)
            throw new RuntimeException("Kakayato Di4");
        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (Node<T> i = first; i != null; i = i.child) {
            sb.append(i.value.toString());
            if (i.child != null)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    private void checkIndex(int index) {
        if ((index >= size && !(size == 0 && index == size)) || index < 0)
            throw new IllegalArgumentException("Index out of bounds: " + index);
    }

    /**
     * Return element from indexed position
     */
    private Node<T> goTo(int index) {
        Node<T> pre = first;
        int i = 1;
        while (i <= index) {
            pre = pre.child;
            i++;
        }
        return pre;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {

            Node<T> res = new Node<>(null, null, first);

            @Override
            public boolean hasNext() {
                return res.child != null;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    res = res.child;
                    return res.value;
                }
                throw new RuntimeException("Error while iteration LinkedList.");
            }
        };
    }

    static class Node<T> {
        T value;
        Node<T> parent;
        Node<T> child;

        public Node(T value, Node<T> parent, Node<T> child) {
            this.value = value;
            this.parent = parent;
            this.child = child;
        }

        public Node<T> setChild(Node<T> child) {
            this.child = child;
            return child;
        }

    }
}
