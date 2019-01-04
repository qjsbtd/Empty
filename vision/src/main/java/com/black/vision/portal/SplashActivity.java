package com.black.vision.portal;

import android.content.Intent;
import android.os.Handler;

import com.black.vision.R;
import com.black.vision.base.BaseActivity;


/**
 * Description: Splash activity. Main and launcher.
 * Date：19-1-2-下午5:23
 * Author: black
 */
public class SplashActivity extends BaseActivity {

    private static final long DELAY_MILLIS = 3 * 1000;
    private Handler mHandler = new Handler();

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_spalsh;
    }

    @Override
    protected void init() {
        if (!isTaskRoot()) {
            finish();
            return;
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                skip();
            }
        }, DELAY_MILLIS);
    }

    @Override
    protected void unInit() {
        mHandler.removeCallbacksAndMessages(null);
    }

    private void skip() {
        startActivity(new Intent(this, DispatchActivity.class));
        finish();
    }
}
