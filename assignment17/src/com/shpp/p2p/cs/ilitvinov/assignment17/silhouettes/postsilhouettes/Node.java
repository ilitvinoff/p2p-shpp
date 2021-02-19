package com.shpp.p2p.cs.ilitvinov.assignment17.silhouettes.postsilhouettes;

/**
 * Class to describe node.
 */
public class Node {
    private final int value;
    private final int x;
    private final int y;
    private boolean isVisited = false;

    public Node(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public int getValue() {
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
