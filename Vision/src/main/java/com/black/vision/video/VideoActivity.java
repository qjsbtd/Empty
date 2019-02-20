package com.black.vision.video;

import android.app.PictureInPictureParams;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Rational;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.black.vision.R;
import com.black.vision.base.BaseActivity;
import com.black.vision.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: Video activity.
 * Date：19-1-8-下午1:37
 * Author: black
 */
public class VideoActivity extends BaseActivity {

    private View mToolView;
    private SurfaceHolder mSHVideo;
    private MediaPlayer mMediaPlayer;
    private List<String> mVideoUrlList;
    private String mCurrentVideoUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void init() {
        hideBottomUIMenu();
        SurfaceView svVideo = findViewById(R.id.sv_video);
        mToolView = findViewById(R.id.rl_tool);

        mVideoUrlList = new ArrayList<>();
        getVideoUrlList();
        printLogE("init...mVideoUrlList==" + mVideoUrlList);
        if (mVideoUrlList.isEmpty()) {
            return;
        }
        svVideo.getHolder().addCallback(callback);
    }

    @Override
    protected void unInit() {
        printLogE("unInit...mMediaPlayer==" + mMediaPlayer);
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        printLogE("onPictureInPictureModeChanged...isInPictureInPictureMode==" + isInPictureInPictureMode);
        printLogE("onPictureInPictureModeChanged...newConfig==" + newConfig);
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);

    }

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            printLogE("surfaceCreated...holder==" + holder);
            printLogE("surfaceCreated...mCurrentVideoUrl==" + mCurrentVideoUrl);
            mSHVideo = holder;
            if (mCurrentVideoUrl == null || mMediaPlayer == null) {
                playVideo(mVideoUrlList.get(0));
            } else {
                mMediaPlayer.setDisplay(mSHVideo);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            printLogE("surfaceChanged...holder==" + holder);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            printLogE("surfaceDestroyed...holder==" + holder);
            printLogE("surfaceDestroyed...mCurrentVideoUrl==" + mCurrentVideoUrl);
        }
    };

    private void getVideoUrlList() {
        mVideoUrlList = new ArrayList<>();
        File blackDir = new File(FileUtil.getESPath() + File.separator + "Black");
        File[] videoUrlArray = blackDir.listFiles();
        if (videoUrlArray != null && videoUrlArray.length > 0) {
            for (File videoUrl : videoUrlArray) {
                mVideoUrlList.add(videoUrl.getAbsolutePath());
            }
        }
    }

    private int getCurrentIndex() {
        int index = -1;
        printLogE("getCurrentIndex...mCurrentVideoUrl==" + mCurrentVideoUrl);
        printLogE("getCurrentIndex...mVideoUrlList==" + mVideoUrlList);
        if (mCurrentVideoUrl != null && !mVideoUrlList.isEmpty()) {
            index = mVideoUrlList.indexOf(mCurrentVideoUrl);
        }
        return index;
    }

    private void playVideo(String videoUrl) {
        printLogE("playVideo...videoUrl==" + videoUrl);
        printLogE("playVideo...mCurrentVideoUrl==" + mCurrentVideoUrl);
        if (mMediaPlayer == null) {
            printLogE("playVideo...0...videoUrl==" + videoUrl);
            play(videoUrl);
        } else if (mMediaPlayer.isPlaying()) {
            printLogE("playVideo...1...videoUrl==" + videoUrl);
            if (!mCurrentVideoUrl.equalsIgnoreCase(videoUrl)) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                play(videoUrl);
            }
        } else {
            printLogE("playVideo...2...videoUrl==" + videoUrl);
            mMediaPlayer.start();
        }
    }

    private void play(String videoUrl) {
        printLogE("play...videoUrl==" + videoUrl);
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(this, Uri.parse(videoUrl));
            mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                    mMediaPlayer.setLooping(true);
                    mMediaPlayer.setDisplay(mSHVideo);
                }
            });
            mCurrentVideoUrl = videoUrl;
        } catch (Exception e) {
            mMediaPlayer = null;
            e.printStackTrace();
        }
    }

    public void onNextClick(View view) {
        int index = getCurrentIndex();
        if (index != -1) {
            String nextUrl = mVideoUrlList.get((index + 1) % mVideoUrlList.size());
            printLogE("onNextClick...nextUrl==" + nextUrl);
            playVideo(nextUrl);
        }

    }

    public void onPreviousClick(View view) {
        int index = getCurrentIndex();
        if (index != -1) {
            String previousUrl = mVideoUrlList.get((index - 1 + mVideoUrlList.size()) % mVideoUrlList.size());
            printLogE("onPreviousClick...nextUrl==" + previousUrl);
            playVideo(previousUrl);
        }
    }

    public void onOverClick(View view) {
        printLogE("onOverClick...view==" + view);
        PictureInPictureParams.Builder builder = new PictureInPictureParams.Builder();
        // 下面的10/5=2，表示画中画窗口的宽度是高度的两倍
        Rational aspectRatio = new Rational(10, 5);
        // 设置画中画窗口的宽高比例
        builder.setAspectRatio(aspectRatio);
        enterPictureInPictureMode(builder.build());
    }

    public void onVideoClick(View view) {
        printLogE("onVideoClick...view==" + view);
        mToolView.setVisibility(mToolView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
}
