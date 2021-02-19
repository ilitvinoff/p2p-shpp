package com.shpp.p2p.cs.ilitvinov.assignment17.collections;

public interface MyList<T> extends Iterable<T> {

    void add(T element);

    void add(int index, T element);

    T get(int index);

    void set(int index, T element);

    int size();

    T remove(int index);

    boolean remove(T element);

    void clear();
}
