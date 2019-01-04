package com.black.vision.app;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Description: Vision application.
 * Date：19-1-2-下午4:26
 * Author: black
 */
public class VisionApp extends Application {

    private static WeakReference<Context> mContextWR;

    @Override
    public void onCreate() {
        super.onCreate();
        mContextWR = new WeakReference<Context>(this);
    }

    public static Context getContext() {
        return mContextWR.get();
    }
}
