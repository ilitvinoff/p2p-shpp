package com.shpp.p2p.cs.ilitvinov.assignment17.silhouettes.postsilhouettes;

import com.shpp.p2p.cs.ilitvinov.assignment17.collections.MyArrayList;
import com.shpp.p2p.cs.ilitvinov.assignment17.collections.MyList;
import com.shpp.p2p.cs.ilitvinov.assignment17.collections.MyStack;

public class StackVersion {
    private static final double THRESHOLD_SQUARE = 0.2;
    private static final int COMPARE_VALUE = 1;
    private static final MyStack<Node> stack = new MyStack<>();

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
        MyList<Integer> branches = new MyArrayList<>();

        for (int x = 0; x < nodes.length; x++) {
            for (int y = 0; y < nodes[x].length; y++) {
                if (nodes[x][y].getValue() == COMPARE_VALUE && !nodes[x][y].isVisited()) {
                    branches.add(goOnBranch(nodes, x, y));
                }
            }
        }

        return filter(branches);
    }

    /**
     * Go through an inextricable cluster of nodes with a given integer value using deep search algorithm with using of stack.
     *
     * @param nodes    - array of nodes, Node[x][y]
     * @param currentX - row's position index in Node[][]
     * @param currentY - column's position index in Node[][]
     * @return integer - amount of visited nodes (all nodes from branch)
     */
    private static int goOnBranch(Node[][] nodes, int currentX, int currentY) {
        int square = 0;
        stack.push(nodes[currentX][currentY]);
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            int x = current.getX();
            int y = current.getY();
            square++;

            if (x + 1 < nodes.length) {
                pushNode(nodes, x + 1, y);
            }
            if (y + 1 < nodes[x].length) {
                pushNode(nodes, x, y + 1);
            }
            if (x - 1 > 0) {
                pushNode(nodes, x - 1, y);
            }
            if (y - 1 > 0) {
                pushNode(nodes, x, y - 1);
            }
        }
        return square;
    }

    /**
     * If nodes[x][y] correspond to the given value and is not visited, - add it to stack, then mark them as visited.
     */
    private static void pushNode(Node[][] nodes, int x, int y) {
        if (nodes[x][y].getValue() == COMPARE_VALUE && !nodes[x][y].isVisited()) {
            stack.push(nodes[x][y]);
            nodes[x][y].setVisited(true);
        }
    }

    /**
     * Returns the number of elements from List <Integer> whose value must be greater than the value of the MAX element
     * multiply the threshold
     */
    private static int filter(MyList<Integer> branches) {
        int amount = 0;
        int max = 0;
        for (Integer el:branches) {
            max = Math.max(max,el);
        }
        int thresholdSquare = (int) (max * THRESHOLD_SQUARE);

        for (Integer value : branches) {
            if (value > thresholdSquare)
                amount++;
        }

        return amount;
    }
}

