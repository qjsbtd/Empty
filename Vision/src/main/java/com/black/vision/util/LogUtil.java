package com.black.vision.util;

import android.text.TextUtils;
import android.util.Log;

import com.black.vision.BuildConfig;

/**
 * Description: Log operation.
 * Date：19-1-2-下午4:29
 * Author: black
 */
public class LogUtil {

    public static void d(String logTag, String logContent) {
        printLog(Log.DEBUG, logTag, logContent);
    }

    public static void i(String logTag, String logContent) {
        printLog(Log.INFO, logTag, logContent);
    }

    public static void e(String logTag, String logContent) {
        printLog(Log.ERROR, logTag, logContent);
    }

    public static void e(String logTag, Throwable throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace();
        }
        printLog(Log.ERROR, logTag, throwable.getLocalizedMessage());
    }

    private static void printLog(int logLevel, String logTag, String logContent) {
        if (BuildConfig.DEBUG || logLevel >= ConfigUtil.LOG_LIMIT_LEVEL) {
            if (!TextUtils.isEmpty(logTag) && !TextUtils.isEmpty(logContent)) {
                Log.println(logLevel, logTag, logContent);
            }
        }
    }
}
