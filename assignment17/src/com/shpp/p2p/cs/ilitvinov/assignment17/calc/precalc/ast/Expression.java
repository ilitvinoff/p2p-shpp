package com.shpp.p2p.cs.ilitvinov.assignment17.calc.precalc.ast;

import java.util.HashMap;

/**Functional interface for splitting an expression into nodes*/
public interface Expression {
    double evaluate(HashMap<String, Double> variables) throws Exception;
}
