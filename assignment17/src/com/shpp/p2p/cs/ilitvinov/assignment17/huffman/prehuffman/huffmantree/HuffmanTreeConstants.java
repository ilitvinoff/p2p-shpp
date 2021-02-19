package com.shpp.p2p.cs.ilitvinov.assignment17.huffman.prehuffman.huffmantree;

public interface HuffmanTreeConstants {

    /**
     * Marker for the root node.
     * The frequency of a node can only be above zero, therefore, when it is less, we know for sure that it is a root node.
     */
    int ROOT_MARKER = -1;

    /**
     * Since the Huffman tree is binary, the step for working correctly with it - is 2 :)
     */
    double AMOUNT_PER_STEP = 2;

}
