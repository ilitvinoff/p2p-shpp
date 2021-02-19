package com.shpp.p2p.cs.ilitvinov.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part2 extends KarelTheRobot
{
    public void run() throws Exception
    {
        //Check if only 1 street from west to east. If so, fill this street
        if(leftIsBlocked())
        {
            watchForEast();
            while (frontIsClear())
            {
                putBeeper();
                goNextRow();
            }

        }
        if(frontIsClear())
        {
            //Fills all the streets we need with beepers, except the last street
            while (frontIsClear())
            {
                watchForNorth();
                fillTheRow();
                turnAround();
                goToWall();
                watchForEast();
                goNextRow();
            }
        }
        //Filling the last row with beepers
        if(frontIsBlocked())
        {
            watchForNorth();
            fillTheRow();
        }
    }
    //With this method, Karel rotates 180 degrees
    private void turnAround() throws Exception
    {
        turnLeft();
        turnLeft();
    }
    //With this method Karel move until the barrier in front
    private void goToWall() throws Exception
    {
        while(frontIsClear())
        {
            move();
        }
    }
    //This method allows Karel to fill cell with beeper(if it's empty) and move until the end of the row
    private void fillTheRow() throws Exception
    {
        while(frontIsClear())
        {
            if(noBeepersPresent())
            {
                putBeeper();
            }
            while(!noBeepersPresent() && frontIsClear())
            {
                move();
            }
            if(noBeepersPresent())
            {
                putBeeper();
            }
        }
    }
    //This method moving Karel across 3 streets, at the 4th he'll stop. If any barrier will appear earlier, Karel will
    //stop in front of it.
    private void goNextRow() throws Exception
    {
        for(int i=0; i<4; i++)
        {
            if(frontIsClear())
            {
                move();
            }
        }
    }
    //This method turns Karel's face to east
    private void watchForEast() throws Exception
    {
        while(notFacingEast())
        {
            turnLeft();
        }
    }
    //This method turns Karel's face to north
    private void watchForNorth() throws Exception
    {
        while(notFacingNorth())
        {
            turnLeft();
        }
    }
}