package com.shpp.p2p.cs.ilitvinov.assignment11;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**Static class to validate our expression*/
public class ExpressionValidation {

    private static final Pattern VALIDATION_EXPRESSION_PATTERN = Pattern.compile("[+*/^][+*/^]+|[`~!@#$%&|><=]");
    private static final Pattern ZERO_DIVISION = Pattern.compile("(((/-?0+(\\.0*)?)|(0+(\\.0*)?\\^-\\w))[-+*/^)])|((/-?0+(\\.0*)?)|(0+(\\.0*)?\\^-\\w))$");
    private static final Pattern CONSTANT_PATTERN = Pattern.compile("\\d+(\\.\\d+)?");
    private static final Pattern VARIABLES_PATTERN = Pattern.compile("[a-zA-Z]+\\w*");

    /**Pre-validation for common errors.
     * If an error is detected, an exception is thrown.
     * @param expression - String with mathematical expression*/
    public static void validateExpression(String expression) throws Exception {
        if (expression == null) {
            throw new Exception("Expression cant be null!");
        }
        Matcher doubleOperation = VALIDATION_EXPRESSION_PATTERN.matcher(expression);
        if (doubleOperation.find()) {
            throw new Exception("\nDoubled operation found: " + doubleOperation.group() + "\nCheck your expression!");
        }
        zeroDivision(expression);
    }

    /**Search for zero division.
     * If an error is detected, an exception is thrown.
     * @param expression - String with mathematical expression*/
    public static void zeroDivision(String expression) throws Exception {
        Matcher zeroDivision = ZERO_DIVISION.matcher(expression);
        if (zeroDivision.find()) {
            throw new Exception("\nZero division found");
        }
    }

    /**Check if operand matches constant pattern.
     * If an error is detected, an exception is thrown.
     * @param operand - String with number expression*/
    public static void validateConstant(String operand) throws Exception {
        if (!operand.matches(CONSTANT_PATTERN.toString())) {
            throw new Exception("invalid constant = " + operand);
        }
    }

    /**Check if operand matches variable pattern.
     * If an error is detected, an exception is thrown.
     * @param operand - String with number expression*/
    public static void validateVariable(String operand) throws Exception {
        if (!operand.matches(VARIABLES_PATTERN.toString())) {
            throw new Exception("Invalid variable = " + operand);
        }
    }

    /**Check if any variable from map is not initialized.
     * If an error is detected, an exception is thrown.
     * @param variables - LinkedHashMap<String, Double>
     *     where: String - variable's name
     *            Double - variable's value*/
    public static void validateInitializationVariables(LinkedHashMap<String, Double> variables) throws Exception {
        StringBuilder notInitialized = new StringBuilder();
        for (String key : variables.keySet()) {
            if (variables.get(key) == null) {
                notInitialized.append("key, ");
            }
        }
        if (!(notInitialized.length() == 0)) {
            throw new Exception(String.format("Variables: %s - not initialized", notInitialized.toString()));
        }
    }

}
