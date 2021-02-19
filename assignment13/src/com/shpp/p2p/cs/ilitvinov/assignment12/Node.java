package com.shpp.p2p.cs.ilitvinov.assignment12;

/**
 * Class to describe node.
 */
public class Node {
    private final boolean value;
    private final int x;
    private final int y;
    private boolean isVisited = false;

    public Node(boolean value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public boolean getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
