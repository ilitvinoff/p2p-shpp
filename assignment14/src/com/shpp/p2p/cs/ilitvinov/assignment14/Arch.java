package com.shpp.p2p.cs.ilitvinov.assignment14;

import java.io.*;
import java.util.HashMap;

public class Arch {
    private static final int CONTAINERS_BITS_LENGTH = 8;

    /**
     * Length of byte's cluster to read and write
     */
    private static final int CLUSTER_LENGTH = 65536;

    /**
     * Map to keep unique bytes.
     * key - fileIn's unique byte
     * value - associated bit sequence
     */
    private static HashMap<Byte, Byte> byteMap;

    /**
     * Source file
     */
    private static File fileIn;

    /**
     * Result file
     */
    private static File fileOut;

    /**
     * Minimal Bit's amount value to save one unique byte
     */
    private static int bitAmount;

    /**
     * @param fileIn  - source file path
     * @param fileOut - result file path
     */
    public Arch(String fileIn, String fileOut) {
        Arch.fileIn = new File(fileIn);
        Arch.fileOut = new File(fileOut);
        byteMap = new HashMap<>();
        bitAmount = 0;
        this.fillMap();
    }

    public String getSourceName() {
        return fileIn.getName();
    }

    public String getArchiveName() {
        return fileOut.getName();
    }

    /**
     * Filling byteMap.
     * Map to keep unique bytes.
     * key - fileIn's unique byte
     * value - associated bit sequence
     */
    private void fillMap() {

        try {
            FileInputStream reader = new FileInputStream(fileIn);
            byte uniqueByteAmount = 0;

            while (reader.available() > 0) {
                //choosing buffer's size to read
                int bufferSize = Math.min(reader.available(), CLUSTER_LENGTH);
                byte[] buffer = new byte[bufferSize];
                reader.read(buffer, 0, bufferSize);

                for (byte element : buffer) {
                    if (!byteMap.containsKey(element)) {
                        byteMap.put(element, (byte) ((++uniqueByteAmount) - 1));
                    }
                }
            }

            //Calculating required minimal bit's amount to write unique byte
            bitAmount = (byte) Math.ceil(Math.log(byteMap.size()) / Math.log(2));
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write map to file.
     *
     * @param fileOutputStream - the stream where to write
     */
    private void writeMap(BufferedOutputStream fileOutputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);

        //write in the first four bytes of result file - the size of the source file
        dataOutputStream.writeLong(fileIn.length());

        //write in the fifth byte of the result file the required minimum bit's amount to write unique byte of the source file
        dataOutputStream.writeInt(bitAmount);

        //write in the six'th byte of result file the size of byte map (elements's amount in map).
        dataOutputStream.writeInt((byteMap.size() * 2));

        //write map to result file. 1st byte - key, 2nd byte - value;
        for (Byte key : byteMap.keySet()) {
            fileOutputStream.write(key);
            fileOutputStream.write(byteMap.get(key));
        }
        fileOutputStream.flush();
    }

    /**
     * Process of archiving and then writing to file archiving's result
     *
     * @param fileInputStream  - stream to read from
     * @param fileOutputStream - stream to write in
     * @throws Exception if cant read/write, or cant get/set bit
     */
    private void writeArchFile(FileInputStream fileInputStream, BufferedOutputStream fileOutputStream) throws Exception {
        writeMap(fileOutputStream);

        byte archContainer = 0; // Bit Packing Container
        byte bitCounter = 0; //Count the amount of bits already packed to container
        int bytesAvailable; //store's the amount of more available bytes to read from input stream

        while ((bytesAvailable = fileInputStream.available()) > 0) {
            int bufferSize = Math.min(bytesAvailable, CLUSTER_LENGTH);
            byte[] buffer = new byte[bufferSize];

            fileInputStream.read(buffer, 0, bufferSize);

            for (int sourceByte = 0; sourceByte < bufferSize; sourceByte++) {
                for (byte bitIndex = 0; bitIndex < bitAmount; bitIndex++) {

                    if (bitCounter >= CONTAINERS_BITS_LENGTH) {
                        fileOutputStream.write(archContainer);
                        archContainer = 0;
                        bitCounter = 0;
                    }

                    archContainer = Bit.setBit(archContainer, Bit.getBit(byteMap.get(buffer[sourceByte]), bitIndex), bitCounter);
                    bitCounter++;
                }
            }

            fileOutputStream.flush();
        }
        fileOutputStream.write(archContainer);
        fileOutputStream.flush();
    }

    /**
     * Archiving source file to result file
     *
     * @return array long[]
     * long[0] - source file size in bytes
     * long[1] - result file size in bytes
     */
    public long[] archive() {

        //If result file exists, then rewrite it
        rewriteFile(fileOut);
        try {
            FileInputStream reader = new FileInputStream(fileIn);
            BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(fileOut, true));

            writeArchFile(reader, writer);
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new long[]{fileIn.length(), fileOut.length()};
    }

    /**
     * Create empty file
     */
    private void rewriteFile(File file) {
        try {
            if (file.exists())
                file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
