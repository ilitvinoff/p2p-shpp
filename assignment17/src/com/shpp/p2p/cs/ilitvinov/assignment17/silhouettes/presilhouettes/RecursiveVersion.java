package com.shpp.p2p.cs.ilitvinov.assignment17.silhouettes.presilhouettes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecursiveVersion {
    private static final double THRESHOLD_SQUARE = 0.2;
    private static final int COMPARE_VALUE = 1;

    /**
     * Counting branches of nodes which matches given integer value.
     * The branch corresponds to an inextricable cluster of pixels in the image (silhouette).
     *
     * @param nodes - Node[][], each element of this array corresponds to pixel on image.
     *              Element value may be:
     *              0 - if pixel has background color
     *              1 - otherwise.
     * @return amount of branches.
     */
    public static int findSilhouette(Node[][] nodes) {
        List<Integer> branches = new ArrayList<>();

        for (int x = 0; x < nodes.length; x++) {
            for (int y = 0; y < nodes[x].length; y++) {
                if (nodes[x][y].getValue() == COMPARE_VALUE && !nodes[x][y].isVisited()) {
                    branches.add(goOnBranch(nodes, x, y, 0));
                }
            }
        }

        return filter(branches);
    }

    /**
     * Go through an inextricable cluster of nodes with a given integer value using a recursive deep search algorithm.
     *
     * @param nodes  - array of nodes, Node[x][y]
     * @param x      - row's position index in Node[][]
     * @param y      - column's position index in Node[][]
     * @param square - starting value to mark amount of visited nodes
     * @return integer - amount of visited nodes (all nodes from branch)
     */
    private static int goOnBranch(Node[][] nodes, int x, int y, int square) {
        nodes[x][y].setVisited(true);
        square++;


        if (x + 1 < nodes.length) {
            square = recurse(nodes, x + 1, y, square);
        }
        if (y + 1 < nodes[x].length) {
            square = recurse(nodes, x, y + 1, square);
        }
        if (x - 1 > 0) {
            square = recurse(nodes, x - 1, y, square);
        }
        if (y - 1 > 0) {
            square = recurse(nodes, x, y - 1, square);
        }
        return square;
    }

    private static int recurse(Node[][] nodes, int x, int y, int square) {
        if (nodes[x][y].getValue() == COMPARE_VALUE && !nodes[x][y].isVisited()) {
            square = goOnBranch(nodes, x, y, square);
        }
        return square;
    }

    /**
     * Returns the number of elements from List <Integer> whose value must be greater than the value of the MAX element
     * multiply the threshold
     */
    private static int filter(List<Integer> branches) {
        int amount = 0;
        int thresholdSquare = (int) (Collections.max(branches) * THRESHOLD_SQUARE);

        for (Integer value : branches) {
            if (value > thresholdSquare)
                amount++;
        }

        return amount;
    }
}
