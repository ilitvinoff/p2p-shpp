package com.shpp.p2p.cs.ilitvinov.assignment2;

import acm.graphics.GLabel;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;
import java.awt.*;

            /*This program is to create:
                3-color flags*/

public class Assignment2Part4 extends WindowProgram {

    /*Enter the name of the Country to COUNTRY_NAME,
    * to see this name under the flag's image*/
    private static final String COUNTRY_NAME = "Zion";
    /*Our flag has 3 stripes. So choose their orientation using the ORIENTATION parameter.
    * There are only two possible values:
    * h - means horizontal,
    * v - means vertical*/
    private static final String ORIENTATION = "v";

    /*Here we choose the colors of our stripes.
    * COLOR1 - 1st stripe, COLOR2 - 2nd, COLOR3 - 3rd.
    * Counting from left to right, or from top to bottom,
    * it depends of ORIENTATION*/
    private static final Color COLOR1 = new Color(0, 0, 0);
    private static final Color COLOR2 = new Color(234, 234, 100);
    private static final Color COLOR3 = new Color(0, 0, 0);

    /*The APPLICATION_WIDTH and APPLICATION_HEIGHT parameters
    are designed to change size of the window with flag.
    Parameters values ​​must be at least 100!!!*/
    public static final int APPLICATION_WIDTH = 300;
    public static final int APPLICATION_HEIGHT = 300;

    /*The FLAG_WIDTH and FLAG_HEIGHT parameters
    are designed to change size of the flag.
    FLAG_WIDTH must be less or equal APPLICATION_WIDTH, but not less than 100!!!
    FLAG_HEIGHT must be less or equal 0.7*APPLICATION_HEIGHT, but not less than 90!!!*/
    public static final int FLAG_WIDTH = 300;
    public static final int FLAG_HEIGHT = 210;

    public void run() {
        drawFlag(APPLICATION_WIDTH, APPLICATION_HEIGHT, FLAG_WIDTH, FLAG_HEIGHT, COUNTRY_NAME, ORIENTATION,
                COLOR1, COLOR2, COLOR3);
        }

    private void drawFlag(int appWidth, int appHeight, double flagWidth, double flagHeight, String flagName, String orientation,
                          Color color1, Color color2, Color color3) {

        /*Here we checking for incorrect values...
         * If everything is ok, we'll see the flag*/
        if (flagWidth > appWidth) {
            setError("FLAG_WIDTH more then APPLICATION_WIDTH!!!", appHeight / 4.0);

        } else if (flagHeight > 0.7 * appWidth) {
                setError("FLAG_HEIGHT more then 0.7*APPLICATION_HEIGHT!!!", appHeight / 4.0);

            } else if (appWidth < 100) {
                setError("APPLICATION_WIDTH less then 100!!!", appHeight / 4.0);

            } else if (appHeight < 100) {
                setError("APPLICATION_HEIGHT less then 100!!!", appHeight / 4.0);

            } else if (appWidth < 0 || appHeight < 0 || flagWidth < 0 || flagHeight < 0) {
                setError("Use only positive numbers to set values !!!", appHeight / 4.0);

            } else {
                /*appendixWidth and appendixHeight will help us to center our image*/
                double appendixWidth = appWidth - flagWidth;
                double appendixHeight = appHeight - flagHeight;

                /*Initialization of our 1st point's coordinates, to start draw our flag.
                 * Drawing starting from top left corner of the flag. */
                double x = appendixWidth / 2;
                double y = appendixHeight / 2 - 15;
                double sectionWidth, sectionHeight;
                Color color = color1;

                /*Checking if ORIENTATION has correct value*/
                if (orientation.equals("v")) {

                    sectionWidth = flagWidth / 3;
                    sectionHeight = flagHeight;
                    appendixHeight = sectionHeight;
                    appendixWidth = 0;

                } else if (orientation.equals("h")) {

                    sectionWidth = flagWidth;
                    sectionHeight = flagHeight / 3;
                    appendixWidth = sectionWidth;
                    appendixHeight = 0;

                } else {

                    setError("ORIENTATION has only two possible values!!!" +
                            " h - horizontal" +
                            " v - vertical!!!", appHeight / 4);
                    return;
                }

                /*Here we change coordinates, to draw 2nd and 3rd stripe*/
                for (int i = 0; i < 3; i++) {
                    switch (i) {
                        case (1): {

                            color = color2;
                            x += sectionWidth - appendixWidth;
                            y += sectionHeight - appendixHeight;

                            break;
                        }
                        case (2): {

                            color = color3;
                            x += sectionWidth - appendixWidth;
                            y += sectionHeight - appendixHeight;

                            break;
                        }
                        default: {
                            break;
                        }
                    }

                    drawSection(x, y, sectionWidth, sectionHeight, color);
                }

                /*Initialization coordinates to draw "Flag of COUNTRY_NAME" */
                x = (appendixWidth + flagWidth) - flagName.length() * 8 - 76;
                y += sectionHeight + 22;

                drawFlagName(flagName, x, y);
            }
    }

    private void drawSection(double x, double y, double width, double height, Color color){
        GRect section = new GRect(x, y, width, height);
        section.setColor(color);
        section.setFillColor(color);
        section.setFilled(true);
        add(section);
    }

    private void drawFlagName(String flagName, double x, double y){
        GLabel name= new GLabel("Flag of " + flagName, x, y);
        name.setFont("Times New Roman-16");
        add(name);
    }

    private void setError(String str, double appHeight){
        GLabel error = new GLabel(str, 20,appHeight*0.4);
        error.setFont("Times New Roman Bold-24");
        error.setColor(Color.red);
        add(error);
    }
}