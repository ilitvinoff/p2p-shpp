package com.shpp.p2p.cs.ilitvinov.assignment17.huffman.posthuffman.huffmantree;

import com.shpp.p2p.cs.ilitvinov.assignment17.collections.MyArrayList;
import com.shpp.p2p.cs.ilitvinov.assignment17.collections.MyHashMap;
import com.shpp.p2p.cs.ilitvinov.assignment17.collections.MyPriorityQueue;

import java.io.IOException;

/**
 * Huffman Tree creating and using.
 */

public class HuffmanTree extends SortMerge implements HuffmanTreeConstants {

    private final Node[] sortedNodes;

    public HuffmanTree(Node[] sortedNodes) {
        this.sortedNodes = sortedNodes;
    }

    public HuffmanTree(MyHashMap<Byte, Integer> byteMap) {
        SortMerge sorter = new SortMerge();
        sortedNodes = sorter.sortMergeAscending(byteMap);
    }

    public Node[] getSortedNodes() {
        return sortedNodes;
    }

    /**
     * Build tree using Huffman algorithm.
     *
     * @return 1 node, as root of the tree
     */
    public Node buildTree() throws Exception {
        Node[] result = sortedNodes.clone();

        while (result.length != 1 && result.length > 0) {
            result = getRoot(result);
        }
        if (result[0].hasChild())
            result[0].setFrequency(ROOT_MARKER);
        return result[0];
    }

    public Node buildTreeWithPriorQueue() throws IOException {
        MyPriorityQueue<Node> priorityQueue = new MyPriorityQueue<>();
        for (Node node: sortedNodes) {
            priorityQueue.offer(node);
        }
        while (priorityQueue.size()>1){
            Node temp1 = priorityQueue.poll();
            Node temp2 = priorityQueue.poll();
            Node parent =  new Node(temp1.getFrequency()+temp2.getFrequency(),temp1.setBitValue((byte) 1),temp2.setBitValue((byte)0));
            temp1.setParent(parent);
            temp2.setParent(parent);
            priorityQueue.offer(parent);
        }
        Node result = priorityQueue.poll();
        if (result.hasChild())
            result.setFrequency(ROOT_MARKER);
        return result;
    }

    /**
     * For each unique byte of source (the lowest level in tree), create bit sequence and store it to Byte[].
     * While going from root to unique byte node, write values of graph's edge to array as bit value 1 or 0.
     * If left edge - 1,
     * if right - 0.
     *
     * @param root - root of the Huffman tree.
     * @return HashMap<Byte, Byte [ ]>, where:
     * key - unique byte value
     * value - Byte[], -sequence of bit to compress/decompress key
     */
    public MyHashMap<Byte, Byte[]> getMap(Node root) {
        MyHashMap<Byte, Byte[]> resultMap = new MyHashMap<>();
        searchInDepth(root, resultMap);
        return resultMap;
    }

    /**
     * Create 1 level of Huffman tree from array of nodes.
     *
     * @param nodes - Node[] sub level.
     * @return Node[] - upper level
     */
    private Node[] getRoot(Node[] nodes) throws IOException {
        SortMerge sorter = new SortMerge();
        if (nodes.length == 1)
            return nodes;
        Node[] temp = new Node[nodes.length - 1];
        byte leftBitValue = 1;
        byte rightBitValue = 0;
        int tempIndex = 0;

        temp[tempIndex] = new Node(nodes[0].getFrequency() + nodes[1].getFrequency(), nodes[0].setBitValue(leftBitValue), nodes[1].setBitValue(rightBitValue));
        nodes[0].setParent(temp[tempIndex]);
        nodes[1].setParent(temp[tempIndex++]);
        for (int i = (int) AMOUNT_PER_STEP; i < nodes.length; i++) {
            temp[tempIndex++] = nodes[i];
        }

        sorter.sortMergeAscending(temp);
        return temp;
    }

    /**
     * Implementation of a recursive deep-search algorithm for storing a bit sequence in an array.
     *
     * @param root      - root of the Huffman tree.
     * @param resultMap - HashMap<Byte, Byte[]>, where the result storing
     */
    private void searchInDepth(Node root, MyHashMap<Byte, Byte[]> resultMap) {


        if (root.getLeft() != null) {
            searchInDepth(root.getLeft(), resultMap);
        }

        if (root.getRight() != null) {
            searchInDepth(root.getRight(), resultMap);
        }

        if (!root.hasChild()) {
            resultMap.put(root.getName(), getBits(root));
        }
    }

    /**
     * Return bits's sequence for unique byte.
     *
     * @param node - node from the Huffman tree with unique byte.
     */
    private Byte[] getBits(Node node) {
        MyArrayList<Byte> invertedBits = new MyArrayList<>();
        setInvertedBits(node, invertedBits);
        return listToArray(invertedBits);
    }

    /**
     * Implementation of a recursive deep-search algorithm for storing an inverted bit sequence in an ArrayList.
     *
     * @param node         - node from the Huffman tree with unique byte.
     * @param invertedBits - ArrayList<Byte> to store inverted bit sequence.
     */
    private void setInvertedBits(Node node, MyArrayList<Byte> invertedBits) {
        Node parent = node.getParent();
        if (parent != null && parent.getFrequency() > ROOT_MARKER) {
            setInvertedBits(parent, invertedBits);
        }
        invertedBits.add(node.getBitValue());
    }

    /**
     * Save inverted bit sequence from ArrayList to array with straight sorting
     */
    private Byte[] listToArray(MyArrayList<Byte> list) {
        Byte[] value = new Byte[list.size()];
        for (int i = 0; i < value.length; i++) {
            value[i] = list.get(i);
        }
        return value;
    }

}