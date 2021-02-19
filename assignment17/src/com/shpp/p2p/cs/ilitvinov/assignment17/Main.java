package com.shpp.p2p.cs.ilitvinov.assignment17;

import com.shpp.p2p.cs.ilitvinov.assignment17.calc.TestCalc;
import com.shpp.p2p.cs.ilitvinov.assignment17.huffman.TestHuffman;
import com.shpp.p2p.cs.ilitvinov.assignment17.silhouettes.TestSilhouettes;

/**
 * Starts tests for calc, archiver, and silhouettes searcher apps. Printout work time.
 */

public class Main {

    public static void main(String[] args) {
        TestCalc testCalc = new TestCalc();
        TestSilhouettes testSilhouettes = new TestSilhouettes();
        TestHuffman testHuffman = new TestHuffman();

        try {
            testCalc.testCalc();
            testSilhouettes.testSilhouettes();
            testHuffman.testHuffman();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
