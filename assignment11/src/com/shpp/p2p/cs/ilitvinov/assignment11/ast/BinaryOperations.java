package com.shpp.p2p.cs.ilitvinov.assignment11.ast;

import com.shpp.p2p.cs.ilitvinov.assignment11.TokenType;
import java.util.HashMap;

/**This class describes form of expression with binary operation
 */
public class BinaryOperations implements Expression {

    private Expression expr1;
    private Expression expr2;
    private TokenType operation;

    public BinaryOperations(Expression expr1, Expression expr2, TokenType operation) {
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.operation = operation;
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) throws Exception {

        switch (operation) {
            case PLUS:
                return expr1.evaluate(variables) + expr2.evaluate(variables);
            case MINUS:
                return expr1.evaluate(variables) - expr2.evaluate(variables);
            case MULTIPLY:
                return expr1.evaluate(variables) * expr2.evaluate(variables);
            case DIVISION:
                if (expr2.evaluate(variables) == 0) {
                    throw new Exception("Zero division found!\n" + this.toString());
                }
                return expr1.evaluate(variables) / expr2.evaluate(variables);
            default:
                if (expr1.evaluate(variables) == 0 && expr2.evaluate(variables) < 0) {
                    throw new Exception("Zero division found!\n" + this.toString());
                }
                return Math.pow(expr1.evaluate(variables), expr2.evaluate(variables));
        }
    }

    @Override
    public String toString() {
        return expr1 + " " + operation.toString() + " " + expr2;
    }
}
