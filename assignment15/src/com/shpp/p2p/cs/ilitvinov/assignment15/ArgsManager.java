package com.shpp.p2p.cs.ilitvinov.assignment15;

public class ArgsManager implements ValidationPrintoutConstants {

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
    public String[] inputArgsValidation(String[] args) throws Exception {
        if (args.length > 3) {
            throw new Exception("To much arguments!");
        }

        if (args.length == 3) {

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

            if (args[0].matches(ARCHIVE_FILE_NAME_PATTERN) && args[1].matches(FILE_NAME_PATTERN)) {
                return new String[]{UNPACK_FLAG, args[0], args[1]};
            } else if (args[0].matches(FILE_NAME_PATTERN) && args[1].matches(FILE_NAME_PATTERN)) {
                return new String[]{ARCH_FLAG, args[0], args[1]};
            } else {
                throw new Exception("Incorrect file path!");
            }
        }

        if (args.length == 1) {

            if (args[0].matches(ARCHIVE_FILE_NAME_PATTERN)) {
                return new String[]{UNPACK_FLAG, args[0], args[0].substring(0, args[0].length() - ARCH_EXTENSION.length()) + UNPACK_EXTENSION};
            } else if (args[0].matches(FILE_NAME_PATTERN)) {
                return new String[]{ARCH_FLAG, args[0], args[0] + ARCH_EXTENSION};
            } else {
                throw new Exception("Incorrect file path!");
            }
        }

        return DEFAULT_ARGS;
    }

}
