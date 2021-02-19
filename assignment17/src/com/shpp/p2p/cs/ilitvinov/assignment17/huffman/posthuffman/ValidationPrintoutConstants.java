package com.shpp.p2p.cs.ilitvinov.assignment17.huffman.posthuffman;

public interface ValidationPrintoutConstants {

    /**
     * Flag's index in args's array
     */
    int FLAG_INDEX = 0;

    /**
     * Source file index in args's array
     */
    int SOURCE_INDEX = 1;

    /**
     * Destination file index in args's array
     */
    int DEST_INDEX = 2;

    /**
     * Archiving flag pattern
     */
    String ARCH_FLAG = "-a";

    /**
     * Unpacking flag pattern
     */
    String UNPACK_FLAG = "-u";

    /**
     * Archive extension pattern
     */
    String ARCH_EXTENSION = ".par";

    /**
     * Archive extension pattern
     */
    String UNPACK_EXTENSION = ".uar";

    /**
     * Default file path to create archive from
     */
    String[] DEFAULT_PATH_IN = {
            "assets/siluets.png", "assets/128x128.jpg", "assets/a.txt",
            "assets/Beatles.txt", "assets/test.txt", "assets/test2.txt", "assets/Fibo.txt",
            "assets/test_4MB.txt", "assets/test.jpg", "assets/test_archive.par", "assets/test_unpack.txt"};

    /**
     * Default args's values
     */
    String[] FLAGS = {ARCH_FLAG, UNPACK_FLAG};
    String[] DEFAULT_ARGS = {FLAGS[0], DEFAULT_PATH_IN[4], DEFAULT_PATH_IN[9]};

    /**
     * Pattern to validate file's name and path spelling
     */
    String FILE_NAME_PATTERN = "(([a-zA-Z]\\:)?\\/)?(\\-*\\w+\\-*\\/)*[a-zA-Z0-9\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\=\\-\\{\\[\\}\\]\\|\\`\\~\\,\\\"\\:\\;\\'\\?\\>]*(\\.\\w+)*";

    /**
     * Pattern to validate archive's name and path spelling
     */
    String ARCHIVE_FILE_NAME_PATTERN = "(([a-zA-Z]\\:)?\\/)?(\\-*\\w+\\-*\\/)*[a-zA-Z0-9\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\=\\-\\{\\[\\}\\]\\|\\`\\~\\,\\\"\\:\\;\\'\\?\\>]*(\\.\\w+)*.par";
}
