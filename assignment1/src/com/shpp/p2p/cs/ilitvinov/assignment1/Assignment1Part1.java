package com.shpp.p2p.cs.ilitvinov.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part1 extends KarelTheRobot
{
    public void run() throws Exception
    {
        //moving for the newspaper
        goToNewsPaper();
        //Picking newspaper up
        pickBeeper();
        //moving back to starting position
        goBack();
    }
    //Define method to turn right
    private void turnRight() throws Exception
    {
        turnLeft();
        turnLeft();
        turnLeft();
    }
    //Define method to turn around
    private void turnAround() throws Exception
    {
        turnLeft();
        turnLeft();
    }
    //Define method which alows Karel move while cell is empty, when beeper is in the cell, Karel stops
    private void goToBeeper() throws Exception
    {
        while(noBeepersPresent())
        {
            move();
        }
    }
    //Define method which alows Karel move while front is clear, when any barier is in the cell, Karel stops
    private void goToWall() throws Exception
    {
        while(frontIsClear())
        {
            move();
        }
    }
    //Define method which alows Karel move while front is clear, when any barier is in the cell, Karel stops
    private void goLeftNearWall() throws Exception
    {
        while(leftIsBlocked())
        {
            move();
        }
    }
    //moving for the newspaper
    private void goToNewsPaper() throws Exception
    {
        goToWall();
        turnRight();
        goLeftNearWall();
        turnLeft();
        goToBeeper();
    }
    //moving back to starting position
    private void goBack() throws Exception
    {
        turnAround();
        goToWall();
        turnRight();
        move();
        turnRight();
    }
}