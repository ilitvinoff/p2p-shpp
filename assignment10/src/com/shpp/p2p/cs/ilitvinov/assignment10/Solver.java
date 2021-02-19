package com.shpp.p2p.cs.ilitvinov.assignment10;

import java.util.*;

/**This class take list of tokens, put variables's values to this list and solve expression.*/

public class Solver {
    /**tokens and VARIABLES store tokenized expression to solve it. */
    private static ArrayList<Token> tokens;
    private static LinkedHashMap<String, Token> VARIABLES = null;

    /**Constructor duplicate list of tokens and map of variables from Class Lexer. */
    public Solver() {
        tokens = new ArrayList<>(Lexer.getTokens());
        VARIABLES = new LinkedHashMap<>(Lexer.getVariables());
    }

    public List<Token> getTokens() {
        return tokens;
    }

    /**Set variables's values to tokens's list*/
    public static void variablesToTokens() {
        for (String name : VARIABLES.keySet()) {
            for (Token token : tokens) {
                if (name.equals(token.getName())) {
                    token.setValue(VARIABLES.get(name).getValue());
                }
            }
        }
        StringBuilder expressionValidation = new StringBuilder();
        for (Token token : tokens) {
            if (token.getType() != TokenType.VARIABLE && token.getType() != TokenType.CONSTANT) {
                expressionValidation.append(token.getName());
                continue;
            }
            expressionValidation.append(token.valueToString());
        }
        ExpressionValidation validation = new ExpressionValidation(expressionValidation.toString());
        System.out.println(expressionValidation.toString());
        if (validation.zeroDivision()) {
            throw new RuntimeException("ololo");
        }
    }

    /**Ask user to input variables's values*/
    public static void setVarValues() {

        if (VARIABLES != null)
            for (String name : VARIABLES.keySet()) {
                VARIABLES.get(name).setValue(setVar(name, "Enter variable's value, pls \n%s: "));
            }
    }

    /**Ask to input variable's value.
     * @param name - variable's name
     * @param title - question to ask user to enter variable value.
     * @return entered value*/
    private static double setVar(String name, String title) {
        Scanner scanner = new Scanner(System.in);
        double value = 0;
        System.out.printf(title, name);
        try {
            value = scanner.nextDouble();
        } catch (Exception e) {
            setVar(name, "Pls use decimals or integers only\n%s: ");
        }
        return value;
    }

    public void eval() {
        toPower();
        divisionAndMultiply();
        plusMinus();
    }

    private void toPower() {
        for (int i = 0; i < tokens.size(); i++) {

            if (tokens.get(i).getType() == TokenType.POWER) {
                if (tokens.get(i - 1) != null && tokens.get(i + 1) != null) {
                    tokens.get(i - 1).setValue(Math.pow(tokens.get(i - 1).getValue(), tokens.get(i + 1).getValue()));
                    tokens.remove(i);
                    tokens.remove(i);
                    i -= 2;
                } else {
                    System.out.println("Wrong spelling of expression");
                }
            }
        }
    }

    private void divisionAndMultiply() {
        for (int i = 0; i < tokens.size(); i++) {

            if (tokens.get(i).getType() == TokenType.MULTIPLY) {
                if (tokens.get(i - 1) != null && tokens.get(i + 1) != null) {
                    tokens.get(i - 1).setValue((tokens.get(i - 1).getValue()) * (tokens.get(i + 1).getValue()));
                    tokens.remove(i);
                    tokens.remove(i);
                    i -= 2;
                } else {
                    System.out.println("Wrong spelling of expression");
                    throw new RuntimeException();
                }

            } else if (tokens.get(i).getType() == TokenType.DIVISION) {
                if (tokens.get(i - 1) != null && tokens.get(i + 1) != null) {
                    tokens.get(i - 1).setValue((tokens.get(i - 1).getValue()) / (tokens.get(i + 1).getValue()));
                    tokens.remove(i);
                    tokens.remove(i);
                    i -= 2;
                } else {
                    System.out.println("Wrong spelling of expression");
                    throw new RuntimeException();
                }
            }
        }
    }

    private void plusMinus() {
        for (int i = 0; i < tokens.size(); i++) {

            if (tokens.get(i).getType() == TokenType.PLUS) {
                if (tokens.get(i - 1) != null && tokens.get(i + 1) != null) {
                    tokens.get(i - 1).setValue((tokens.get(i - 1).getValue()) + (tokens.get(i + 1).getValue()));
                    tokens.remove(i);
                    tokens.remove(i);
                    i -= 2;
                } else {
                    System.out.println("Wrong spelling of expression");
                    throw new RuntimeException();
                }

            } else if (tokens.get(i).getType() == TokenType.MINUS) {
                if (tokens.get(i - 1) != null && tokens.get(i + 1) != null) {
                    tokens.get(i - 1).setValue((tokens.get(i - 1).getValue()) - (tokens.get(i + 1).getValue()));
                    tokens.remove(i);
                    tokens.remove(i);
                    i -= 2;
                } else {
                    System.out.println("Wrong spelling of expression");
                    throw new RuntimeException();
                }
            }
        }
    }
}
