package com.shpp.p2p.cs.ilitvinov.assignment11.ast;

import com.shpp.p2p.cs.ilitvinov.assignment11.Token;
import com.shpp.p2p.cs.ilitvinov.assignment11.TokenType;

import java.util.HashMap;

/**This class describes the behavior of constant or variable in the expression*/
public class ConstantExpression implements Expression {
    private Token token;

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
