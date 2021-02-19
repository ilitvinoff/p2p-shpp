package com.shpp.p2p.cs.ilitvinov.assignment17.calc.postcalc.ast;

import com.shpp.p2p.cs.ilitvinov.assignment17.calc.postcalc.Token;
import com.shpp.p2p.cs.ilitvinov.assignment17.calc.postcalc.TokenType;
import com.shpp.p2p.cs.ilitvinov.assignment17.collections.MyHashMap;

/**This class describes the behavior of constant or variable in the expression*/
public class ConstantExpression implements Expression {
    private final Token token;

    public ConstantExpression(Token token) {
        this.token = token;
    }

    @Override
    public double evaluate(MyHashMap<String, Double> variables) throws Exception {
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
