package com.shpp.p2p.cs.ilitvinov.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part3 extends KarelTheRobot
{
    public void run() throws Exception
    {
        firstMarks();
        //Checking if the length of the street is not <= 2;
        if (frontIsBlocked())
        {
            turnAround();
            if(frontIsClear())
            {
                move();
                if(frontIsBlocked())
                {
                    pickBeeper();
                }
            }
            //If the street is longer than 2 cells
            if(frontIsClear())
            {
                turnAround();
                move();
                while (beepersPresent())
                {
                    pickPut();
                    moveToEnd();
                }
            }
        }
    }
    //With this method, Karel rotates 180 degrees
    private void turnAround() throws Exception
    {
        turnLeft();
        turnLeft();
    }
    //This method marking the 1st and the last cell in row with a beeper
    private void firstMarks() throws Exception
    {
        putBeeper();
            while(frontIsClear())
            {
                move();
            }
            if(noBeepersPresent())
            {
                putBeeper();
            }
    }
    /*Karel pick up the beeper from extreme cell with beeper and put it to nearest cell,
    so this nearest cell becomes extreme*/
    private void pickPut() throws Exception
    {
        turnAround();
        if(beepersPresent() && frontIsClear())
        {
            pickBeeper();
            move();
        }
        if(noBeepersPresent())
        {
            putBeeper();
        }
    }
    //This method moves Karel to another mark(beeper)
    private void moveToEnd() throws Exception
    {
        if (frontIsClear())
        {
            move();
        }
        while (frontIsClear() && noBeepersPresent())
        {
            move();
        }
    }
}