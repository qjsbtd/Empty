package com.black.vision.util;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.black.vision.R;
import com.black.vision.app.VisionApp;

/**
 * Description: Toast operation.
 * Date：19-1-3-下午2:38
 * Author: black
 */
public class ToastUtil {
    private static Toast mToast;

    public static void show(int contentResId) {
        if (VisionApp.getContext() == null) {
            return;
        }
        show(VisionApp.getContext().getString(contentResId));
    }

    public static void show(final String content) {
        if (VisionApp.getContext() == null) {
            return;
        }
        if (Looper.getMainLooper().isCurrentThread()) {
            showInner(content);
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    showInner(content);
                }
            });
        }
    }

    private static void showInner(String content) {
        if (mToast == null) {
            mToast = new Toast(VisionApp.getContext());
            mToast.setDuration(Toast.LENGTH_SHORT);
            View view = View.inflate(VisionApp.getContext(), R.layout.toast_lay, null);
            mToast.setView(view);
        }
        mToast.setText(content);
        mToast.show();
    }
}
