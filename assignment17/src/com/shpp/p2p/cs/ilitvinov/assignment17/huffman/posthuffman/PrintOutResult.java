package com.shpp.p2p.cs.ilitvinov.assignment17.huffman.posthuffman;

import java.io.File;

/**
 * Printout result of compressing/decompressing.
 */

public class PrintOutResult {

    /**
     * Byte's amount in 1mB
     */
    private static final double kB_LENGTH = 1024.0;

    /**
     * Amount of milliseconds in 1 second
     */
    private static final double SEC_LENGTH = 1000.0;

    /**
     * Max percent value
     */

    private static final double MAX_PERCENT = 100.0;

    File fileIn;
    File fileOut;

    public PrintOutResult(String pathIn, String pathOut) {
        this.fileIn = new File(pathIn);
        this.fileOut = new File(pathOut);
    }

    /**
     * Printout result of compressing/decompressing.
     *
     * @param processTime - time of compressing/decompressing process.
     * @throws Exception if any file is not exist
     */
    public void printOutResult(long processTime) throws Exception {
        String[] filesNames = getFilesNames();
        long sourceSize = fileIn.length();
        long destSize = fileOut.length();

        double efficiency = sourceSize == 0 ? sourceSize : destSize * MAX_PERCENT / sourceSize;

        System.out.printf("Source file:\n<< %s >> [ %.3f kB ]   ( %d bytes)\n", filesNames[0], sourceSize / kB_LENGTH, sourceSize);
        System.out.printf("\nDestination file:\n<< %s >>  [ %.3f kB ]   ( %d bytes)\n", filesNames[1], destSize / kB_LENGTH, destSize);
        System.out.printf("\nDestination file's size is: [ %.2f%% ] of source's file\n", efficiency);
        System.out.printf("process time: [ %.3f sec ]\n", processTime / SEC_LENGTH);
    }

    /**
     * Check if files exist.
     *
     * @return String[] - consist of 2 elements:
     * 1st - source file name
     * 2nd - destination file name.
     * @throws Exception if any file is not exist.
     */
    private String[] getFilesNames() throws Exception {

        if (!fileIn.exists()) {
            throw new Exception(String.format("File: %s - is not exist!", fileIn.getPath()));
        } else if (!fileOut.exists()) {
            throw new Exception(String.format("File: %s - is not exist!", fileOut.getPath()));
        }
        return new String[]{fileIn.getName(), fileOut.getName()};
    }
}
