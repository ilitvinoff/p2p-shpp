package com.shpp.p2p.cs.ilitvinov.assignment2;

import acm.graphics.GOval;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/* This program drawing paw print in static size window with static size of paw print
   and with static arrangement of this print in our static sized window :)
 */

public class Assignment2Part3 extends WindowProgram {
    /* Constants controlling the relative positions of the
     * three toes to the upper-left corner of the pawprint.
     *
     * (Yes, I know that actual pawprints have four toes.
     * Just pretend it's a cartoon animal. ^_^)
     */
    private static final double FIRST_TOE_OFFSET_X = 0;
    private static final double FIRST_TOE_OFFSET_Y = 20;
    private static final double SECOND_TOE_OFFSET_X = 30;
    private static final double SECOND_TOE_OFFSET_Y = 0;
    private static final double THIRD_TOE_OFFSET_X = 60;
    private static final double THIRD_TOE_OFFSET_Y = 20;

    /* The position of the heel relative to the upper-left
     * corner of the pawprint.
     */
    private static final double HEEL_OFFSET_X = 20;
    private static final double HEEL_OFFSET_Y = 40;

    /* Each toe is an oval with this width and height. */
    private static final double TOE_WIDTH = 20;
    private static final double TOE_HEIGHT = 30;

    /* The heel is an oval with this width and height. */
    private static final double HEEL_WIDTH = 40;
    private static final double HEEL_HEIGHT = 60;

    /* The default width and height of the window. These constants will tell Java to
     * create a window whose size is *approximately* given by these dimensions. You should
     * not directly use these constants in your program; instead, use getWidth() and
     * getHeight(), which return the *exact* width and height of the window.
     */
    @SuppressWarnings("unused")
    public static final int APPLICATION_WIDTH = 270;
    @SuppressWarnings("unused")
    public static final int APPLICATION_HEIGHT = 220;

    public void run() {
        drawPawprint(20, 20);
        drawPawprint(180, 70);
    }

    /**
     * Draws a pawprint. The parameters specify the upper-left corner of the
     * bounding box containing that pawprint.
     *
     * @param x The x coordinate of the upper-left corner of the bounding box for the pawprint.
     * @param y The y coordinate of the upper-left corner of the bounding box for the pawprint.
     */
    private void drawPawprint(double x, double y) {

        for (int i = 1; i < 5; i++) {

            GOval oval = drawCircle(0, 0, TOE_WIDTH, TOE_HEIGHT, Color.black);
            /*Here we calculating coordinates and setting them to the object "oval"*/
            switch (i) {
                case (1): {
                    oval.setLocation(FIRST_TOE_OFFSET_X+x, FIRST_TOE_OFFSET_Y+y);
                    break; }
                case (2): {
                    oval.setLocation(SECOND_TOE_OFFSET_X+x, SECOND_TOE_OFFSET_Y+y);
                    break; }
                case (3): {
                    oval.setLocation(THIRD_TOE_OFFSET_X+x, THIRD_TOE_OFFSET_Y+y);
                    break; }
                case (4): {
                    oval.setBounds(HEEL_OFFSET_X+x, HEEL_OFFSET_Y+y, HEEL_WIDTH,HEEL_HEIGHT);
                    break; }
                default:break;
            }
            add(oval);
        }
    }

    private GOval drawCircle(double x, double y, double width, double height, Color color){
        GOval oval = new GOval(x, y, width, height);
        oval.setFillColor(color);
        oval.setFilled(true);
        return (oval);
    }
}