package com.shpp.p2p.cs.ilitvinov.assignment17.huffman.posthuffman.archiver;

public class Bit {
    /**
     * @param operand  - byte value, where searching bit's value
     * @param bitIndex - bit's position in operand. Possible value of index: 0-15;
     * @return int bit's value (0 or 1) as byte.
     */
    public static byte getBit(byte operand, int bitIndex) {
        return (byte) (1 & operand >> bitIndex);
    }

    /**
     * @param operand  - byte value where to set bit
     * @param bitValue - integer 0 or 1
     * @param bitIndex - bit's position in parameter operand. Possible value of index: 0-15;
     * @return result of operation as byte
     */
    public static byte setBit(byte operand, byte bitValue, byte bitIndex) {
        if (bitValue == 0) {
            return (byte) (operand & (~(1 << bitIndex)));
        }
        return (byte) (operand | (1 << bitIndex));
    }
}
