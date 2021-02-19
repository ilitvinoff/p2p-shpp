package com.shpp.p2p.cs.ilitvinov.assignment14;

public class Bit {
    /**
     * @param operand  - int value, where searching bit's value
     * @param bitIndex - bit's position in operand. Possible value of index: 0-15;
     * @return int bit's value (0 or 1) as byte.
     * @throws Exception invalid parameters
     */
    public static byte getBit(byte operand, byte bitIndex) throws Exception {
        if (bitIndex < 0 || bitIndex > 7) throw new Exception("Invalid index's value: " + bitIndex);
        return (byte) (1 & operand >> bitIndex);
    }

    /**
     * @param operand  - int value where to set bit
     * @param bitValue - integer 0 or 1
     * @param bitIndex - bit's position in parameter operand. Possible value of index: 0-15;
     * @return result of operation as byte
     * @throws Exception invalid parameters
     */
    public static byte setBit(byte operand, byte bitValue, byte bitIndex) throws Exception {
        if (bitValue < 0 || bitValue > 1) {
            throw new Exception("Invalid bit's value parameter to set bit: " + bitValue);
        }
        if (bitIndex < 0 || bitIndex > 7) {
            throw new Exception("Invalid index's value: " + bitIndex);
        }
        if (bitValue==0){
            return (byte) (operand & (~(1 << bitIndex)));
        }
        return (byte) (operand | (1 << bitIndex));
    }
}
