package com.shpp.p2p.cs.ilitvinov.assignment17.calc.postcalc;

import com.shpp.p2p.cs.ilitvinov.assignment17.calc.Testing;
import com.shpp.p2p.cs.ilitvinov.assignment17.collections.MyList;

import java.util.Scanner;

/**
 * This program is designed to solve expressions that are written as a string of text.
 * It may consist of:
 * constant: any digit(decimals must be separated with '.' or ',');
 * variable: it must consist of characters(latin alphabet), underscores and numbers,and
 * it must start with character.
 * operations:
 * + summation;
 * - subtraction;
 * * multiplication;
 * / division;
 * ^ raising to power;
 * cos(expression) - cosines
 * sin(expression) - sines
 * tan(expression) - tangent
 * atan(expression) - arctangent
 * log10(expression) - decimal logarithm
 * log2(expression) - binary logarithm
 * sqrt(expression) - square root
 * <p>
 * Example: sqrt(a+b-2*3/aa^a1+a_b+sin(a+b*log10(10))-cos(90)+tan(a)*atan(2)/log2(10));
 * You may pass this expression through command line, as parameter of this program,
 * before program start. Or u may input it latter, program will ask to do it if u didn't.
 */

public class PostCalc implements Testing {

    public void proceed(String[] args) throws Exception {

        boolean run = true;

        if (args.length < 2) { // when the argument includes only expression or nothing
            while (run) {

                String expression = (args.length == 1) ? args[0] : input("yo! input expression, pls");

                MyList<Token> tokens = Lexer.tokenize(expression);
                Parser parser = new Parser();
                Calculate result = new Calculate(parser.getNodes(tokens), parser.getVariables(tokens));

                boolean newIteration = true;
                while (newIteration) {
                    result.setVariables();
                    result.printOutResult(expression);
                    newIteration = recycle("\nDo u want 1 more iteration?\ny - 'yes'; any other - 'no'");
                }
                run = recycle("\nDo u want to enter new expression?\ny - 'yes'; any other - 'no'");
            }
        } else { // when the arguments includes expression and variables

            String expression = args[0];
            String[] variables = new String[(args.length - 1)];
            System.arraycopy(args, 1, variables, 0, (args.length - 1));

            MyList<Token> tokens = Lexer.tokenize(expression);
            Parser parser = new Parser();
            new Calculate(parser.getNodes(tokens), parser.getVariables(tokens), variables).calculate();
        }
    }

    private static String input(String title) {
        Scanner input = new Scanner(System.in);
        System.out.println(title);
        return input.nextLine();
    }

    private static boolean recycle(String title) {
        return input(title).equals("y");
    }
}
