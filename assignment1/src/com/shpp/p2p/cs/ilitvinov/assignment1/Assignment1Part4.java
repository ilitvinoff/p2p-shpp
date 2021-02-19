package com.shpp.p2p.cs.ilitvinov.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part4 extends KarelTheRobot
{
    public void run() throws Exception
    {
        //Check if the width of the world is 1, and fill the row only in height
        if(frontIsBlocked())
        {
            //Checking if the world has 1x1 size
            if(leftIsBlocked())
            {
                putBeeper();
            }
            else
            {
                turnLeft();
                fillChessRow();
            }
        }
        else
        {
            //The cycle conditions check if the street is not extreme in the world,
            //if not, the fill the world in chessboard pattern up to extreme street
            while ((facingWest() && rightIsClear()) || (facingEast() && leftIsClear()))
            {
                fillChessRow();
                goNextRow();
            }
            //filling the extreme street in the world
            fillChessRow();
        }
    }
    //With this method, Karel rotates 180 degrees
    private void turnAround() throws Exception
    {
        turnLeft();
        turnLeft();
    }
    //This method is presented to fill a row in a checkerboard pattern
    private void fillChessRow() throws Exception
    {
        while (frontIsClear())
        {
            if(noBeepersPresent())
            {
                putBeeper();
            }
            if(frontIsClear())
            {
                move();
            }
            if(frontIsClear())
            {
                move();
                //this condition checking if after the 2nd movement is the end of the world
                //then put beeper
                if(frontIsBlocked())
                {
                    putBeeper();
                }
            }
        }
    }
    //This method is for turning Karel to North direction
    private void watchToNorth() throws Exception
    {
        while(notFacingNorth())
        {
            turnLeft();
        }
    }
    //This method is for turning Karel to East direction
    private void watchToEast() throws Exception
    {
        while(notFacingEast())
        {
            turnLeft();
        }
    }
    //This method is for turning Karel to West direction
    private void watchToWest() throws Exception
    {
        while(notFacingWest())
        {
            turnLeft();
        }
    }
    //This method is to move the Karel, after he filled the row,
    //to the next street in the right starting position to fill this "next street"
    private void goNextRow() throws Exception
    {
        if(beepersPresent() && facingWest())
        {
            turnAround();
            move();
            watchToNorth();
            move();
            watchToEast();
        }
        else
        {
            if(beepersPresent() && facingEast())
            {
                turnAround();
                move();
                watchToNorth();
                move();
                watchToWest();
            }
            else
            {
                if(noBeepersPresent() && facingWest())
                {
                    watchToNorth();
                    move();
                    watchToEast();
                }
                else
                {
                    if(noBeepersPresent() && facingEast())
                    {
                        watchToNorth();
                        move();
                        watchToWest();
                    }
                }
            }
        }
    }
}