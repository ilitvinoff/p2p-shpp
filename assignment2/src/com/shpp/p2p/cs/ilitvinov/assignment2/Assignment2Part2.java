package com.shpp.p2p.cs.ilitvinov.assignment2;

import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;
import java.awt.*;

            /*This program is to create:
        Optical Illusion: "Illusory Contours"*/

public class Assignment2Part2 extends WindowProgram {

    /*The APPLICATION_WIDTH and APPLICATION_HEIGHT parameters
    are designed
    to simulate different display(window) orientations(horizontal or vertical)!*/
    @SuppressWarnings("unused")
    public static final int APPLICATION_WIDTH = 600;
    @SuppressWarnings("unused")
    public static final int APPLICATION_HEIGHT = 800;

    public void run() {

        /*Here we checking if display(window of application)
          horizontally or vertically oriented.*/
        if (getWidth() < getHeight()) {
            verticalDisplay();

        } else {
            horizontalDisplay();
        }
    }

    private void horizontalDisplay() {
        double appendix, diameter, lengthSquare;
        double x, y, sideSize;
      /*To center our optical illusion in the center of the screen,
        imagine the square that is equidistant
        from the shortest sides of the screen(app's window),
        so "appendix" is the distance between sides of screen and square.
        At the corners of this imaginary square we'll put circles*/
        appendix = (getWidth() - getHeight()) / 2.0;
        lengthSquare = getHeight();
        diameter = lengthSquare / 3;

        //Drawing circles
        for (int i = 1; i < 5; i++) {
            GOval circle = new GOval(0, 0, diameter, diameter);
            circle.setFillColor(Color.black);
            circle.setFilled(true);

            /*Here we calculating coordinates and setting them to the object "circle"*/
            switch (i) {
                case (1): {
                    x = appendix;
                    y = 0;
                    circle.setLocation(x, y);
                    break; }

                case (2): {
                    x =appendix + lengthSquare - diameter;
                    y = 0;
                    circle.setLocation(x, y);
                    break; }

                case (3): {
                    x = appendix;
                    y =  lengthSquare - diameter;
                    circle.setLocation(x, y);
                    break; }

                case (4): {
                    x = appendix + lengthSquare - diameter;
                    y =lengthSquare - diameter;
                    circle.setLocation(x,y );
                    break;
                }
                default:break;
            }
            add(circle);
        }

        /*Creating the square that has the color of background*/
        x = appendix + diameter/2;
        y = diameter/2;
        sideSize = lengthSquare - diameter;

        drawSquare(x, y, sideSize, sideSize, getBackground());
    }

    private void verticalDisplay() {
        double appendix, diameter, lengthSquare;
        double x, y, sideSize;

        /*To center our optical illusion in the center of the screen,
        imagine the square that is equidistant
        from the shortest sides of the screen(app's window),
        so "appendix" is the distance between sides of screen and square's closest side.
        At the corners of this imaginary square we'll put circles*/
        appendix = (getHeight() - getWidth()) / 2.0;
        lengthSquare = getWidth();
        diameter = lengthSquare / 3;

        for (int i = 1; i < 5; i++) {
            GOval circle = new GOval(0, 0, diameter, diameter);
            circle.setFillColor(Color.black);
            circle.setFilled(true);

            /*Here we calculating coordinates and setting them to the object "circle"*/
            switch (i) {
                case (1): {
                    x = 0;
                    y = appendix;
                    circle.setLocation(x, y);
                    break; }

                case (2): {
                    x = lengthSquare - diameter;
                    y = appendix;
                    circle.setLocation(x, y);
                    break; }

                case (3): {
                    x = 0;
                    y = appendix + lengthSquare - diameter;
                    circle.setLocation(x, y);
                    break; }

                case (4): {
                    x = lengthSquare - diameter;
                    y = appendix + lengthSquare - diameter;
                    circle.setLocation(x, y);
                    break; }

                default:break;
            }
            add(circle);
        }

        //Creating the square that has the color of background
        x = diameter/2;
        y = appendix + diameter/2;
        sideSize = lengthSquare - diameter;

        drawSquare(x, y, sideSize, sideSize, getBackground());
    }
    private void drawSquare(double x, double y, double width, double height, Color color){
        GRect square = new GRect(x,y, width,height);
        square.setColor(color);
        square.setFillColor(color);
        square.setFilled(true);
        add(square);
    }
}