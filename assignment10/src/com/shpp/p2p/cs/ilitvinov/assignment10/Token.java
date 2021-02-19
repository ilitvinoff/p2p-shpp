package com.shpp.p2p.cs.ilitvinov.assignment10;

/**
 * This class describe token's characteristics
 */

public class Token {
    private TokenType type;
    String name;
    private double value;

    public Token(TokenType type) {
        this.type = type;
    }

    public Token(TokenType type, String name) {
        this.type = type;
        this.name = name;
    }

    public Token(TokenType type, double value) {
        this.type = type;
        this.value = value;
    }

    public Token(TokenType type, String name, double value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public Token(Token token) {
        this.type = token.getType();
        this.name = token.getName();
        this.value = token.getValue();
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

    public void setType(TokenType type) {
        this.type = type;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String valueToString() {
        if (value < 0) {
            return "(" + String.valueOf(this.value) + ")";
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
