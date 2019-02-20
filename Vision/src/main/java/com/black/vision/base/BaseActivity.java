package com.black.vision.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.black.vision.util.LogUtil;

/**
 * Description: Base activity.
 * Date：19-1-2-下午4:29
 * Author: black
 */
public abstract class BaseActivity extends Activity {
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getWindow().requestFeature(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }catch (Exception e){
            e.printStackTrace();
        }
        printLogI("onCreate...");
        mContext = this;
        setContentView(getContentLayoutId());
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        printLogI("onCreate...");
    }

    @Override
    protected void onPause() {
        printLogI("onPause...");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unInit();
        printLogI("onDestroy...");
        super.onDestroy();
    }

    protected abstract int getContentLayoutId();

    protected abstract void init();

    protected abstract void unInit();

    private String getLogTag() {
        return getClass().getSimpleName();
    }

    protected void printLogD(String logContent) {
        LogUtil.d(getLogTag(), logContent);
    }

    protected void printLogI(String logContent) {
        LogUtil.i(getLogTag(), logContent);
    }

    protected void printLogE(String logContent) {
        LogUtil.e(getLogTag(), logContent);
    }

    protected void printLogE(Throwable throwable) {
        LogUtil.e(getLogTag(), throwable);
    }

    protected void hideBottomUIMenu() {
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }
}
