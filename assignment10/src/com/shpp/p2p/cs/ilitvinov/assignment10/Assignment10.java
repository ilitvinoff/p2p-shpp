package com.shpp.p2p.cs.ilitvinov.assignment10;

import java.util.Scanner;

/**
 * This program is designed to solve expressions that are written as a string of text.
 * It may consist of:
 * constant: any digit(decimals must be separated with '.');
 * variable: it must consist of characters(latin alphabet), underscores and numbers.
 * It must start with character.
 * operations:
 * + summation;
 * - subtraction;
 * * multiplication;
 * / division;
 * ^ raising to power;
 * <p>
 * Example: a+b-2*3/aa^a1+a_b;
 * You may pass this expression through command line, as parameter of this program,
 * before program start. Or u may input it latter, program will ask to do it if u didn't.
 */

public class Assignment10 {

    public static void main(String[] args) {
        boolean runProgram = true;

        while (runProgram) {
            if (args.length == 0) {
                String expression = inputExpression();
                runProgram = solveExpression(expression);
            } else if (args.length == 1) {
                solveExpression(args[0]);
                runProgram = false;
            } else {
                throw new RuntimeException("To many arguments!");
            }
        }
    }

    /**
     * Input expression from console
     *
     * @return expression, String type, entered by user
     */
    private static String inputExpression() {
        Scanner input = new Scanner(System.in);
        System.out.println("yo! input expression, pls");
        return input.nextLine();
    }

    /**
     * Out's the result of solved expression
     */
    private static void resultOut(Solver solverExpr) {
        solverExpr.eval();
        System.out.println("result = " + solverExpr.getTokens().get(0).getValue());
    }

    /**Ask if user want to do 1 more iteration, or to run program 1 more time.
     * @param titleQuestion - question
     * @param comparisonValue - value to compare with user's answer
     * @return true or false*/
    private static boolean triggerSwitcher(String titleQuestion,String comparisonValue) {
        Scanner input = new Scanner(System.in);
        System.out.println(titleQuestion);
        String decision = input.nextLine();
        return decision.equals(comparisonValue);
    }

    /**Solving our expression*/
    private static boolean solveExpression(String expression) {
        new Lexer(expression);

        Solver solverExpr = new Solver();
        Solver.setVarValues();
        Solver.variablesToTokens();

        resultOut(solverExpr);

        boolean moreIterations = true;
        while (moreIterations) {
            solverExpr.getTokens().clear();
            solverExpr = new Solver();

            if (triggerSwitcher("\n Do yuo want one more iteration with this expression?\n'y' - yes\n any other means  - no","y")) {
                Solver.setVarValues();
                Solver.variablesToTokens();
                resultOut(solverExpr);
            } else {
                moreIterations = false;
            }
        }

        return !triggerSwitcher("\n Do you want to exit the program?\n'y' - yes\n any other means  - no","y");
    }
}
