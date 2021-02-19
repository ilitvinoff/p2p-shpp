package com.shpp.p2p.cs.ilitvinov.assignment17.huffman.posthuffman.huffmantree;

import java.io.IOException;

/**
 * Class to store important values in nodes of Huffman tree
 */

public class Node implements Comparable<Node>{

    private byte name; // Unique byte value
    private byte bitValue; // Bit value symbolizing value of edge from upper lvl to sub lvl.
    private int frequency; // Amount of unique byte in source

    private Node parent; // Parent node
    private Node left; // Left child node
    private Node right; // Right child node

    public Node(byte name, int frequency) {
        this.name = name;
        this.frequency = frequency;
    }

    public Node(int frequency, Node left, Node right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public int getFrequency() {
        return frequency;
    }

    public byte getBitValue() {
        return bitValue;
    }

    public Node getParent() {
        return parent;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public byte getName() {
        return name;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    /**
     * @param bitValue - 0 or 1
     * @throws IOException when wrong bitValue value
     */
    public Node setBitValue(byte bitValue) throws IOException {
        if (!(bitValue == 0 || bitValue == 1)) {
            throw new IOException("Wrong bitValue value!");
        }
        this.bitValue = bitValue;
        return this;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * @return false when no children nodes has.
     */
    public boolean hasChild() {
        return (this.getLeft() != null || this.getRight() != null);
    }

    @Override
    public String toString() {
        return String.format("name: %s; frequency: %s", name, frequency);
    }

    @Override
    public int compareTo(Node node) {
        return (Long.compare(this.getFrequency(),node.getFrequency()));
    }
}
