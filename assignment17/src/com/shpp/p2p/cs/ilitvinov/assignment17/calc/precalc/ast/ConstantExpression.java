package com.shpp.p2p.cs.ilitvinov.assignment17.calc.precalc.ast;

import com.shpp.p2p.cs.ilitvinov.assignment17.calc.precalc.Token;
import com.shpp.p2p.cs.ilitvinov.assignment17.calc.precalc.TokenType;

import java.util.HashMap;

/**This class describes the behavior of constant or variable in the expression*/
public class ConstantExpression implements Expression {
    private final Token token;

    public ConstantExpression(Token token) {
        this.token = token;
    }

    @Override
    public double evaluate(HashMap<String, Double> variables) throws Exception {
        if (token.getType() == TokenType.VARIABLE) {
            return variables.get(token.getName());
        }
        return token.getValue();
    }

    @Override
    public String toString() {
        return "" + token.getName();
    }
}
