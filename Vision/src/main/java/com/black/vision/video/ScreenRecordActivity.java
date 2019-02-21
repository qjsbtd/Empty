package com.black.vision.video;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.view.View;
import android.widget.TextView;

import com.black.vision.R;
import com.black.vision.base.BaseActivity;
import com.black.vision.util.VisionUtil;

/**
 * Description: Screen record activity.
 * Date：19-2-20-下午5:22
 * Author: black
 */
public class ScreenRecordActivity extends BaseActivity {

    private TextView mTVScreenRecord;
    private boolean mIsRecording;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_screen_record;
    }

    @Override
    protected void init() {
        printLogE("init...");
        mTVScreenRecord = findViewById(R.id.tv_screen_record);
    }

    @Override
    protected void unInit() {
        printLogE("unInit...");
    }

    public void onScreenRecordClick(View view) {
        printLogE("onScreenRecordClick...");
        if (mIsRecording) {
            stopRecord();
        } else {
            // Audio and file permission
            if (!VisionUtil.checkPermission(Manifest.permission.RECORD_AUDIO, this) ||
                    !VisionUtil.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, this)) {
                return;
            }
            startRecord();
        }
        mIsRecording = !mIsRecording;
        mTVScreenRecord.setText(mIsRecording ? R.string.stop_record : R.string.start_record);
    }

    private void startRecord() {
        printLogE("startRecord...");
        // Screen record permission.
        MediaProjectionManager mediaProjectionManager =
                (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        Intent permissionIntent = mediaProjectionManager.createScreenCaptureIntent();
        startActivityForResult(permissionIntent, 1000);
    }

    private void stopRecord() {
        printLogE("stopRecord...");
        Intent intent = new Intent(mContext, ScreenRecordService.class);
        stopService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        printLogE("onActivityResult...");
        printLogE("onActivityResult...requestCode==" + requestCode);
        printLogE("onActivityResult...resultCode==" + resultCode);
        printLogE("onActivityResult...data==" + data);
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                start(resultCode, data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void start(int resultCode, Intent data) {
        printLogE("start...");
        printLogE("start...resultCode==" + resultCode);
        printLogE("start...data==" + data);
        Intent intent = new Intent(mContext, ScreenRecordService.class);
        intent.putExtra("resultCode", resultCode);
        intent.putExtra("resultData", data);
        intent.putExtra("recordWidth", VisionUtil.getScreenWidth(mContext));
        intent.putExtra("recordHeight", VisionUtil.getScreenHeight(mContext));
        intent.putExtra("screenDensity", VisionUtil.getScreenDensity(mContext));
        startService(intent);
    }
}
