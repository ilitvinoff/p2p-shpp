package com.shpp.p2p.cs.ilitvinov.assignment14;

public class Assignment14 {

    /**
     * Archiving flag pattern
     */
    private static final String ARCH_FLAG = "-a";

    /**
     * Unpacking flag pattern
     */
    private static final String UNPACK_FLAG = "-u";

    /**
     * Archive extension pattern
     */
    private static final String ARCH_EXTENSION = ".par";

    /**
     * Default file path to create archive from
     */
    private static final String[] DEFAULT_PATH_IN = {
            "assets/siluets.png", "assets/128x128.jpg", "assets/a.txt",
            "assets/Beatles.txt", "assets/test.txt", "assets/test2.txt",
            "assets/test_archive.par", "assets/test_unpack.txt"};

    /**
     * Default args's values
     */
    private static final String[] FLAGS = {ARCH_FLAG, UNPACK_FLAG};
    private static final String[] DEFAULT_ARGS = {FLAGS[0], DEFAULT_PATH_IN[4], DEFAULT_PATH_IN[6]};

    /**
     * Pattern to validate file's name and path spelling
     */
    private static final String FILE_NAME_PATTERN = "(\\w+/)*\\w+(\\.\\w+)*";

    /**
     * Pattern to validate archive's name and path spelling
     */
    private static final String ARCH_FILE_NAME_PATTERN = "(\\w+/)*\\w+(\\.\\w+)*\\.par";

    /**
     * Byte's amount in 1mB
     */
    private static final double MB_LENGTH = 1024.0;

    /**
     * Amount of milliseconds in 1 second
     */
    private static final double SEC_LENGTH = 1000.0;

    private String fileInName = "";
    private String fileOutName = "";
    private double efficiency = -1;

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Assignment14 main = new Assignment14();
        String[] validatedArgs = main.inputArgsValidation(args);
        long[] result = main.process(validatedArgs);
        long end = System.currentTimeMillis();

        main.printResult(start, end, result);
    }

    /**
     * Printout result of process
     *
     * @param startTime  - process's starting time
     * @param endTime    - process's end time
     * @param resultSize - array consists of 2 elements:
     *                   [ fileIn size, fileOut size ]
     */
    private void printResult(long startTime, long endTime, long[] resultSize) {
        System.out.printf("\n%s size: %.3fmB\n", fileInName, resultSize[0] / MB_LENGTH);
        System.out.printf("\nresult:\n%s size: %.3fmB\n", fileOutName, resultSize[1] / MB_LENGTH);
        if (efficiency > -1) {
            System.out.printf("Compression efficiency: %.2f%%\n", efficiency);
        }
        System.out.printf("\nprocess time: %.3fsec\n", (endTime - startTime) / SEC_LENGTH);
    }

    /**
     * Processing archiving/unpacking.
     *
     * @param args - args's array, consists of 3 elements:
     *             [ flag, fileIn path, fileOut path]
     *             flag may be:
     *             -a - to create archive from fileIn and save it to fileOut
     *             -u - to unpack archive from fileIn to fileOut
     */
    private long[] process(String[] args) {
        if (args[0].equals(ARCH_FLAG)) {
            Arch arch = new Arch(args[1], args[2]);
            fileInName = arch.getSourceName();
            fileOutName = arch.getArchiveName();
            long[] temp = arch.archive();
            efficiency = -(temp[1] - temp[0]) / 100.0;
            return temp;
        }

        Unpack unpack = new Unpack(args[1], args[2]);
        fileInName = unpack.getArchiveName();
        fileOutName = unpack.getUnpackFileName();
        return unpack.unpack();
    }

    /**
     * Validation of args's spelling
     *
     * @param args - array of args to validate
     * @return args's String[] array, which consists of:
     * [ flag, fileIn path, fileOut path]
     * flag may be:
     * -a - to create archive from fileIn and save it to fileOut
     * -u - to unpack archive from fileIn to fileOut
     * @throws Exception when validation failed
     */
    private String[] inputArgsValidation(String[] args) throws Exception {
        if (args.length > 3) {
            throw new Exception("To much arguments!");
        }

        if (args.length == 3) {
            fileNameValidation(args, 1);

            if (args[0].equals(ARCH_FLAG)) {
                return args;
            }
            if (args[0].equals(UNPACK_FLAG)) {
                return args;
            } else {
                throw new Exception("Incorrect flag!");
            }
        }

        if (args.length == 2) {
            fileNameValidation(args, 0);

            if (args[0].matches(ARCH_FILE_NAME_PATTERN)) {
                return new String[]{UNPACK_FLAG, args[0], args[1]};
            } else {
                return new String[]{ARCH_FLAG, args[0], args[1]};
            }
        }

        if (args.length == 1) {

            if (args[0].matches(ARCH_FILE_NAME_PATTERN)) {
                return new String[]{UNPACK_FLAG, args[0], args[0].substring(0, args[0].length() - 4)};

            } else if (args[0].matches(FILE_NAME_PATTERN)) {
                return new String[]{ARCH_FLAG, args[0], args[0] + ARCH_EXTENSION};
            }

        }

        return DEFAULT_ARGS;
    }

    /**
     * Validate file's name and path spelling.
     *
     * @param args   - array with args to validate
     * @param offset - index to start validate from
     * @throws Exception when validation failed
     */
    private void fileNameValidation(String[] args, int offset) throws Exception {
        for (int i = offset; i < args.length; i++) {
            if (!args[i].matches(FILE_NAME_PATTERN))
                throw new Exception("Incorrect files's names!");
        }
    }
}
