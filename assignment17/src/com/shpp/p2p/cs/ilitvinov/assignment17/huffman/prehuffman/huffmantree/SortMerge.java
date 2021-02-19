package com.shpp.p2p.cs.ilitvinov.assignment17.huffman.prehuffman.huffmantree;

import java.util.HashMap;

/**
 * Take values from HashMap, sort it using merge algorithm and save result to array of nodes.
 */

public class SortMerge implements HuffmanTreeConstants {

    /**
     * Sorting values from HashMap.
     *
     * @param map - HashMap<Byte, Integer>, where:
     *            key - unique byte value.
     *            value - amount of key in source.
     * @return Node[] - sorted ascending according to node frequency.
     */
    public Node[] sortMergeAscending(HashMap<Byte, Integer> map) {
        Node[] result = mapToArray(map);

        int amountPerStep = 1;
        while (amountPerStep <= result.length) {
            sortArray(result, amountPerStep);
            amountPerStep *= 2;
        }
        return result;
    }

    /**
     * Sorting values ascending in array.
     *
     * @param nodes - Node[] array
     */
    public void sortMergeAscending(Node[] nodes) {
        int amountPerStep = 1;
        while (amountPerStep <= nodes.length) {
            sortArray(nodes, amountPerStep);
            amountPerStep *= 2;
        }
    }

    /**
     * 2nd lvl of sorting :)
     */
    private void sortArray(Node[] array, int amountPerStep) {
        int i = 0;
        while (i < array.length) {
            i += sortArrayParts(array, i, amountPerStep);
        }
    }

    /**
     * Compare 2 parts of array and sort them ascending.
     *
     * @param array         - source array.
     * @param offset        - index in source array from which starts 1st part.
     * @param amountPerStep - length of each part.
     * @return sum of the compared parts's length
     */
    private int sortArrayParts(Node[] array, int offset, int amountPerStep) {


        int index1 = offset;
        int end1 = Math.min(offset + amountPerStep, array.length);
        int index2 = end1;
        int end2 = Math.min(end1 + amountPerStep, array.length);
        Node[] temp = new Node[end2 - index1];
        int tempIndex = 0;

        while (index1 < end1 && index2 < end2) {
            temp[tempIndex++] = array[index1].getFrequency() > array[index2].getFrequency() ?
                    array[index2++] : array[index1++];
        }

        while (index1 < end1) {
            temp[tempIndex++] = array[index1++];
        }
        while (index2 < end2) {
            temp[tempIndex++] = array[index2++];
        }

        tempIndex = 0;
        while (offset < end2) {
            array[offset++] = temp[tempIndex++];
        }

        return temp.length;
    }

    private Node[] mapToArray(HashMap<Byte, Integer> map) {
        Node[] result = new Node[map.size()];
        int index = 0;
        for (Byte key : map.keySet()) {
            result[index] = new Node(key, map.get(key));
            index++;
        }
        return result;
    }
}
