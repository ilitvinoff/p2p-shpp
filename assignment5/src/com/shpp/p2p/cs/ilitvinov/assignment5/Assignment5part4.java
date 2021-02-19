package com.shpp.p2p.cs.ilitvinov.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * CSV - PARSING
 */

public class Assignment5part4 extends TextProgram {

    private static final String FILE_PATH = "assets/part4.csv";

    public void run() {
        int index = 1;

        while (index >= 0) {
            Scanner input = new Scanner(System.in);
            ArrayList<String> result = new ArrayList<>();

            System.out.println("\nTo exit enter any negative number.\nEnter column index plz");

            try {

                index = input.nextInt();

            } catch (InputMismatchException e) {
                System.out.println("only positive digits, plz,\nor negative - to exit");
                continue;
            }

            if (index >= 0) {

                result = extractColumn(FILE_PATH, index);
            }

            if (result != null) {

                for (String str : result) {
                    System.out.println(str);
                }
            }
        }

    }

    /**
     * Create ArrayList of strings from one string.
     * According to csv-files formatting rules, method extracts from string to ArrayList elements,
     * that are separated with coma, if a coma between double quotes - it is ignored.
     *
     * @param line - String
     * @return ArrayList of strings
     */
    private ArrayList<String> fieldsIn(String line) {
        ArrayList<String> resultList = new ArrayList<>();
        ArrayList<Character> charBufferList = new ArrayList<>();

        for (int i = 0; i < line.length(); i++) {

            if (line.charAt(i) != '"' && line.charAt(i) != ',') {

                charBufferList.add(line.charAt(i));
            }

            if (line.charAt(i) == ',' && i != 0) {
                StringBuilder buffer = new StringBuilder();

                for (char symbol : charBufferList) {
                    buffer.append(symbol);
                }
                resultList.add(buffer.toString());
                charBufferList.clear();
            }

            if (line.charAt(i) == '"') {
                StringBuilder buffer = new StringBuilder();

                buffer.append(findQuotes(line, i));

                i += buffer.length() + 2;

                resultList.add(buffer.toString());
            }

            if (i == line.length() - 1 && charBufferList.size() > 0) {
                StringBuilder buffer = new StringBuilder();

                for (char symbol : charBufferList) {
                    buffer.append(symbol);
                }

                resultList.add(buffer.toString());
                return resultList;
            }
        }

        return resultList;
    }

    /**
     * Extract string between opening and closing double quotes,
     * according to rules for csv-files.
     * Opening and closing double quotes - are ignored.
     *
     * @param line      - string.
     * @param indexFrom - string's symbol to start search from.
     * @return string between between opening and closing double quotes.
     */
    private String findQuotes(String line, int indexFrom) {
        StringBuilder buffer = new StringBuilder();

        //ignore if 1st symbol is "opening" double quote.
        if (line.charAt(indexFrom) == '"') {

            indexFrom++;
        }

        while (line.charAt(indexFrom) != '"' && indexFrom < line.length() - 1) {

            buffer.append(line.charAt(indexFrom));

            indexFrom++;
        }

        //ignore "closing" double quote.
        indexFrom++;

        /*if next symbol after double quote is not coma, then
         * quote was  not "closing", so we need to search one more time*/
        if (indexFrom < line.length() - 1 && line.charAt(indexFrom) != ',') {

            //if it was not "closing quote", we need return it back"
            buffer.append('"');
            buffer.append(findQuotes(line, indexFrom));

        }

        return buffer.toString();
    }

    /**
     * Extracts from file(*.csv) column of values.
     *
     * @param filename    - path to file.
     * @param columnIndex - number of column to extract.
     * @return ArrayList, each element of - is value of cell(intersection of line number and column number) in file
     * if columnIndex is out of range, returns null.
     */
    private ArrayList<String> extractColumn(String filename, int columnIndex) {
        ArrayList<String> resultList = new ArrayList<>();
        ArrayList<String> bufferFile = readFile(filename);
        ArrayList<String> bufferList;

        for (String string : bufferFile) {

            bufferList = fieldsIn(string);

            if (columnIndex > bufferList.size() - 1) {
                System.out.println("Column index is out of range");
                return null;
            }

            resultList.add(bufferList.get(columnIndex));

        }

        return resultList;
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
