package com.black.vision.util;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Description: File operation.
 * Date：19-1-3-下午1:59
 * Author: black
 */
public class FileUtil {

    private static final String VISION_NAME = "Vision";
    private static final String ROOT_PATH = getDirPath(Environment.getExternalStorageDirectory()) + File.separator + VISION_NAME;
    private static final String CRASH_PATH = ROOT_PATH + File.separator + "Crash" + File.separator;
    private static final String PIC_PATH = ROOT_PATH + File.separator + "Pic" + File.separator;

    private static void printLogE(String logContent) {
        LogUtil.e(FileUtil.class.getSimpleName(), logContent);
    }

    private static void printLogE(Throwable throwable) {
        LogUtil.e(FileUtil.class.getSimpleName(), throwable);
    }

    private static boolean checkFilePath(File file) {
        boolean parentExists = Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment.getExternalStorageState(file)) && checkDirPath(file.getParentFile());
        try {
            return parentExists && (file.exists() || file.createNewFile());
        } catch (IOException e) {
            printLogE(e);
        }
        return false;
    }

    private static String getFilePath(File file) {
        return checkFilePath(file) ? file.getAbsolutePath() : "";
    }

    private static boolean checkDirPath(File file) {
        return Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment.getExternalStorageState(file)) && (file.exists() || file.mkdirs());
    }

    private static String getDirPath(File file) {
        return checkDirPath(file) ? file.getAbsolutePath() : "";
    }

    public static String getRootPath() {
        return getDirPath(new File(ROOT_PATH));
    }

    public static String getCrashPath() {
        return getDirPath(new File(CRASH_PATH));
    }

    public static String getPicPath() {
        return getDirPath(new File(PIC_PATH));
    }
}
