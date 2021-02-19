package com.shpp.p2p.cs.ilitvinov.assignment17.huffman.prehuffman.archiver;

import com.shpp.p2p.cs.ilitvinov.assignment17.huffman.prehuffman.huffmantree.HuffmanTree;
import com.shpp.p2p.cs.ilitvinov.assignment17.huffman.prehuffman.huffmantree.Node;

import java.io.*;
import java.util.HashMap;

/**
 * File Compression Using Huffman Algorithm
 */

public class Packer implements ArchiverConstants {

    /**
     * Source file
     */
    private final File fileIn;

    /**
     * Destination file
     */
    private final File fileOut;

    /**
     * @param fileIn  - file path to compress it.
     * @param fileOut - file path destination, where to save compressed file
     */
    public Packer(String fileIn, String fileOut) {
        this.fileIn = new File(fileIn);
        this.fileOut = new File(fileOut);
    }

    /**
     * Compress source file and wright result to destination file.
     *
     * @return time of working in milliseconds (type long).
     * @throws Exception when:
     *                   Source file - NOT FOUND!
     *                   Can't read/wright file!
     *                   Can't close input or output stream.
     */
    public long pack() throws Exception {
        long startTime = System.currentTimeMillis();

        FileInputStream inputStream = null;
        BufferedOutputStream outputStream = null;

        try {

            if (!fileInIsEmpty()) {

                HuffmanTree tree = new HuffmanTree(getUniqueBytesFrequency());
                //Node root = tree.buildTree();
                Node root = tree.buildTreeWithPriorQueue();
                HashMap<Byte, Byte[]> byteMap = tree.getMap(root);

                writeMap(tree.getSortedNodes());

                inputStream = (new FileInputStream(fileIn));
                outputStream = new BufferedOutputStream(new FileOutputStream(fileOut, true));

                writePack(inputStream, outputStream, byteMap);
            }

        } catch (FileNotFoundException fne) {
            throw new Exception(String.format("File: %s - NOT FOUND!\n", fileIn));

        } catch (IOException ioe) {
            System.err.println("Can't read/wright file!");

        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                throw new Exception(String.format("Can't close reading:\n%s\n", fileIn));
            }

            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                throw new Exception(String.format("Can't close writing:\n%s\n", fileOut));
            }
        }

        return System.currentTimeMillis() - startTime;
    }

    /**
     * Write table for decompression to destination file.
     * Table consists of:
     * first 2 bytes - map length as int value
     * map:
     * 1 byte - unique byte value from source
     * 2nd and 3rd - amount of this unique byte in source (frequency)
     * this two fields for each unique byte
     *
     * @param nodes - array of nodes (com.shpp.p2p.cs.ilitvinov.assignment15.huffmantree)
     * @throws IOException when can't write to destination file.
     */
    private void writeMap(Node[] nodes) throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileOut));
        dos.writeLong(fileIn.length());
        dos.writeInt(nodes.length);

        for (Node node : nodes) {
            dos.write(node.getName());
            dos.writeInt(node.getFrequency());
        }
        dos.close();
    }

    /**
     * Read source file, compress it and write it to destination file.
     *
     * @param inputStream  - FileInputStream stream.
     * @param outputStream - DataOutputStream stream.
     * @param byteMap      - Hashmap<Byte, Byte[]>, where:
     *                     key - unique byte name as Byte.
     *                     value - Byte[] consists of bit sequence from Huffman tree.
     * @throws IOException when can't read source / write to destination file.
     */
    private void writePack(FileInputStream inputStream, BufferedOutputStream outputStream, HashMap<Byte, Byte[]> byteMap) throws IOException {
        long fileSize = fileIn.length();
        byte container = 0;
        byte bitIndex = 0;
        int available;
        int sizeLimit = 0;

        while ((available = inputStream.available()) > 0 && sizeLimit < fileSize) {
            byte[] buffer = new byte[Math.min(CLUSTER_LENGTH, available)];

            inputStream.read(buffer, 0, buffer.length);
            int byteIndex = 0;

            while (byteIndex < buffer.length && sizeLimit < fileSize) {

                Byte[] bitSequence = byteMap.get(buffer[byteIndex++]);
                for (Byte bit : bitSequence) {
                    container = Bit.setBit(container, bit, bitIndex++);

                    if (bitIndex >= BYTE_LENGTH_IN_BITS) {
                        outputStream.write(container);
                        container = 0;
                        bitIndex = 0;
                    }
                }

                ++sizeLimit;
            }
        }
        outputStream.write(container);
        outputStream.flush();
    }

    /**
     * Read source file and store unique bytes from it. For each unique byte - count amount of them in source file.
     *
     * @return HashMap<Byte, Integer>, where:
     * key - unique byte.
     * value - amount of unique byte in source file.
     * @throws IOException when can't read source file
     */
    private HashMap<Byte, Integer> getUniqueBytesFrequency() throws IOException {
        HashMap<Byte, Integer> byteMap = new HashMap<>();
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(fileIn));

        int available;

        while ((available = inputStream.available()) > 0) {
            byte[] buffer = inputStream.readNBytes(Math.min(CLUSTER_LENGTH, available));

            for (byte element : buffer) {
                if (byteMap.containsKey(element)) {
                    byteMap.put(element, byteMap.get(element) + 1);
                } else {
                    byteMap.put(element, 1);
                }
            }
        }
        inputStream.close();
        return byteMap;
    }

    /**
     * If source file is empty create empty destination file and return true. If not - false.
     */
    private boolean fileInIsEmpty() throws Exception {
        if (fileIn.length() == 0) {
            try {
                if (fileOut.exists())
                    fileOut.delete();
                return fileOut.createNewFile();
            } catch (IOException e) {
                throw new Exception(String.format("Can't create file: %s", fileOut.getPath()));
            }
        }
        return false;
    }

}
