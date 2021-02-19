package com.shpp.p2p.cs.ilitvinov.assignment17.collections;

import java.util.*;

/**
 * Hash table based implementation of the map, to store collection of pairs: <Key, Value>.
 * Each pair has unique key and any value.
 */

public class MyHashMap<K, V> {

    /**
     * Starting array of lists size
     */
    private static final int START_ARR_SIZE = 16;

    /**
     * Max array of lists size (2^30)
     */
    private static final int MAX_ARR_SIZE = 1 << 31;

    /**
     * Max list size
     */
    private static final int MAX_LIST_SIZE = 8;

    /**
     * Stores current array size
     */
    private int current_arr_size = START_ARR_SIZE;

    /**
     * Stores current list size
     */
    private int current_max_list_size = 0;

    /**
     * Current amount of elements in map
     */
    private int size = 0;

    /**
     * Array of dynamically changing lists for storing <Key, Value> pairs
     */
    @SuppressWarnings("unchecked")
    NodeList<Node>[] cellArray = new NodeList[START_ARR_SIZE];

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     */
    public void put(K key, V value) {
        Node newNode;
        if (key == null) {
            newNode = new Node(0, null, value);
        } else {
            newNode = new Node(key.hashCode(), key, value);
        }
        int index = current_arr_size - 1 & newNode.hash;
        NodeList<Node> listFromCell = (cellArray[index] = cellArray[index] == null ? new NodeList<>() : cellArray[index]);

        for (Node node : listFromCell) {
            if (node.equals(newNode)) {
                node.value = newNode.value;
                return;
            }
        }

        listFromCell.add(newNode);
        current_max_list_size = Math.max(current_max_list_size, listFromCell.size);
        size++;

        increaseCellArray();
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code (key==null ? k==null :
     * key.equals(k))}, then this method returns {@code v}; otherwise
     * it returns {@code null}.  (There can be at most one such mapping.)
     *
     * <p>A return value of {@code null} does not <i>necessarily</i>
     * indicate that the map contains no mapping for the key; it's also
     * possible that the map explicitly maps the key to {@code null}.
     * The {@link #containsKey containsKey} operation may be used to
     * distinguish these two cases.
     */
    public V get(K key) {
        Node newNode;
        if (key == null) {
            newNode = new Node(0, null, null);
        } else {
            newNode = new Node(key.hashCode(), key, null);
        }

        NodeList<Node> listFromCell = cellArray[current_arr_size - 1 & newNode.hash];

        if (listFromCell != null)
            for (Node node : listFromCell) {
                if (node.equals(newNode)) {
                    return node.value;
                }
            }
        return null;
    }

    /**
     * Returns {@code true} if this map contains a mapping for the
     * specified key.
     *
     * @param key The key whose presence in this map is to be tested
     * @return {@code true} if this map contains a mapping for the specified
     * key.
     */
    public boolean containsKey(K key) {
        Node newNode;
        if (key == null) {
            newNode = new Node(0, null, null);
        } else {
            newNode = new Node(key.hashCode(), key, null);
        }

        NodeList<Node> listFromCell = cellArray[current_arr_size - 1 & newNode.hash];

        if (listFromCell != null)
            for (Node node : listFromCell) {
                if (node.equals(newNode)) {
                    return true;
                }
            }
        return false;
    }

    /**
     * Removes all of the mappings from this map.
     * The map will be empty after this call returns.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        cellArray = new NodeList[START_ARR_SIZE];
        current_arr_size = START_ARR_SIZE;
        current_max_list_size = 0;
        size = 0;
    }

    /**
     * Return (@code true) if no elements in the map
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return current elements's amount in the map as integer
     */
    public int size() {
        return size;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with {@code key}, or
     * {@code null} if there was no mapping for {@code key}.
     * (A {@code null} return can also indicate that the map
     * previously associated {@code null} with {@code key}.)
     */
    public V remove(K key) {
        Node newNode;
        if (key == null) {
            newNode = new Node(0, null, null);
        } else {
            newNode = new Node(key.hashCode(), key, null);
        }

        NodeList<Node> listFromCell = cellArray[current_arr_size - 1 & newNode.hash];
        Node container = null;

        if (listFromCell != null)
            for (Node node : listFromCell) {
                if (node.equals(newNode)) {
                    container = node;
                    break;
                }
            }

        if (container != null) {
            size--;
            return listFromCell.remove(container).value;
        }
        return null;
    }

    /**
     * Return Set<K>, each element of the Set - is key from the map.
     */
    public Set<K> keySet() {
        HashSet<K> keySet = new HashSet<>();
        for (NodeList<Node> listFromCell : cellArray) {
            if (listFromCell != null)
                for (Node node : listFromCell) {
                    keySet.add(node.key);
                }
        }
        return keySet;
    }

    /**
     * Increase container-array's size if need
     */
    @SuppressWarnings("unchecked")
    private void increaseCellArray() {
        if (current_max_list_size > MAX_LIST_SIZE && current_arr_size < MAX_ARR_SIZE) {
            current_max_list_size = 0;
            int tempArraySize = current_arr_size * 2;
            NodeList<Node>[] tempArray = new NodeList[tempArraySize];
            HashSet<K> keySet = (HashSet<K>) keySet();

            for (K key : keySet) {
                Node newNode = new Node(key.hashCode(), key, remove(key));
                int index = tempArraySize - 1 & newNode.hash;
                NodeList<Node> listFromCell = (tempArray[index] = tempArray[index] == null ? new NodeList<>() : tempArray[index]);
                listFromCell.add(newNode);
                current_max_list_size = Math.max(current_max_list_size, listFromCell.size);
            }

            cellArray = tempArray;
            current_arr_size = tempArraySize;
            size = keySet.size();
        }
    }

    /**
     * Class Node describes characteristics of element in map.
     */
    private class Node {

        /**
         * hash = key.hashcode()
         */
        private final int hash;

        /**
         * Key value
         */
        private final K key;

        /**
         * Value :)
         */
        private V value;

        Node(int hash, K key, V value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return hash == node.hash &&
                    Objects.equals(key, node.key);
        }
    }

    /**
     * NodeList - class to describe characteristics and possibilities of linked list.
     */
    private static class NodeList<T> implements Iterable<T> {

        /**
         * First element in the list
         */
        Node<T> first;

        /**
         * Last element in the list
         */
        Node<T> last;

        /**
         * Current size of the list
         */
        int size = 0;

        /**
         * Add element to the list
         */
        void add(T value) {
            if (first == null) {
                first = new Node<>(value, null, null);
            } else if (last == null) {
                Node<T> node = new Node<>(value, first, null);
                last = node;
                first.right = node;
            } else {
                Node<T> node = new Node<>(value, last, null);
                last.right = node;
                last = node;
            }
            size++;
        }

        /**
         * Remove element from the list
         */
        T remove(T value) {
            Node<T> node = first;
            while (node != null) {
                if (node.value.equals(value)) {
                    T result = node.value;

                    if (node == first) {
                        first = first.right;
                    } else if (node == last) {
                        last = last.left;
                    } else {
                        node.left.right = node.right;
                        node.right.left = node.left;
                    }

                    size--;
                    return result;
                }
                node = node.right;
            }
            return null;
        }

        /**
         * To iterate through the list
         */
        @Override
        public Iterator<T> iterator() {
            return new Iterator<>() {

                Node<T> current = new Node<>(null, null, first);

                @Override
                public boolean hasNext() {
                    return current.right != null;
                }

                @Override
                public T next() {
                    if (hasNext()) {
                        current = current.right;
                        return current.value;
                    }
                    throw new RuntimeException("Wrong while iterate!");
                }
            };
        }

        /**
         * Describes node's characteristics in the list
         */
        static class Node<V> {
            V value;
            Node<V> left;
            Node<V> right;

            Node(V value, Node<V> left, Node<V> right) {
                this.value = value;
                this.left = left;
                this.right = right;
            }
        }
    }
}