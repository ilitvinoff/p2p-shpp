package com.shpp.p2p.cs.ilitvinov.assignment3;

import com.shpp.cs.a.console.TextProgram;

import java.util.Scanner;

/**
 * Program calculates numbers-hailstones :).
 * User enter some positive integer - "n".
 * If "n" - even, then: n = n / 2;
 * If "n" - odd, then n = 3*n +1;
 * Repeat the process, while "n" not equals 1
 */

public class Assignment3Part2 extends TextProgram {

    private int number;

    public void run() {

        number = inputNumber();
        printOutResult(number);
    }

    /*Input the number and checking if user entered correct value,
     * it must positive and integer.
     * @return positive integer*/
    private int inputNumber() {
        int number = 0;

        System.out.println("Pls enter positive integer bigger than zero: ");

        while (number <= 0) {

            Scanner scanner = new Scanner(System.in);
            try {
                number = scanner.nextInt();

            } catch (Exception excptn) {
                number = 0;
                System.out.println("Be serious!!! Pls enter positive integer bigger than zero: ");
                continue;
            }
            if (number <= 0) {
                System.out.println("Really, use positive integers please!");
            }
        }
        return number;
    }

    /*Calculating and print out the result*/
    private void printOutResult(int number) {

        while (number != 1) {

            if (number % 2 == 0) {
                println("" + number + " is even so I take half: " + (number = number / 2));
            } else {
                println("" + number + " is odd so I make 3n + 1: " + (number = 3 * number + 1));
            }
        }
    }

}
