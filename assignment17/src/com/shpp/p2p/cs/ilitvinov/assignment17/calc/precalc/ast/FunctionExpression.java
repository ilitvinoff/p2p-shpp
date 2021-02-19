package com.shpp.p2p.cs.ilitvinov.assignment17.calc.precalc.ast;

import com.shpp.p2p.cs.ilitvinov.assignment17.calc.precalc.TokenType;

import java.util.HashMap;

/**This class describes the behavior of functions in the expression*/
public class FunctionExpression implements Expression {

    private final Expression expr;
    private final TokenType func;

    public FunctionExpression(Expression expr, TokenType func) {
        this.expr = expr;
        this.func = func;
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) throws Exception {
        switch (func) {
            case SIN:
                return Math.sin((expr.evaluate(variables)));
            case TAN:
                return Math.tan((expr.evaluate(variables)));
            case ATAN:
                return Math.atan((expr.evaluate(variables)));
            case LOG10:
                if (expr.evaluate(variables) <= 0) {
                    throw new Exception("The log() function's operand must be greater than zero.\n" + this.toString());
                }
                return Math.log10(expr.evaluate(variables));
            case LOG2:
                if (expr.evaluate(variables) <= 0) {
                    throw new Exception("The log() function's operand must be greater than zero.\n" + this.toString());
                }
                return Math.log10(expr.evaluate(variables)) / Math.log10(2);
            case SQRT:
                if (expr.evaluate(variables) < 0) {
                    throw new Exception(" Use positive digits to get sqrt().\n" + this.toString());
                }
                return Math.sqrt(expr.evaluate(variables));
            default:
                return Math.cos(Math.toRadians(expr.evaluate(variables)));
        }
    }

    @Override
    public String toString() {
        return "" + func + expr;
    }
}
