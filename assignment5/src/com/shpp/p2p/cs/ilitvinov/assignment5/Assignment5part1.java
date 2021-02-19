package com.shpp.p2p.cs.ilitvinov.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Counting syllables of the words
 */

public class Assignment5part1 extends TextProgram {
    private static final String[][] test =
            {{"eeeeee-1", "eeeeee"}, {"Unity-3", "Unity"}, {"Unite-2", "Unite"}, {"Growth-1", "Growth"}
                    , {"Possibilities-5", "Possibilities"}, {"Nimble-1", "Nimble"}, {"Me-1", "Me"},
                    {"Beautiful-3", "Beautiful"}, {"Manatee-3", "Manatee"}};

    public void run() {
        String word;
//        *Use to test

        for (String[] string : test) {
            word = string[1];
            println(string[0] + " is - " + syllablesIn(word));
        }

        /** Repeatedly prompt the user for a word and print out the estimated
         * number of syllables in that word.
         */

        while (true) {
            //String word = readLine("Enter a single word: ");
            word = readLine("Enter a single word: ");
            println("  Syllable count: " + syllablesIn(word));
        }
    }

    /**
     * Given a word, estimates the number of syllables in that word according to the
     * heuristic specified in the handout.
     *
     * @param word A string containing a single word.
     * @return An estimate of the number of syllables in that word.
     */
    private int syllablesIn(String word) {
        String vowel = "";
        int counter = 0;
        boolean isPreviousVowel = false;
        boolean isVowel;

        vowel = getVowelFromFile(vowel);

        for (int i = 0; i < word.length(); i++) {

            isVowel = isVowel(word.charAt(i), vowel);

            if (isVowel && !isPreviousVowel) {
                isPreviousVowel = true;
                counter++;
            }
            if (!isVowel) {
                isPreviousVowel = false;
            }
        }
        if (word.charAt(word.length() - 1) == 'e' && counter > 1 && !isPreviousVowel) {
            counter--;
        }
        return counter;
    }

    private String getVowelFromFile(String vowel) {
        try {
            BufferedReader vowelFile = new BufferedReader(new FileReader("/home/laptopuser/Documents/IdeaProjects/Assingments/assignment5/assets/vowel.txt"));
            vowel = vowelFile.readLine();
            vowelFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vowel;
    }

    private boolean isVowel(char wordChar, String vowel) {

        for (int i = 0; i < vowel.length(); i++) {

            if (wordChar == vowel.charAt(i)) {
                return true;
            }
        }
        return false;
    }
}