package com.shpp.p2p.cs.ilitvinov.assignment2;

import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/*This program is to create optical illusion
 *           with black points*/

public class Assignment2Part5 extends WindowProgram {

    /* The number of rows and columns in the grid, respectively. */
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 6;

    /* The width and height of each box. */
    private static final double BOX_SIZE = 80;

    /* The horizontal and vertical spacing between the boxes. */
    private static final double BOX_SPACING = 10;

    /*Box color :)*/
    private static final Color BOX_COLOR = new Color(0, 0, 0);

    /*The size of window*/
    @SuppressWarnings("unused")
    public static final int APPLICATION_WIDTH = (int) (NUM_COLS * BOX_SIZE + NUM_COLS * BOX_SPACING) + 40;
    @SuppressWarnings("unused")
    public static final int APPLICATION_HEIGHT = (int) (NUM_COLS * BOX_SIZE + NUM_COLS * BOX_SPACING) + 40;

    public void run() {

        drawIllusion(NUM_ROWS, NUM_COLS, BOX_SIZE, BOX_SPACING, BOX_COLOR);
    }

    @SuppressWarnings("SameParameterValue")
    private void drawIllusion(int num_rows, int num_cols, double boxSize, double spaceSize, Color boxColor) {

        double x = 50, y = 0;

        for (int i = 0; i < num_cols; i++) {

            //Initialization of draw staring position
            if (i == 0) {
                x = 15;
            } else {
                x += spaceSize + boxSize;
            }

            //Drawing :)
            drawBoxRow(x, y, num_rows, boxSize, spaceSize, boxColor);
        }

    }

    private void drawBoxRow(double x, double y, int num_rows, double boxSize, double spaceSize, Color boxColor) {
        for (int j = 0; j < num_rows; j++) {
            drawBox(x, y, boxSize, boxSize, boxColor);
            y += spaceSize + boxSize;
        }
    }

    private void drawBox(double x, double y, double width, double height, Color color) {
        GRect section = new GRect(x, y, width, height);
        section.setColor(color);
        section.setFillColor(color);
        section.setFilled(true);
        add(section);
    }
}