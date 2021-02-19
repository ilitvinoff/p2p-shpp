package com.shpp.p2p.cs.ilitvinov.assignment2;

import com.shpp.cs.a.console.TextProgram;

//quadratic equation solution

public class Assignment2Part1 extends TextProgram {

    public void run() {

        println("Calculator for quadratic equation solution" +
                "\nthe equation has the form:" +
                "\na * (x ^ 2) + b * x + c = 0\n");
        println(inputAndSolution());

    }

    private String inputAndSolution(){
        String result;
        double a=readDouble("Please enter a:");
        double b=readDouble("Please enter b:");

        //Roots calculating
        if (a == 0 && b == 0){
            result = "'a' and 'b' cant be equal to 0 at one time";
            return (result);
        }

        double c=readDouble("Please enter c:");
        //calculate discriminant
        double D = b*b - 4*a*c;

        if (a == 0){
            result = "There are one root: " + c*(-1) / b;
        }else if (D > 0){
            result = "There are two roots: "+ (-b + Math.sqrt(D)) / (2*a)
                    + " and " + (-b - Math.sqrt(D)) / (2*a);
        }else if (D == 0){
            result = "There are one root: "+ (-(b / 2*a));
        }else{
            result = "There are no real roots";
        }
    return (result);
    }
}
