package com.shpp.p2p.cs.ilitvinov.assignment17.calc.postcalc.ast;

import com.shpp.p2p.cs.ilitvinov.assignment17.collections.MyHashMap;


/**Functional interface for splitting an expression into nodes*/
public interface Expression {
    double evaluate(MyHashMap<String, Double> variables) throws Exception;
}
