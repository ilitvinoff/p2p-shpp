package com.shpp.p2p.cs.ilitvinov.assignment17.calc.precalc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**Static class to tokenize the expression into tokens*/
public class Lexer {
    private static final Pattern CONSTANT_PATTERN = Pattern.compile("\\d+(\\.\\d+)?");
    private static final Pattern VARIABLES_PATTERN = Pattern.compile("[a-zA-Z]+\\w*");
    private static final String[] FUNCTION_NAME = {
            "cos", "sin", "tan", "atan", "log10", "log2", "sqrt"
    };

    private static final TokenType[] FUNCTIONS = {
            TokenType.COS, TokenType.SIN, TokenType.TAN, TokenType.ATAN, TokenType.LOG10, TokenType.LOG2,
            TokenType.SQRT
    };

    private static final String OPERATIONS = "+-/*^()";
    private static final TokenType[] OPERATIONS_TOKEN_TYPE = {
            TokenType.PLUS, TokenType.MINUS,
            TokenType.DIVISION, TokenType.MULTIPLY,
            TokenType.POWER, TokenType.LPAREN, TokenType.RPAREN
    };

    /**tokenize string into tokens
     * @param expression - string, example: (a+b)*2/3-cos(x)...
     * Expression string may consist of:
     *  * constant: any digit(decimals must be separated with '.');
     *  * variable: it must consist of characters(latin alphabet), underscores and numbers,and
     *  * it must start with character.
     *  * operations:
     *  * + summation;
     *  * - subtraction;
     *  * * multiplication;
     *  * / division;
     *  * ^ raising to power;
     *  * cos(expression) - cosines
     *  * sin(expression) - sines
     *  * tan(expression) - tangent
     *  * atan(expression) - arctangent
     *  * log10(expression) - decimal logarithm
     *  * log2(expression) - binary logarithm
     *  * sqrt(expression) - square root*/
    public static List<Token> tokenize(String expression) throws Exception {
        int index = 0;
        List<Token> tokens = new ArrayList<>();
        ExpressionValidation.validateExpression(expression);

        while (index < expression.length()) {
            if (Character.isDigit(expression.charAt(index))) {
                String searchIn = expression.substring(index);
                index += tokenizeNumber(tokens, searchIn);
            } else if (Character.isLetter(expression.charAt(index))) {
                String searchIn = expression.substring(index);
                index+= tokenizeLetter(tokens, searchIn);
            } else if (OPERATIONS.indexOf(expression.charAt(index)) != -1) {
                tokenizeOperation(tokens, expression.charAt(index));
                index++;
            } else {
                throw new Exception("Expression's spelling error. Unknown symbol: " + expression.charAt(index));
            }

            if (index == expression.length()) {
                tokens.add(new Token(TokenType.EOF, "eof"));
            }
        }
        return tokens;
    }

    /**Make token of constant type
     * @param tokens - List<Token> where to add token
     * @param searchIn - string where method search for first match of constant expression
     * @return length (how many characters) of constant
     * */
    private static int tokenizeNumber(List<Token> tokens, String searchIn) throws Exception {
        Matcher matcher = CONSTANT_PATTERN.matcher(searchIn);

        if (matcher.find()) {
            String constant = matcher.group();
            tokens.add(new Token(TokenType.CONSTANT, constant, Double.parseDouble(constant)));
            return constant.length();
        } else {
            throw new Exception("Bad syntax for constant. Watch from here: " + searchIn);
        }
    }

    /**Defines if expression is variable or function and adds the appropriate token to list
     * @param tokens - List<Token> where to add token
     * @param searchIn - string where method search for first match of letter expression
     * @return length (how many characters) of letter expression
     * */
    private static int tokenizeLetter(List<Token> tokens, String searchIn) throws Exception {
        Matcher varMatcher = VARIABLES_PATTERN.matcher(searchIn);

        if (varMatcher.find()) {
            String name = varMatcher.group();
            if (!tokenizeFunction(tokens, name)) {

                tokens.add(new Token(TokenType.VARIABLE, name));
            }
            return name.length();
        } else {
            throw new Exception("Bad syntax for variable or function. Watch from here: " + searchIn);
        }
    }

    /**Defines if letter expression is function (cos, sin etc...)
     * @param tokens - List<Token> where to add token
     * @param searchIn - string where method search for first match of function expression
     * @return true if letter expression matches function, false - otherwise*/
    private static boolean tokenizeFunction(List<Token> tokens, String searchIn) {
        for (int i = 0; i < FUNCTION_NAME.length; i++) {
            if (searchIn.equals(FUNCTION_NAME[i])) {
                tokens.add(new Token(FUNCTIONS[i], searchIn));
                return true;
            }
        }
        return false;
    }

    /**Defines which arithmetic operation is. Add it to token's list
     * @param tokens - List<Token> where to add token
     * @param operation - char which includes operation */
    private static void tokenizeOperation(List<Token> tokens, char operation) {
        tokens.add(new Token(OPERATIONS_TOKEN_TYPE[OPERATIONS.indexOf(operation)], String.valueOf(operation)));
    }
}
