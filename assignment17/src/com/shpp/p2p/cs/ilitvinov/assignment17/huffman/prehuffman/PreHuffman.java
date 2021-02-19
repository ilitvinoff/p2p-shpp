package com.shpp.p2p.cs.ilitvinov.assignment17.huffman.prehuffman;

import com.shpp.p2p.cs.ilitvinov.assignment17.huffman.Testing;
import com.shpp.p2p.cs.ilitvinov.assignment17.huffman.prehuffman.archiver.Packer;
import com.shpp.p2p.cs.ilitvinov.assignment17.huffman.prehuffman.archiver.Unpacker;

import java.io.IOException;

/**
 * Compress/decompress files using Huffman's algorithm.
 */

public class PreHuffman implements ValidationPrintoutConstants, Testing {

    /**
     * @param args - String[].
     *             <p>
     *             Args may have 3 elements:
     *             1st - flag, '-a' - to compress, '-u' - to decompress.
     *             2nd - source file path.
     *             3rd - destination file path.
     *             <p>
     *             May have 2 elements:
     *             1st - source file path.
     *             2nd - destination file path.
     *             <p>
     *             May have 1 element:
     *             1st - source path name, file destination will be the same, but name of file will be 'sourceName.par'
     *             <p>
     *             May have 0 elements:
     *             Will use default arguments: '-a test.txt test.txt.par'
     */
    public long proceed(String[] args) {
        long processTime = 0;
        String[] validatedArgs;
        ArgsManager argsManager = new ArgsManager();

        try {
            validatedArgs = argsManager.inputArgsValidation(args);
            //PrintOutResult printOutResult = new PrintOutResult(validatedArgs[SOURCE_INDEX], validatedArgs[DEST_INDEX]);

            if (validatedArgs[FLAG_INDEX].equals(ARCH_FLAG)) {
                Packer packer = new Packer(validatedArgs[SOURCE_INDEX], validatedArgs[DEST_INDEX]);
                processTime = packer.pack();

            } else {
                Unpacker unpacker = new Unpacker(validatedArgs[SOURCE_INDEX], validatedArgs[DEST_INDEX]);
                processTime = unpacker.unpack();
            }

            //printOutResult.printOutResult(processTime);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processTime;
    }


}
