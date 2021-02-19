package com.shpp.p2p.cs.ilitvinov.assignment17.calc.postcalc.ast;

import com.shpp.p2p.cs.ilitvinov.assignment17.calc.postcalc.TokenType;
import com.shpp.p2p.cs.ilitvinov.assignment17.collections.MyHashMap;

/**This class describes the behavior of unary operations in the expression*/
public class UnaryOperations implements Expression {
    private final Expression expr;
    private final TokenType operation;

    public UnaryOperations(Expression expr, TokenType operation) {
        this.expr = expr;
        this.operation = operation;
    }

    @Override
    public double evaluate(MyHashMap<String, Double> variables) throws Exception {
        if (operation == TokenType.MINUS) {
            return -expr.evaluate(variables);
        }
        return expr.evaluate(variables);
    }

    @Override
    public String toString() {
        return operation.toString() + expr;
    }
}