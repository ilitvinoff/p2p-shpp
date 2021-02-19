package com.shpp.p2p.cs.ilitvinov.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Enter ur plate number, to get the possible
 * words u can make from the letters from ur number
 */
public class Assignment5part3 extends TextProgram {

    private static final String DICTIONARY_PATH = "assets/en-dictionary.txt";
    private static final String EXIT = "x";

    public void run() {
        String input;

        while (!(input = inputValue(EXIT)).equals((EXIT))) {

            ArrayList<String> dictionary = createList(input);

            for (String listString : dictionary) {
                System.out.println(listString);
            }

            System.out.printf("You may create %d words with this letters.\n" +
                    "Scroll up to see them\n", dictionary.size());
        }
    }

    /**
     * Method checking if input is correct.
     *
     * @return if correct return entered string
     */
    private String inputValue(String exit) {
        Scanner in = new Scanner(System.in);
        String str = "";
        boolean loop = true;
        boolean title = true;

        while (loop) {
            loop = false;

            if (title) {

                System.out.println("\nTo exit: type - x\nEnter ur letters, pls:");
            }

            str = in.next();

            if (str.equals(exit)) {
                break;
            }

            if (str.length() != 3) {
                System.out.println("Plate number must consist of 3 symbols.");
                title = false;
                loop = true;
                continue;
            }


            char[] strCharArr = stringToCharArr(str.toLowerCase());

            for (char symbol : strCharArr) {

                if (!Character.isLetter(symbol)) {
                    System.out.println("Use letters only, pls!");
                    title = false;
                    loop = true;
                    break;
                }
            }
        }

        return str;
    }

    /**
     * Convert string to array of chars;
     *
     * @param str - string;
     * @return char[] array
     */
    private char[] stringToCharArr(String str) {
        char[] array = new char[str.length()];

        for (int i = 0; i < str.length(); i++) {

            array[i] = str.charAt(i);
        }
        return array;
    }

    /**
     * Searching in dictionary for possible words, that can be created from entered letters.
     *
     * @param inputStr - string of letters
     * @return ArrayList of strings(possible words)
     */
    private ArrayList<String> createList(String inputStr) {

        ArrayList<String> dictionary = readFile(DICTIONARY_PATH);
        ArrayList<String> result = new ArrayList<>();
        char[] input = stringToCharArr(inputStr.toLowerCase());

        for (String word : dictionary) {
            int indexFrom = 0, counter = 0;

            for (char letter : input) {


                if (word.indexOf(letter, indexFrom) != -1) {
                    indexFrom = word.indexOf(letter, indexFrom) + 1;
                    ++counter;


                } else {
                    break;
                }

                if (counter == input.length) {
                    result.add(word);
                    break;
                }

            }
        }
        return result;
    }

    private ArrayList<String> readFile(String path) {
        ArrayList<String> result = new ArrayList<>();
        String element;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((element = br.readLine()) != null) {
                result.add(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
