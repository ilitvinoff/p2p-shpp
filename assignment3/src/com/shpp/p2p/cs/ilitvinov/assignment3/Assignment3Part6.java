package com.shpp.p2p.cs.ilitvinov.assignment3;

import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * Tried to emulate The Galton Board
 * https://www.curiositybox.com/store/galton-board
 */

public class Assignment3Part6 extends WindowProgram {

    /*Set the size of the window*/
    public static final int APPLICATION_WIDTH = 300;
    public static final int APPLICATION_HEIGHT = 500;

    /*Set the size of the workspace area*/
    public static final double WORKSPACE_X0 = 0;
    public static final double WORKSPACE_Y0 = 0;
    public static final double WORKSPACE_WIDTH = APPLICATION_WIDTH * 0.9;
    public static final double WORKSPACE_HEIGHT = APPLICATION_HEIGHT * 0.9;

    /* The amount of time to pause between frames (125fps). */
    private static final double PAUSE_TIME = 1000.0 / 125;

    /*Time to stop animation*/
    private static final double STOP_TIME = 5000.0;

    /*Value for approximate calculations*/
    private static final double CALC_OFFSET = 0.1;

    /*Offset for coordinates*/
    private static final double GRAVITY = 0.325;

    /*Ball parameters*/
    private static final double BALL_DIAMETER = 15;
    private static final double BALL_DX = 0.7;
    private static final double BALL_DY = 0.7;
    private static final Color BALL_FILL_COLOR = new Color(50, 65, 138);

    /*Parameters of 1lvl of supports*/
    private static final double SUPPORT_LINE_BETWEEN_WIDTH = 4 * BALL_DIAMETER;
    private static final double SUPPORT_LINE_X1 = (WORKSPACE_WIDTH - SUPPORT_LINE_BETWEEN_WIDTH) / 2D;
    private static final double SUPPORT_LINE_Y1 = WORKSPACE_HEIGHT / 4D;
    private static final double SUPPORT_LINE_X2 = SUPPORT_LINE_X1 + SUPPORT_LINE_BETWEEN_WIDTH;
    private static final double SUPPORT_LINE_Y2 = WORKSPACE_HEIGHT / 6D;

    /*Parameters of 2lvl of supports*/
    private static final double ROUND_SUPPORT_DIAMETER = 15;
    private static final int ROUND_SUPPORT_AMOUNT_IN_ROW = 7;
    private static final int ROUND_SUPPORT_ROWS_AMOUNT = 4;
    private static final double ROUND_SUPPORT_DX_COEFFICIENT = 2.5 * ROUND_SUPPORT_DIAMETER;
    private static final double ROUND_SUPPORT_DY_COEFFICIENT = 2.5 * ROUND_SUPPORT_DIAMETER;
    private static final double ROUND_SUPPORT_STARTING_POSITION_X = (WORKSPACE_WIDTH - ROUND_SUPPORT_DIAMETER * ROUND_SUPPORT_AMOUNT_IN_ROW
            - (ROUND_SUPPORT_AMOUNT_IN_ROW - 1) * (ROUND_SUPPORT_DX_COEFFICIENT - ROUND_SUPPORT_DIAMETER)) / 2D;
    private static final double ROUND_SUPPORT_STARTING_POSITION_Y = SUPPORT_LINE_Y1 + 2.5 * ROUND_SUPPORT_DIAMETER;
    private static final Color ROUND_SUPPORT_FILL_COLOR = new Color(253, 129, 91);
    private static final Color ROUND_SUPPORT_OUT_COLOR = Color.black;

    /*Parameters of 3lvl of supports*/
    private static final int VERTICAL_SUPPORT_AMOUNT_IN_ROW = ROUND_SUPPORT_AMOUNT_IN_ROW - 1;
    private static final double VERTICAL_SUPPORT_STARTING_POSITION_X = ROUND_SUPPORT_STARTING_POSITION_X + 0.5 * ROUND_SUPPORT_DX_COEFFICIENT;
    private static final double VERTICAL_SUPPORT_DX_COEFFICIENT = 2.5 * ROUND_SUPPORT_DIAMETER;
    private static final double VERTICAL_SUPPORT_STARTING_POSITION_Y = ROUND_SUPPORT_STARTING_POSITION_Y +
            (ROUND_SUPPORT_DIAMETER + ROUND_SUPPORT_DY_COEFFICIENT - ROUND_SUPPORT_DIAMETER) * ROUND_SUPPORT_ROWS_AMOUNT;
    private static final double VERTICAL_SUPPORT_WIDTH = ROUND_SUPPORT_DIAMETER;
    private static final double VERTICAL_SUPPORT_HEIGHT = WORKSPACE_HEIGHT - VERTICAL_SUPPORT_STARTING_POSITION_Y;
    private static final Color VERTICAL_SUPPORT_FILL_COLOR = new Color(253, 129, 91);

    /*Draw animation*/
    public void run() {

        setWorkSpace();

        double[][] supportLeft = setLineSupport(SUPPORT_LINE_X1, 0);
        double[][] supportRight = setLineSupport(SUPPORT_LINE_X2, WORKSPACE_WIDTH);
        double[][][] roundSupport = setRoundSupport();
        double[][] horizontalSupport = setVerticalSupport();

        double x, i = 0, time = 0;

        while (true) {

            x = i * BALL_DIAMETER;
            fallBall(x, 0, supportLeft, supportRight, roundSupport, horizontalSupport);

            println("time worked: " + time + "ms");
        }
    }

    /*Draw workspace area frame*/
    private void setWorkSpace() {
        GRect field = new GRect(WORKSPACE_X0, WORKSPACE_Y0, WORKSPACE_WIDTH, WORKSPACE_HEIGHT);
        field.setColor(Color.black);
        field.setFilled(true);
        field.setFillColor(Color.lightGray);
        add(field);
    }

    /*Set 1st lvl of supports
     * @return coordinates 2points(x,y)(x2,y2) for each line*/
    private double[][] setLineSupport(double x1, double x2) {
        double[][] coordinates = {{x1, SUPPORT_LINE_Y1}, {x2, SUPPORT_LINE_Y2}};
        GLine support = new GLine(x1, SUPPORT_LINE_Y1, x2, SUPPORT_LINE_Y2);
        add(support);
        return coordinates;
    }

    /*Set 2nd lvl of supports
     * @return coordinates of the center of each circle, with array*/
    private double[][][] setRoundSupport() {
        double[][][] coordinates = new double[ROUND_SUPPORT_ROWS_AMOUNT][ROUND_SUPPORT_AMOUNT_IN_ROW][2];
        double x = ROUND_SUPPORT_STARTING_POSITION_X;
        double y = ROUND_SUPPORT_STARTING_POSITION_Y;

        for (int i = 0; i < ROUND_SUPPORT_ROWS_AMOUNT; i++) {

            for (int j = 0; j < ROUND_SUPPORT_AMOUNT_IN_ROW; j++) {

                if (i % 2 != 0 && j == 0) {

                    coordinates[i][j] = null;
                    x += 0.5 * ROUND_SUPPORT_DX_COEFFICIENT;
                    continue;
                }

                coordinates[i][j] = drawCircle(x, y);
                x += ROUND_SUPPORT_DX_COEFFICIENT;
            }
            y += ROUND_SUPPORT_DY_COEFFICIENT;
            x = ROUND_SUPPORT_STARTING_POSITION_X;
        }
        return coordinates;
    }

    /*Draw circle
     * @param (double x, double y) coordinates of top left rectangle's corner, that bond the circle,
     * @param diameter - diameter of the circle
     * @param outlineColor - outlineColor's color
     * @param color - the fill's color
     * @param isFilled - true or false, to fill the circle
     * @return coordinates of the center of the circle*/
    public double[] drawCircle(double x, double y) {

        double[] coordinatesCenter = {x + ROUND_SUPPORT_DIAMETER / 2.D, y + ROUND_SUPPORT_DIAMETER / 2.D};

        GOval circle = new GOval(x, y, ROUND_SUPPORT_DIAMETER, ROUND_SUPPORT_DIAMETER);
        circle.setColor(ROUND_SUPPORT_OUT_COLOR);
        circle.setFilled(true);
        circle.setFillColor(ROUND_SUPPORT_FILL_COLOR);
        add(circle);

        return coordinatesCenter;
    }

    /*Set 3rd lvl of supports
     * @return coordinates of the top left corner of the rectangle(support), with array,
     * for each rectangle*/
    private double[][] setVerticalSupport() {

        double[][] coordinates = new double[VERTICAL_SUPPORT_AMOUNT_IN_ROW][2];
        double x = VERTICAL_SUPPORT_STARTING_POSITION_X;

        for (int i = 0; i < VERTICAL_SUPPORT_AMOUNT_IN_ROW; i++) {

            coordinates[i] = drawVerticalSupport(x);
            x += VERTICAL_SUPPORT_DX_COEFFICIENT;
        }

        return coordinates;
    }

    /*Draw one vertical support
     * @param double x,y - top left corner of rectangle(support)
     * @return array[x,y] - coordinates of top left corner of th rectangle*/
    private double[] drawVerticalSupport(double x) {
        double[] coordinates = {x, VERTICAL_SUPPORT_STARTING_POSITION_Y};
        GRect support = new GRect(x, VERTICAL_SUPPORT_STARTING_POSITION_Y, VERTICAL_SUPPORT_WIDTH, VERTICAL_SUPPORT_HEIGHT);
        support.setFilled(true);
        support.setFillColor(VERTICAL_SUPPORT_FILL_COLOR);
        support.setColor(Color.black);
        add(support);

        return (coordinates);
    }

    /*Emulate falling of the ball
     * @param double x,y - starting coordinates of the ball
     * @param double time - if the method has already been used in a loop,
     *        we can get to the method the information about the time of the previous work (number of ms)
     * @param double[][] lineSupportArrayLeft, double[][] lineSupportArrayRight - arrays with 1st's lvl
     *        supports coordinates
     * @param double[][][] roundSupportArray - array with 2nd's lvl of supports coordinates
     * @param double[][] verticalSupportArray - array with 3rd's lvl of supports coordinates
     * @return double time - amount of ms, while the ball was falling*/

    private void fallBall(double x, double y, double[][] lineSupportArrayLeft,
                            double[][] lineSupportArrayRight, double[][][] roundSupportArray,
                            double[][] verticalSupportArray) {

        double dx = BALL_DX;
        double dy = BALL_DY;

        GOval ball = drawBall(x, y);
        while (!ballBelowFloor(ball)) {
            ball.move(dx, dy);

            dy += GRAVITY;
            if (ball.getX() + BALL_DIAMETER > WORKSPACE_WIDTH || ball.getX() + BALL_DIAMETER < WORKSPACE_X0) {
                dx *= -BALL_DX;
                continue;
            }

            if ((ball.getY() + BALL_DIAMETER) < SUPPORT_LINE_Y1) {

                if (ball.getX() < WORKSPACE_WIDTH / 2D && touchLineSupport(ball, lineSupportArrayLeft)) {
                    dx *= BALL_DX + GRAVITY;
                    dy *= -BALL_DY;
                    continue;
                }
                if (ball.getX() > WORKSPACE_WIDTH / 2D && touchLineSupport(ball, lineSupportArrayRight)) {
                    if (dx > 0) {
                        dx *= -BALL_DX - GRAVITY;
                    } else {
                        dx *= BALL_DX + GRAVITY;
                    }
                    dy *= -BALL_DY;
                    continue;
                }
            }

            if (ball.getY() + BALL_DIAMETER > SUPPORT_LINE_Y1
                    && ball.getY() < SUPPORT_LINE_Y1 +
                    ROUND_SUPPORT_ROWS_AMOUNT * ROUND_SUPPORT_DY_COEFFICIENT * ROUND_SUPPORT_DIAMETER) {
                if (touchRoundSupport(ball, roundSupportArray) > 0) {
                    dx = dx * 1D + GRAVITY;
                    dy *= -BALL_DY;
                    continue;
                }
                if (touchRoundSupport(ball, roundSupportArray) < 0) {
                    dx = -BALL_DX * dx - GRAVITY;
                    dy *= -BALL_DY;
                    continue;
                }
            }
            if (ball.getY() + BALL_DIAMETER > VERTICAL_SUPPORT_STARTING_POSITION_Y) {
                if ((touchVerticalSupportHorizon(ball, verticalSupportArray))) {
                    dx *= BALL_DX;
                    dy *= -BALL_DY;
                }
            }
            if (ball.getY() + BALL_DIAMETER >= WORKSPACE_HEIGHT) {
                dy *= -BALL_DY;
                dx = 0;
            }
            pause(PAUSE_TIME);
        }
    }

    /*Draw ball*/
    private GOval drawBall(double x, double y) {
        GOval ball = new GOval(x, y, BALL_DIAMETER, BALL_DIAMETER);
        ball.setColor(Color.black);
        ball.setFilled(true);
        ball.setFillColor(BALL_FILL_COLOR);
        add(ball);

        return ball;
    }

    /*Checking if ball bounce the floor
     *@param GOval ball - object of class GOval
     *@return true or false(bounced or not)*/
    private boolean ballBelowFloor(GOval ball) {
        return ball.getY() + (BALL_DIAMETER + BALL_DIAMETER * GRAVITY) >= WORKSPACE_HEIGHT;
    }

    /*Checking if ball bounce 1st lvl of supports
     *@param GOval ball - object of class GOval
     *@param  double[][] lineSuppArray - coordinates 2points(x,y)(x2,y2) for each line
     *@return true or false(bounced or not)*/
    private boolean touchLineSupport(GOval ball, double[][] lineSuppArray) {
        double x1 = lineSuppArray[0][0];
        double x2 = lineSuppArray[1][0];
        double y1 = lineSuppArray[0][1];
        double y2 = lineSuppArray[1][1];

        double x3 = (ball.getX() + (BALL_DIAMETER) / 2D);
        double y3 = (ball.getY() + (BALL_DIAMETER) / 2D);

        boolean res = false;

        try {

            res = (x3 - x1) / (x2 - x1) - (y3 - y1) / (y2 - y1) < 2 * CALC_OFFSET
                    && (x3 - x1) / (x2 - x1) - (y3 - y1) / (y2 - y1) > -2 * CALC_OFFSET;
        } catch (Exception exception) {
            println(exception);
        }
        return res;
    }

    /*Checking if ball bounce 2nd lvl of supports
     *@param GOval ball - object of class GOval
     *@param  double[][][] roundSupport - coordinates of the center of each circle(support), with array
     *@return double (-1,0,1)
     * -1 - touched from left side of the support
     *  1 - touched from right side of the support
     *  0 - no touching*/
    private double touchRoundSupport(GOval ball, double[][][] roundSupport) {
        double x, y, indicator = 0;
        for (double[][] row : roundSupport) {
            for (double[] col : row) {

                if (col != null) {

                    x = col[0];
                    y = col[1];
                    double ballX = ball.getX() + BALL_DIAMETER / 2D;
                    double ballY = ball.getY() + BALL_DIAMETER / 2D;

                    /*Checking if circles touching
                     * (x-x1)^2+(y-y1)^2 - (r+r1)^2 = 0
                     * x,y - center of circle
                     * r - radius*/
                    if (Math.pow(ballX - x, 2) + Math.pow(ballY - y, 2)
                            - Math.pow(ROUND_SUPPORT_DIAMETER / 2D + BALL_DIAMETER / 2D, 2) < CALC_OFFSET) {

                        if (ball.getX() < x) {
                            indicator = -1;
                        } else {
                            indicator = 1;
                        }

                    }
                }
            }

        }
        return (indicator);
    }

    /*Checking if ball bounce 3rd lvl of supports
     *@param GOval ball - object of class GOval
     * @double[][] setHorizontalSupport - array of coordinates of left top rectangle's(support's) corner
     * @return true or false - touched or not...*/
    private boolean touchVerticalSupportHorizon(GOval ball, double[][] setHorizontalSupport) {
        double x1, y1, x2, y2;
        boolean result = false;
        for (double[] row : setHorizontalSupport) {
            x1 = row[0];
            y1 = row[1];
            x2 = x1 + VERTICAL_SUPPORT_WIDTH;
            y2 = y1;

            double x3 = ball.getX() + (BALL_DIAMETER + BALL_DIAMETER * GRAVITY) / 2D;
            double y3 = ball.getY() + (BALL_DIAMETER + BALL_DIAMETER * GRAVITY) / 2D;

            try {

                result = (x3 - x1) / (x2 - x1) - (y3 - y1) / (y2 - y1) < CALC_OFFSET
                        && (x3 - x1) / (x2 - x1) - (y3 - y1) / (y2 - y1) > -CALC_OFFSET;
            } catch (Exception exception) {
                println(exception);
            }
        }
        return result;
    }
}
