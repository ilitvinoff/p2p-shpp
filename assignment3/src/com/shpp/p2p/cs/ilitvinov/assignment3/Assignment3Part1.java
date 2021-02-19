package com.shpp.p2p.cs.ilitvinov.assignment3;

import com.shpp.cs.a.console.TextProgram;

import java.util.Scanner;

/**
 * Program calculates if it enough time per one day of your training,
 * drawing, learning, etc... the result depends on values you'll enter.
 * In this case we checking is it enough trainings for
 * good cardiovascular health and blood pressure
 */


public class Assignment3Part1 extends TextProgram {

    final static String TYPE_1st_TITLE = "Cardiovascular health:";
    final static int ENOUGH_MINUTES_1st_TYPE_TRAINING = 30;
    final static int ENOUGH_1st_TYPE_DAYS_AMOUNT = 5;

    final static String TYPE_2nd_TITLE = " Blood pressure:";
    final static int ENOUGH_MINUTES_2nd_TYPE_TRAINING = 40;
    final static int ENOUGH_2nd_TYPE_DAYS_AMOUNT = 3;

    final static String QUESTION_TITLE = "How many minutes did you do on day ";

    final static String NOT_ENOUGH_TITLE = "You needed to train hard for at least";
    final static String NOT_ENOUGH_END_OF_SENTENCE_STRING = " more day(s) a week!";
    final static String ENOUGH_TITLE = "Great job! You've done enough exercise for cardiovascular health.";

    final static int TRAINING_WEEK_LENGTH = 7;

    public void run() {

        int[] resPrintArray = resPrintInArray();

        printResult(TYPE_1st_TITLE, amountWellDoneDays(resPrintArray, ENOUGH_MINUTES_1st_TYPE_TRAINING), ENOUGH_1st_TYPE_DAYS_AMOUNT);
        printResult(TYPE_2nd_TITLE, amountWellDoneDays(resPrintArray, ENOUGH_MINUTES_2nd_TYPE_TRAINING), ENOUGH_2nd_TYPE_DAYS_AMOUNT);
    }

    /*Check's: "is it enough training per day?"
     * Returns the amount of days, when it was enough training*/
    private int amountWellDoneDays(int[] resPrintInArray, int enoughMinutes) {
        int positiveDaysAmount = 0;

        for (int value : resPrintInArray) {
            if (value >= enoughMinutes) {
                positiveDaysAmount += 1;
            }
        }

        return positiveDaysAmount;
    }

    /*Fill the array with positive integers
     * @return array of positive integers*/
    private int[] resPrintInArray() {

        int[] resPrintInArray = new int[TRAINING_WEEK_LENGTH];
        for (int i = 0; i < resPrintInArray.length; i++) {
            Boolean elementIsEmpty = true;
            while (elementIsEmpty) {

                Scanner scanner = new Scanner(System.in);
                System.out.print(QUESTION_TITLE + (i + 1) + "? :");
                try {

                    resPrintInArray[i] = scanner.nextInt();
                } catch (Exception exception) {

                    System.out.println("\nPls use positive integers\n");
                    continue;
                }

                if (resPrintInArray[i] < 0) {
                    System.out.println("\nPls use only positive integers\n");
                    continue;
                }
                elementIsEmpty = false;
            }
        }
        return resPrintInArray;
    }

    /*Printout the result*/
    private void printResult(String title, int daysAmount, int enoughDaysAmount) {

        println((daysAmount < enoughDaysAmount) ?
                title + "\n\t" + NOT_ENOUGH_TITLE + (enoughDaysAmount - daysAmount) + NOT_ENOUGH_END_OF_SENTENCE_STRING
                : title + "\n\t" + ENOUGH_TITLE);
    }
}