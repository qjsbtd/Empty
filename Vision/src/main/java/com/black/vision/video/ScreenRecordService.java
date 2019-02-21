package com.black.vision.video;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.IBinder;

import com.black.vision.util.VisionUtil;

import java.io.File;

/**
 * Description: Screen record service.
 * Date：19-2-21-下午3:05
 * Author: black
 */
public class ScreenRecordService extends Service {
    private static final String PERMISSION_AUDIO = Manifest.permission.RECORD_AUDIO;
    private static final String PERMISSION_FILE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private MediaRecorder mMediaRecorder;
    private String mRecordFilePath;
    private int mRecordWidth;
    private int mRecordHeight;
    private int mScreenDensity;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopRecord();
        super.onDestroy();
    }

    private void init(Intent intent) {
        // 权限
        if (!hasPermission()) {
            return;
        }

        // 保存路径
        mRecordFilePath = VisionUtil.getVideoDir() + File.separator + System.currentTimeMillis() + ".mp4";
        // 屏幕宽高
        mRecordWidth = intent.getIntExtra("recordWidth", -1);
        mRecordHeight = intent.getIntExtra("recordHeight", -1);
        mScreenDensity = intent.getIntExtra("screenDensity", -1);

        initMediaRecorder();
        initMediaProjection(intent);
        statRecord();
    }

    private boolean hasPermission() {
        return VisionUtil.checkPermission(PERMISSION_AUDIO, this) && VisionUtil.checkPermission(PERMISSION_FILE, this);
    }

    private void initMediaRecorder() {
        try {
            // 配置顺序不能变
            mMediaRecorder = new MediaRecorder();
            //设置来源
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            //设置输出
            mMediaRecorder.setOutputFile(mRecordFilePath);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            //设置编码
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mMediaRecorder.setVideoEncodingBitRate(mRecordWidth * mRecordHeight * 5);
            mMediaRecorder.setVideoSize(mRecordWidth, mRecordHeight);
            mMediaRecorder.setVideoFrameRate(60);
            // 准备
            mMediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMediaProjection(Intent intent) {
        try {
            int resultCode = intent.getIntExtra("resultCode", -1);
            Intent resultData = intent.getParcelableExtra("resultData");
            MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            MediaProjection mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, resultData);
            mediaProjection.createVirtualDisplay("mediaProjection", mRecordWidth, mRecordHeight, mScreenDensity,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mMediaRecorder.getSurface(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void statRecord() {
        try {
            mMediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopRecord() {
        try {
            mMediaRecorder.stop();
            mMediaRecorder.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
