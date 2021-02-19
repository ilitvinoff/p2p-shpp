package com.shpp.p2p.cs.ilitvinov.assignment11;

import com.shpp.p2p.cs.ilitvinov.assignment11.ast.*;

import java.util.*;

/**
 * Static class to parse expression, presented as list of tokens(List<Token> tokens),
 * into list of nodes(List<Expression>
 * and map of variables (HashMap<String name, Double value>), using the recursive descent method.
 */
public class Parser {

    /**Unary operation marker*/
    private static final TokenType UNARY_TYPE = TokenType.MINUS;

    /**Array to compare if token's type equals function*/
    private static final TokenType[] FUNCTION_TYPE = {
            TokenType.SIN, TokenType.COS, TokenType.TAN,
            TokenType.ATAN, TokenType.LOG10, TokenType.LOG2, TokenType.SQRT
    };

    /**Array to compare if token's type equals additive operations*/
    private static final TokenType[] SUM_TYPE = {
            TokenType.PLUS, TokenType.MINUS
    };

    /**Array to compare if token's type equals multiply operations*/
    private static final TokenType[] MULTIPLY_TYPE = {TokenType.MULTIPLY, TokenType.DIVISION};

    /**Exponentiation operation marker*/
    private static final TokenType POWER_TYPE = TokenType.POWER;

    /**
     * Current position in list of tokens
     */
    private static int position;

    public Parser() {
        position = 0;
    }

    /**
     * Return LinkedHashMap<String,Double> that includes all presented  variables's names in list of tokens(List<Token>),
     * the value of each variable (Double) = null;
     *
     * @param tokens - List<Toke>
     * @return LinkedHashMap<String, Double>
     */
    public LinkedHashMap<String, Double> getVariables(List<Token> tokens) {
        LinkedHashMap<String, Double> variables = new LinkedHashMap<>();
        for (Token token : tokens) {
            if (token.getType() == TokenType.VARIABLE && !variables.containsKey(token.getName())) {
                variables.put(token.getName(), null);
            }
        }
        return variables;
    }

    /**
     * If token's type match needed type increase position by 1
     *
     * @param tokens - List<Token>,
     * @param type   - TokenType,
     * @return true, - if match
     * false, - if not
     */
    private boolean match(List<Token> tokens, TokenType type) {
        if (getType(tokens) == type) {
            position++;
            return true;
        }
        return false;
    }

    /**
     * If token's type match needed type increase position by 1
     *
     * @param tokens      - List<Token>,
     * @param arrayOfType - TokenType[],
     * @return true, - if match
     * false, - if not
     */
    private boolean match(List<Token> tokens, TokenType[] arrayOfType) {
        for (TokenType type : arrayOfType) {
            if (getType(tokens) == type) {
                position++;
                return true;
            }
        }
        return false;
    }

    /**
     * @param tokens - List<Token>,
     * @return type of current token
     */
    private TokenType getType(List<Token> tokens) {
        if (position >= tokens.size()) return TokenType.EOF;
        return tokens.get(position).getType();
    }
    /**@param tokens - List<Token>
     * @return object of Expression type if expression matches number or variable*/
    private Expression numberExpression(List<Token> tokens) throws Exception {
        Token currentValueToken = tokens.get(position);

        if (match(tokens, TokenType.CONSTANT) || match(tokens, TokenType.VARIABLE)) {
            return new ConstantExpression(currentValueToken);

        } else if (match(tokens, TokenType.LPAREN)) {

            Expression parenthesesResult = sumExpression(tokens);
            if (!match(tokens, TokenType.RPAREN)) {
                throw new Exception("missing closing paren: " + currentValueToken.toString() + "\nexpression: " + parenthesesResult);
            }

            return parenthesesResult;
        } else if (match(tokens, TokenType.EOF)) {
            return new ConstantExpression(new Token(TokenType.EOF, "ololo"));
        } else {
            throw new Exception(String.format("Unknown expression: {%s}", currentValueToken.toString()));
        }

    }

    /**@param tokens - List<Token>
     * @return object of Expression type for unary operations*/
    private Expression unaryExpression(List<Token> tokens) throws Exception {
        Token currentToken = tokens.get(position);
        if (match(tokens, UNARY_TYPE)) {
            return new UnaryOperations(numberExpression(tokens), currentToken.getType());
        }
        return numberExpression(tokens);
    }

    /**@param tokens - List<Token>
     * @return object of Expression type for functions's operations*/
    private Expression functionExpression(List<Token> tokens) throws Exception {
        TokenType currentTokenType = getType(tokens);
        if (match(tokens, FUNCTION_TYPE)) {
            return new FunctionExpression(unaryExpression(tokens), currentTokenType);
        }
        return unaryExpression(tokens);
    }

    /**@param tokens - List<Token>
     * @return object of Expression type for exponentiation operation*/
    private Expression powerExpression(List<Token> tokens) throws Exception {
        Expression result = functionExpression(tokens);
        TokenType currentTokenType = getType(tokens);

        while (match(tokens, POWER_TYPE)) {
            result = new BinaryOperations(result, unaryExpression(tokens), currentTokenType);
        }
        return result;

    }

    /**@param tokens - List<Token>
     * @return object of Expression type for multiply or division operations*/
    private Expression multiplyExpression(List<Token> tokens) throws Exception {
        Expression result = powerExpression(tokens);
        TokenType currentTokenType = getType(tokens);

        while (match(tokens, MULTIPLY_TYPE)) {
            result = new BinaryOperations(result, powerExpression(tokens), currentTokenType);
            currentTokenType = getType(tokens);
        }
        return result;
    }

    /**@param tokens - List<Token>
     * @return object of Expression type for additive or subtraction operations*/
    private Expression sumExpression(List<Token> tokens) throws Exception {
        Expression result = multiplyExpression(tokens);
        TokenType currentTokenType = getType(tokens);

        while (match(tokens, SUM_TYPE)) {
            result = new BinaryOperations(result, multiplyExpression(tokens), currentTokenType);
            currentTokenType = getType(tokens);
        }
        return result;
    }

    /**
     * Returns list of nodes that parsed from list of tokens.
     *
     * @param tokens - List<Token>
     * @return List<Expression> nodes
     */
    public List<Expression> nodeList(List<Token> tokens) throws Exception {
        List<Expression> result = new ArrayList<>();
        while (!match(tokens, TokenType.EOF)) {
            result.add(sumExpression(tokens));
        }
        return result;
    }
}
