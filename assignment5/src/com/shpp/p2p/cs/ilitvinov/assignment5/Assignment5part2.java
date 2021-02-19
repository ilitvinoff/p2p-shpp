package com.shpp.p2p.cs.ilitvinov.assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * taking in to string, which are consist of numbers,
 * then converts them to numbers to get sum of it.
 * String's length may any u want...(only one limit - depends on memory of ur computer)
 */

public class Assignment5part2 extends TextProgram {
    public void run() {


        /* Sit in a loop, reading numbers and adding them. */
        while (true) {

            String n1 = readLine("Enter first number:  ");

            if (n1.equals("")) {
                System.out.println("bye bye");
                break;
            }

            String n2 = readLine("Enter second number: ");

            if (n2.equals("")) {
                System.out.println("bye bye");
                break;
            }

            println(n1 + " + " + n2 + " = " + addNumericStrings(n1, n2));
        }
    }

    /**
     * Given two string representations of nonnegative integers, adds the
     * numbers represented by those strings and returns the result.
     *
     * @param n1 The first number.
     * @param n2 The second number.
     * @return A String representation of n1 + n2
     */
    private String addNumericStrings(String n1, String n2) {

        int buffer = 0;
        int counter = 1;

        StringBuilder result = new StringBuilder();

        while (n1.length() - counter >= 0 && n2.length() - counter >= 0) {

            int elSum = n1.charAt(n1.length() - counter) + n2.charAt(n2.length() - counter) - '0' - '0' + buffer;

            buffer = checkSum(elSum);

            result.insert(0, elSum % 10);

            //if it is the end of any string and buffer has value, then put this value to result
            if (buffer > 0 && n1.length() - counter == 0 && n2.length() - counter == 0) {

                result.insert(0, buffer);
            }

            counter++;
        }

        if (n1.length() > n2.length()) {

            outOfRangeSum(n1, result, buffer, counter);
        }

        if (n1.length() < n2.length()) {

            outOfRangeSum(n2, result, buffer, counter);
        }

        return result.toString();
    }

    private void outOfRangeSum(String n, StringBuilder result, int buffer, int counter) {


        while (n.length() - counter >= 0) {
            int elSum = n.charAt(n.length() - counter) + buffer - '0';

            buffer = checkSum(elSum);

            if (elSum % 10 == 0 && buffer == 0) {
                result.insert(0, n.substring(0, counter));
                break;
            }

            result.insert(0, elSum % 10);

            if (n.length() - counter == 0 && buffer > 0) {
                result.insert(0, buffer);
            }
            counter++;
        }
    }

    private int checkSum(int elSum) {

        if (elSum > 9) {

            return 1;
        }
        return 0;
    }
}