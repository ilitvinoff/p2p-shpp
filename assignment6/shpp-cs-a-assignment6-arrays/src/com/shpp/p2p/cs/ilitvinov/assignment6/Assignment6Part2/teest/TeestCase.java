package com.shpp.p2p.cs.ilitvinov.assignment6.Assignment6Part2.teest;

public abstract class TeestCase {
    public abstract String getName();

    public abstract boolean runTest();

    public final TeestResult resultOf() {
        try {
            return this.runTest() ? TeestResult.success() : TeestResult.failure();
        } catch (Exception var2) {
            return TeestResult.exception(var2);
        }
    }
}
