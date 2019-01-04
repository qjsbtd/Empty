package com.black.vision.util;

import android.os.Environment;

import java.io.File;

/**
 * Description: File operation.
 * Date：19-1-3-下午1:59
 * Author: black
 */
public class FileUtil {

    private static final String VISION_NAME = "Vision";
    private static final String ROOT_PATH = getFilePath(Environment.getExternalStorageDirectory()) + File.separator + VISION_NAME;
    private static final String CRASH_PATH = ROOT_PATH + File.separator + "Crash";
    private static final String PIC_PATH = ROOT_PATH + File.separator + "Pic";

    private static String getFilePath(File file) {
        if (Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment.getExternalStorageState(file))) {
            return file.getAbsolutePath();
        }
        return "";
    }
}
