package com.shpp.p2p.cs.ilitvinov.assignment2;

import acm.graphics.GOval;
import com.shpp.cs.a.graphics.WindowProgram;
import java.awt.*;

/**Caterpillar*/

public class Assignment2Part6 extends WindowProgram {

    /* The number of caterpillar's sections and their diameter.*/
    private static final int SECTION_NUM = 8;
    private static final double SECTION_DIAMETER = 300;

    /*Section primary and outline colors*/
    private static final Color COLOR_PRIMARY = new Color(0,0,0);
    private static final Color COLOR_OUTLINE = new Color(33,174,83);

    /*The size of window*/
    @SuppressWarnings("unused")
    public static final int APPLICATION_WIDTH =(int) (SECTION_NUM*SECTION_DIAMETER*0.6);
    @SuppressWarnings("unused")
    public static final int APPLICATION_HEIGHT = (int) (SECTION_DIAMETER*1.7);

    public void run() {

            drawCaterpillar(SECTION_DIAMETER, SECTION_NUM, COLOR_PRIMARY, COLOR_OUTLINE );
    }

    /*Draw whole caterpillar*/
    @SuppressWarnings("SameParameterValue")

    private void drawCaterpillar(double diameter, int sectionNum, Color colorMajor, Color colorOutline){

        /*Initializing starting coordinates to draw Caterpillar*/
        double x = 0;
        double y = 0.5*diameter;

        for(int i = 0; i < sectionNum; i++){

            drawSection(x, y, diameter, colorMajor, colorOutline);

            /*set axial displacement*/
            x += diameter/2;
            if(i%2==0){
                y -= diameter/2;
            }else {
                y += diameter/2;
            }
        }
    }

    /*Draw one section(filled circle) with colored outline*/
    private void drawSection(double x,double y, double diameter, Color colorMajor, Color colorOutline){

        GOval section = new GOval(x, y, diameter, diameter);
        section.setFilled(true);
        section.setFillColor(colorMajor);
        section.setColor(colorOutline);
        add(section);
    }
}