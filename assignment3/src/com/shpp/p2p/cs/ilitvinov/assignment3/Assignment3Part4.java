package com.shpp.p2p.cs.ilitvinov.assignment3;

import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * Pyramid drawing at the center of canvas
 */

public class Assignment3Part4 extends WindowProgram {

    /*Constants for user interface. Sizes of bricks and their maximum amount */
    static final double BRICK_HEIGHT = 20.0;
    static final double BRICK_WIDTH = 40.0;
    static final int BRICKS_IN_BASE = 19;

    /*Constants for user interface. Color of outline,
     * if IS_FILLED = true, we got the opportunity to choose a fill color;
     * if IS_FILLED = false, there will be no fill */
    static final Color OUTLINE_COLOR = Color.green;
    static final Boolean IS_FILLED = true;
    static final Color FILL_COLOR = Color.black;

    public void run() {

        drawPyramid();
    }

    /*Pyramid drawing at the center of canvas*/
    private void drawPyramid() {

        /*Initialization starting coordinates of drawing */
        double x = (getWidth() + BRICKS_IN_BASE * BRICK_WIDTH - 2 * BRICK_WIDTH) / 2D;
        double y = getHeight() - BRICK_HEIGHT;

        for (int i = BRICKS_IN_BASE; i > 0; i--) {

            drawBricksRow(x, y, i);
            y -= BRICK_HEIGHT;
            x -= BRICK_WIDTH / 2D;
        }
    }

    /*Draw a row of bricks*/
    private void drawBricksRow(double x, double y, int brickInBase) {

        for (int j = brickInBase; j > 0; j--) {

            drawBrick(x, y);
            //offset of x-coordinate to draw the next brick
            x -= BRICK_WIDTH;
        }

    }

    /*Draw brick*/
    private void drawBrick(double x, double y) {

        GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        brick.setFilled(IS_FILLED);
        brick.setFillColor(FILL_COLOR);
        brick.setColor(OUTLINE_COLOR);
        add(brick);
    }
}