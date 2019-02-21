package com.black.vision.portal;

import android.content.Intent;

import com.black.vision.R;
import com.black.vision.base.BaseActivity;
import com.black.vision.text.TextActivity;
import com.black.vision.video.ScreenRecordActivity;
import com.black.vision.video.VideoActivity;


/**
 * Description: Dispatch activity.vision app dispatch portal.
 * Date：19-1-3-上午10:24
 * Author: black
 */
public class DispatchActivity extends BaseActivity {
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_dispach;
    }

    @Override
    protected void init() {
        skip();
    }

    @Override
    protected void unInit() {

    }

    private void skip() {
        Intent intent = new Intent();
        intent.setClass(this, TextActivity.class);
        intent.setClass(this, VideoActivity.class);
        intent.setClass(this, ScreenRecordActivity.class);
        startActivity(intent);
        finish();
    }
}
