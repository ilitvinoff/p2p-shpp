package com.shpp.p2p.cs.ilitvinov.assignment10;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionValidation {

    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("(^-?)(((\\d+(\\.\\d*)?)|([a-zA-z]+\\w*))[*/+\\-^])*((\\d+(\\.\\d*)?)|([a-zA-z]+\\w*))");
    private static final Pattern ZERO_DIVISION = Pattern.compile("(/0+(\\.0*)?)|(0+(\\.0*)?\\^-\\w)");
    private static final Pattern CONSTANT_PATTERN = Pattern.compile("^\\d*(\\.\\d*)?$");
    private static final Pattern VARIABLES_PATTERN = Pattern.compile("^[a-zA-z]*\\w*\\w");
    private static String expression;

    public ExpressionValidation(String expression) {
        ExpressionValidation.expression = expression;
    }

    public void setExpression(String expression) {
        ExpressionValidation.expression = expression;
    }

    public boolean validateExpression() {
        if (!expression.matches(EXPRESSION_PATTERN.toString())) {
            System.out.println("\nWrong form of expression!");
            return false;
        }
        return !this.zeroDivision();
    }

    public boolean zeroDivision() {
        Matcher zeroDivision = ZERO_DIVISION.matcher(expression);

        if (zeroDivision.find()) {
            System.out.println("\nZero division found");
            return true;
        }
        return false;
    }

    public boolean validateConstant(String operand) {
        if (!operand.matches(CONSTANT_PATTERN.toString())) {
            System.out.println("\nIncorrect form of constants entered. Use '.' as separator.");
            System.out.println("invalid constant = " + operand);
            return false;
        }
        return true;
    }

    public boolean validateVariable(String operand) {
        if (!operand.matches(VARIABLES_PATTERN.toString())) {
            System.out.println("\nIncorrect spelling of variables.");
            System.out.println("Invalid variable = " + operand);
            return false;
        }
        return true;
    }

}
