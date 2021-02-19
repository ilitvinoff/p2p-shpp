package com.shpp.p2p.cs.ilitvinov.assignment17.huffman.prehuffman.archiver;

import com.shpp.p2p.cs.ilitvinov.assignment17.huffman.prehuffman.huffmantree.HuffmanTree;
import com.shpp.p2p.cs.ilitvinov.assignment17.huffman.prehuffman.huffmantree.Node;

import java.io.*;

/**
 * File Decompression Using Huffman Algorithm
 */

public class Unpacker implements ArchiverConstants {

    /**
     * Source file path
     */
    private final File fileIn;

    /**
     * Destination file path
     */
    private final File fileOut;

    private long destinationLimitSize = -1;

    /**
     * @param fileIn  - file path to decompress it.
     * @param fileOut - file path destination, where to save decompressed file
     */
    public Unpacker(String fileIn, String fileOut) {
        this.fileIn = new File(fileIn);
        this.fileOut = new File(fileOut);
    }

    /**
     * Decompress source file and wright result to destination file.
     *
     * @return time of working in milliseconds (type long).
     * @throws Exception when:
     *                   Source file - NOT FOUND!
     *                   Can't read/wright file!
     *                   Can't close input or output stream.
     */
    public long unpack() throws Exception {
        long startTime = System.currentTimeMillis();

        DataInputStream inputStream = null;
        BufferedOutputStream outputStream = null;

        try {
            if (!fileInIsEmpty()) {

                inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(fileIn)));
                outputStream = new BufferedOutputStream(new FileOutputStream(fileOut));

                Node[] nodesFromMap = readMap(inputStream);
                HuffmanTree huffman = new HuffmanTree(nodesFromMap);
                //Node root = huffman.buildTree();
                Node root = huffman.buildTreeWithPriorQueue();

                writeFile(inputStream, outputStream, root);
            }

        } catch (FileNotFoundException fne) {
            throw new Exception(String.format("File: %s - NOT FOUND!\n", fileIn));

        } catch (IOException ioe) {
            throw new Exception("Can't read/wright file!");

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
     * Read table for decompression to destination file.
     * Table consists of:
     * first 2 bytes of file - map's length as int value
     * map:
     * 1 byte - unique byte value from source
     * 2nd and 3rd - amount of this unique byte in source (frequency)
     * this two fields for each unique byte
     *
     * @param inputStream - DataInputStream stream
     * @throws IOException when can't  read from destination file.
     */
    private Node[] readMap(DataInputStream inputStream) throws IOException {
        destinationLimitSize = inputStream.readLong();
        int mapLength = inputStream.readInt();

        Node[] result = new Node[mapLength];

        for (int i = 0; i < result.length; i++) {
            result[i] = new Node(inputStream.readByte(), inputStream.readInt());
        }

        checkSum(result);
        return result;
    }

    /**
     * Read source file and write decompressed version to destination file.
     *
     * @param inputStream  - DataInputStream stream
     * @param outputStream - FileOutputStream stream
     * @param root         - Huffman's tree.
     * @throws IOException when can't read source / write to destination file.
     */
    private void writeFile(DataInputStream inputStream, BufferedOutputStream outputStream, Node root) throws IOException {
        Node node = root;

        if (destinationLimitSize < 0)
            throw new IOException(String.format("Invalid file to unpack. File: %s", fileOut.getPath()));

        for (int i = 0; i < destinationLimitSize; ) {
            int byteIndex = 0;
            byte[] buffer = new byte[CLUSTER_LENGTH];
            inputStream.read(buffer, 0, CLUSTER_LENGTH);

            while (byteIndex < buffer.length && i < destinationLimitSize) {
                for (int bitIndex = 0; bitIndex < BYTE_LENGTH_IN_BITS; bitIndex++) {

                    if (i >= destinationLimitSize)
                        break;

                    if (!node.hasChild()) {
                        outputStream.write(node.getName());
                        i++;
                        node = root;
                    }

                    byte bitValue = Bit.getBit(buffer[byteIndex], bitIndex);
                    if (bitValue == 1 && node.getLeft() != null) {
                        node = node.getLeft();
                    } else if (node.getRight() != null) {
                        node = node.getRight();
                    }

                }

                byteIndex++;
            }
            outputStream.flush();
        }
    }

    private boolean fileInIsEmpty() throws IOException {
        if (fileIn.length() == 0) {
            try {
                if (fileOut.exists())
                    fileOut.delete();
                return fileOut.createNewFile();
            } catch (IOException e) {
                throw new IOException(String.format("Can't create file: %s", fileOut.getPath()));
            }
        }
        return false;
    }

    private void checkSum(Node[] nodes) throws IOException {
        long checkSum = 0;
        for (Node node : nodes) {
            checkSum += node.getFrequency();
        }

        if (destinationLimitSize != checkSum || destinationLimitSize < 0) {
            throw new IOException(String.format("Unknown file: %s\n Can't unpack it.", fileIn.getPath()));
        }
    }

}
