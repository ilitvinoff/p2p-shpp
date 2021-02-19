package com.shpp.p2p.cs.ilitvinov.assignment3;

import acm.util.RandomGenerator;
import com.shpp.cs.a.console.TextProgram;

/**
 * Program imitates the St Petersburg game. There are two players: Lucky and Sweating.
 * Game is ended when Lucky gets 20$.
 * Sweating put 1$ on table, Lucky throws a coin.
 * If eagle - Sweating doubles the money on the table.
 * If tails - Lucky take a cash prise.
 * if Lucky won less than 20$, the game will be repeated
 */

public class Assignment3Part5 extends TextProgram {

    public void run() {
        theGamePrintOut();
    }

    private void theGamePrintOut() {

        int startMoney = 1, prize = 0;

        /*Creating of RandomGenerator object.
        With method object.nextBoolean (true or false)...*/
        RandomGenerator coin = new RandomGenerator();
        while (prize < 20) {
            println("This game, you earned $" + startMoney);
            if (coin.nextBoolean(0.5)) {
                startMoney *= 2;

            } else {
                prize += startMoney;
                startMoney = 1;
            }
            println("Your total is $" + prize+"\n");
        }
    }
}