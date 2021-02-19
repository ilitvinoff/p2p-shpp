package com.shpp.p2p.cs.ilitvinov.assignment17.calc.postcalc;

import com.shpp.p2p.cs.ilitvinov.assignment17.calc.postcalc.ast.*;
import com.shpp.p2p.cs.ilitvinov.assignment17.collections.MyArrayList;
import com.shpp.p2p.cs.ilitvinov.assignment17.collections.MyHashMap;
import com.shpp.p2p.cs.ilitvinov.assignment17.collections.MyList;

/**
 * Static class to parse expression, presented as MyList of tokens(MyList<Token> tokens),
 * into MyList of nodes(MyList<Expression>
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
     * Current position in MyList of tokens
     */
    private static int position;

    public Parser() {
        position = 0;
    }

    /**
     * Return MyHashMap<String,Double> that includes all presented  variables's names in MyList of tokens(MyList<Token>),
     * the value of each variable (Double) = null;
     *
     * @param tokens - MyList<Toke>
     * @return MyHashMap<String, Double>
     */
    public MyHashMap<String, Double> getVariables(MyList<Token> tokens) {
        MyHashMap<String, Double> variables = new MyHashMap<>();
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
     * @param tokens - MyList<Token>,
     * @param type   - TokenType,
     * @return true, - if match
     * false, - if not
     */
    private boolean match(MyList<Token> tokens, TokenType type) {
        if (getType(tokens) == type) {
            position++;
            return true;
        }
        return false;
    }

    /**
     * If token's type match needed type increase position by 1
     *
     * @param tokens      - MyList<Token>,
     * @param arrayOfType - TokenType[],
     * @return true, - if match
     * false, - if not
     */
    private boolean match(MyList<Token> tokens, TokenType[] arrayOfType) {
        for (TokenType type : arrayOfType) {
            if (getType(tokens) == type) {
                position++;
                return true;
            }
        }
        return false;
    }

    /**
     * @param tokens - MyList<Token>,
     * @return type of current token
     */
    private TokenType getType(MyList<Token> tokens) {
        if (position >= tokens.size()) return TokenType.EOF;
        return tokens.get(position).getType();
    }
    /**@param tokens - MyList<Token>
     * @return object of Expression type if expression matches number or variable*/
    private Expression numberExpression(MyList<Token> tokens) throws Exception {
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

    /**@param tokens - MyList<Token>
     * @return object of Expression type for unary operations*/
    private Expression unaryExpression(MyList<Token> tokens) throws Exception {
        Token currentToken = tokens.get(position);
        if (match(tokens, UNARY_TYPE)) {
            return new UnaryOperations(numberExpression(tokens), currentToken.getType());
        }
        return numberExpression(tokens);
    }

    /**@param tokens - MyList<Token>
     * @return object of Expression type for functions's operations*/
    private Expression functionExpression(MyList<Token> tokens) throws Exception {
        TokenType currentTokenType = getType(tokens);
        if (match(tokens, FUNCTION_TYPE)) {
            return new FunctionExpression(unaryExpression(tokens), currentTokenType);
        }
        return unaryExpression(tokens);
    }

    /**@param tokens - MyList<Token>
     * @return object of Expression type for exponentiation operation*/
    private Expression powerExpression(MyList<Token> tokens) throws Exception {
        Expression result = functionExpression(tokens);
        TokenType currentTokenType = getType(tokens);

        while (match(tokens, POWER_TYPE)) {
            result = new BinaryOperations(result, unaryExpression(tokens), currentTokenType);
        }
        return result;

    }

    /**@param tokens - MyList<Token>
     * @return object of Expression type for multiply or division operations*/
    private Expression multiplyExpression(MyList<Token> tokens) throws Exception {
        Expression result = powerExpression(tokens);
        TokenType currentTokenType = getType(tokens);

        while (match(tokens, MULTIPLY_TYPE)) {
            result = new BinaryOperations(result, powerExpression(tokens), currentTokenType);
            currentTokenType = getType(tokens);
        }
        return result;
    }

    /**@param tokens - MyList<Token>
     * @return object of Expression type for additive or subtraction operations*/
    private Expression sumExpression(MyList<Token> tokens) throws Exception {
        Expression result = multiplyExpression(tokens);
        TokenType currentTokenType = getType(tokens);

        while (match(tokens, SUM_TYPE)) {
            result = new BinaryOperations(result, multiplyExpression(tokens), currentTokenType);
            currentTokenType = getType(tokens);
        }
        return result;
    }

    /**
     * Returns MyList of nodes that parsed from MyList of tokens.
     *
     * @param tokens - MyList<Token>
     * @return MyList<Expression> nodes
     */
    public MyList<Expression> getNodes(MyList<Token> tokens) throws Exception {
        MyList<Expression> result = new MyArrayList<>();
        while (!match(tokens, TokenType.EOF)) {
            result.add(sumExpression(tokens));
        }
        return result;
    }
}
