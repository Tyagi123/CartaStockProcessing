package com.carta.batch.common;

import java.nio.file.FileSystems;

/**
 * Interface to manage constants
 */
public interface Constants {
    public static String DATE_FORMAT_YYYY_MM_DD = "YYYY-MM-DD";

    /**
     * Batch load process
     */
    public static Integer BATCH_LOAD = 10000;

    /**
     * Constant to result csv file
     */
    public static String RESULT_PATH = String.valueOf(FileSystems.getDefault().getPath("").toAbsolutePath())+
            "/src/main/resources/result.csv";

    public static Integer DEFAULT_FRACTION = 2;
}
