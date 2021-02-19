package com.shpp.p2p.cs.ilitvinov.assignment10;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is to create list of tokens. String 'expression' is split
 * to lexemes(tokens). Token can be: operation, constant or variable.
 */

public class Lexer {

    /**
     * Patterns for searching constants and variables in a string
     */
    private static final Pattern CONSTANT_PATTERN = Pattern.compile("\\d*(\\.\\d*)?");
    private static final Pattern VARIABLES_PATTERN = Pattern.compile("[a-zA-z]*\\w*\\w");

    /**
     * Operations and token's types. The operation index in the string corresponds to the type index in the array
     */
    private static final String OPERATIONS = "+-/*^";
    private static final TokenType[] OPERATIONS_TOKEN_TYPE = {
            TokenType.PLUS, TokenType.MINUS,
            TokenType.DIVISION, TokenType.MULTIPLY,
            TokenType.POWER};

    /**
     * expression - stores our expression as string.
     * TOKENS - saves the tokenized expression.
     * VARIABLES - is to store our variables(for user friendly interface)
     */
    private static String expression;
    private static final List<Token> TOKENS = new ArrayList<>();
    private static final LinkedHashMap<String, Token> VARIABLES = new LinkedHashMap<>();

    public Lexer(String expression) {
        Lexer.expression = expression;
        TOKENS.clear();
        VARIABLES.clear();
        tokenize();
    }

    public static List<Token> getTokens() {
        return TOKENS;
    }

    public static LinkedHashMap<String, Token> getVariables() {
        return VARIABLES;
    }

    /**
     * Is to split our expression to tokens.
     */
    private void tokenize() {
        int index = 0;
        ExpressionValidation validation = new ExpressionValidation(expression);

        if (!validation.validateExpression()) {
            throw new RuntimeException("");
        }
        while (index < expression.length()) {

            if (Character.isDigit(expression.charAt(index))) {
                String searchIn = expression.substring(index);
                index += tokenizeNumber(searchIn);

            } else if (Character.isLetter(expression.charAt(index))) {
                String searchIn = expression.substring(index);
                index += tokenizeVariable(searchIn);

            } else if (OPERATIONS.indexOf(expression.charAt(index)) != -1) {
                if (index == 0 && expression.charAt(index) == '-') {
                    tokenizeNumber("0");
                    tokenizeOperation('-');
                    index++;
                } else {
                    tokenizeOperation(expression.charAt(index));
                    index++;
                }

            }
        }
    }

    /**
     * Is to find and save first digit from a string as token.
     *
     * @param searchIn - string where to search
     * @ returns the length of the found digit (number of characters), if found,
     * and -1, - if not found
     */
    private int tokenizeNumber(String searchIn) {
        Matcher matcher = CONSTANT_PATTERN.matcher(searchIn);

        if (matcher.find()) {
            String constant = matcher.group();
            TOKENS.add(new Token(TokenType.CONSTANT, Double.parseDouble(constant)));
            return constant.length();
        }
        return -1;
    }

    /**
     * Is to find and save first variable from a string as token.
     * It save variable to token's list, and to variable's map.
     *
     * @param searchIn - string where to search
     * @ returns the length of the found variable's name (number of characters), if found,
     * and -1, - if not found
     */
    private int tokenizeVariable(String searchIn) {
        Matcher matcher = VARIABLES_PATTERN.matcher(searchIn);

        if (matcher.find()) {
            String name = matcher.group();
            TOKENS.add(new Token(TokenType.VARIABLE, name));
            if (!VARIABLES.containsKey(name)) {
                VARIABLES.put(name, new Token(TokenType.VARIABLE, name));
            }
            return name.length();
        }
        return -1;
    }

    /**
     * Is to save operation(char) as token to token's list.
     *@param operation - char which can be: '+', '-', '*', '/', '^'
     * */
    private void tokenizeOperation(char operation) {
        TOKENS.add(new Token(OPERATIONS_TOKEN_TYPE[OPERATIONS.indexOf(operation)], String.valueOf(operation)));
    }

}
