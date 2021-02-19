package com.shpp.p2p.cs.ilitvinov.assignment17.calc.precalc;

/**Class to describe token.*/
public class Token {
    private final TokenType type;
    private final String name;
    private double value;

    public Token(TokenType type, String name) {
        this.type = type;
        this.name = name;
    }

    public Token(TokenType type, String name, double value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public String valueToString() {
        if (type != TokenType.VARIABLE && type != TokenType.CONSTANT) {
            return name;
        }
        if (value < 0) {
            return "(" + this.value + ")";
        }
        return String.valueOf(this.value);
    }

    public String typeToString() {
        return String.valueOf(this.type);
    }

    @Override
    public String toString() {
        return "name: " + name + "; type: " + typeToString() + "; value: " + valueToString();
    }
}
