package com.shpp.p2p.cs.ilitvinov.assignment14;

import java.io.*;
import java.util.HashMap;

public class Unpack {

    private static final int CONTAINERS_BITS_LENGTH = 8;

    /**
     * Length of byte's cluster to read and write
     */
    private static final int CLUSTER_LENGTH = 65536;

    /**
     * Bit's amount value to restore one unique byte
     */
    private static int bitAmount;

    /**The size of the source file in bytes*/
    private static long sourceSize;

    /**File to unpack*/
    private static File fileIn;

    /**File where to unpack*/
    private static File fileOut;

    /** Map to keep unique bytes.
     * key - associated bit sequence to unique byte of the source file
     * value - source file unique byte*/
    private static HashMap<Byte, Byte> byteMap;

    /**@param fileIn  - archive file's path
     * @param fileOut - destination file path*/
    public Unpack(String fileIn, String fileOut) {
        bitAmount = 0;
        byteMap = new HashMap<>();
        Unpack.fileIn = new File(fileIn);
        Unpack.fileOut = new File(fileOut);
    }

    public String getArchiveName() {
        return fileIn.getName();
    }

    public String getUnpackFileName() {
        return fileOut.getName();
    }

    /**Restore the map from packed file
     * @param fileInputStream - stream read from
     * @throws IOException if cant read data*/
    private void fillMap(FileInputStream fileInputStream) throws IOException {
        DataInputStream inputStream = new DataInputStream(fileInputStream);
        //read value of source file size in bytes
        sourceSize = inputStream.readLong();

        //read bit's amount value to restore one unique byte
        bitAmount = inputStream.readInt();
        int mapOffset = inputStream.readInt();


        byte[] buffer = new byte[mapOffset];
        fileInputStream.read(buffer, 0, buffer.length);

        //filling map
        for (int i = 0; i < buffer.length; i++) {
            byte value = buffer[i];
            byte key = buffer[++i];
            byteMap.put(key, value);
        }
    }

    /**Unpack archive.
     * @return array long[]
     * long[0] - archive's size
     * long[1] - unpacked file's size*/
    public long[] unpack() {

        //if destination file to unpack in - exists, then recreate it
        recreateFile(fileOut);
        try {
            FileInputStream fileInputStream = new FileInputStream(fileIn);
            FileOutputStream fileOutputStream = new FileOutputStream(fileOut, true);
            //read map
            fillMap(fileInputStream);

            byte keyContainer = 0; //where to write in the value of key to unpack
            byte bitCounter = 0;  //count amount of written bits to keyContainer
            int bytesAvailable;  //store's the amount of more available bytes to read from input stream
            long bytesCounter = 0; //unpacked bytes counter

            while ((bytesAvailable = fileInputStream.available()) > 0) {
                int bufferSize = Math.min(bytesAvailable, CLUSTER_LENGTH);
                byte[] buffer = new byte[bufferSize];
                fileInputStream.read(buffer, 0, bufferSize);

                for (int archByte = 0; archByte < bufferSize; archByte++) {
                    for (byte bitIndex = 0; bitIndex < CONTAINERS_BITS_LENGTH; bitIndex++) {

                        if (bitCounter >= bitAmount) {
                            fileOutputStream.write(byteMap.get(keyContainer));
                            keyContainer = 0;
                            bitCounter = 0;
                            ++bytesCounter;
                        }
                        //stop if the number of bytes unpacked has reached the number of bytes in the source
                        if (bytesCounter> sourceSize){
                            break;
                        }

                        keyContainer = Bit.setBit(keyContainer, Bit.getBit(buffer[archByte], bitIndex), bitCounter);
                        bitCounter++;
                    }
                }
                fileOutputStream.flush();
            }

            fileInputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new long[]{fileIn.length(),fileOut.length()};
    }

    /**Create empty file*/
    private void recreateFile(File file) {
        try {
            if (file.exists())
                file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
