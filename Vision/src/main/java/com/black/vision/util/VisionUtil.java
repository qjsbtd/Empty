package com.black.vision.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Description: The app common operation.
 * Date：19-1-2-下午5:00
 * Author: black
 */
public class VisionUtil {

    private static final String APP_NAME = "Vision";
    private static final int DEFAULT_PERMISSION_REQUEST_CODE = 10000;

    /**
     * Get screen width.
     *
     * @param context context
     */
    public static int getScreenWidth(Context context) {
        int screenWidth = 1;
        try {
            Point outSize = new Point();
            ((Activity) context).getWindowManager().getDefaultDisplay().getSize(outSize);
            screenWidth = outSize.x;
        } catch (Exception e) {
            printLogE(e);
        }
        return screenWidth;
    }

    /**
     * Get screen height.
     *
     * @param context context
     */
    public static int getScreenHeight(Context context) {
        int screenHeight = 1;
        try {
            Point outSize = new Point();
            ((Activity) context).getWindowManager().getDefaultDisplay().getSize(outSize);
            screenHeight = outSize.y;
        } catch (Exception e) {
            printLogE(e);
        }
        return screenHeight;
    }

    /**
     * Get screen height.
     *
     * @param context context
     */
    public static int getScreenDensity(Context context) {
        int densityDpi = 1;
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            densityDpi = displayMetrics.densityDpi;
        } catch (Exception e) {
            printLogE(e);
        }
        return densityDpi;
    }

    /**
     * Remeasure banner ad image view to adapt to the screen by width.
     *
     * @param imageView imageView
     */
    public static void remeasureBannerAD(ImageView imageView) {
        try {
            int destWidth = getScreenWidth(imageView.getContext()) - imageView.getPaddingLeft() - imageView.getPaddingRight();
            remeasureBannerAD(imageView, destWidth);
        } catch (Exception e) {
            printLogE(e);
        }
    }

    /**
     * Remeasure banner ad image view to adapt to the screen by width.
     *
     * @param imageView imageView
     * @param destWidth destWidth
     */
    public static void remeasureBannerAD(ImageView imageView, int destWidth) {
        try {
            Drawable drawable = imageView.getDrawable();
            float scale = destWidth * 1.0F / drawable.getIntrinsicWidth();
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = Math.round(drawable.getIntrinsicWidth() * scale);
            layoutParams.height = Math.round(drawable.getIntrinsicHeight() * scale);
            imageView.setLayoutParams(layoutParams);
            imageView.measure(layoutParams.width, layoutParams.height);
        } catch (Exception e) {
            printLogE(e);
        }
    }

    /**
     * print exception log.
     */
    private static void printLogE(Exception e) {
        LogUtil.e(VisionUtil.class.getSimpleName(), e);
    }

    private static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                printLogE(e);
            }
        }
    }

    public static Bitmap convertViewToBitmap(View view) {
        try {
            Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            canvas.save();
            return bitmap;
        } catch (Exception e) {
            printLogE(e);
        }
        return null;
    }

    public static boolean saveBitmapToFile(Bitmap bitmap, File file) {
        boolean saveResult = false;
        FileOutputStream fileOutputStream = null;
        try {
            boolean createResult = file.exists() || file.createNewFile();
            if (createResult) {
                fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                bitmap.recycle();
                saveResult = true;
            }
        } catch (Exception e) {
            printLogE(e);
        } finally {
            closeStream(fileOutputStream);
        }
        return saveResult;
    }

    public static File getPictureDir() {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (file == null || !file.exists()) {
            file = Environment.getExternalStorageDirectory();
        }
        if (file == null || !file.exists()) {
            return null;
        }
        File pictureDir = new File(file, APP_NAME);
        if (pictureDir.exists() || pictureDir.mkdirs()) {
            return pictureDir;
        }
        return null;
    }

    public static File getVideoDir() {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        if (file == null || !file.exists()) {
            file = Environment.getExternalStorageDirectory();
        }
        if (file == null || !file.exists()) {
            return null;
        }
        File pictureDir = new File(file, APP_NAME);
        if (pictureDir.exists() || pictureDir.mkdirs()) {
            return pictureDir;
        }
        return null;
    }

    public static boolean checkPermission(String permission, Context context) {
        boolean checkResult = TextUtils.isEmpty(permission);
        if (!checkResult) {
            checkResult = context != null && context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
            if (!checkResult && context instanceof Activity) {
                ((Activity) context).requestPermissions(new String[]{permission}, DEFAULT_PERMISSION_REQUEST_CODE);
            }
        }
        return checkResult;
    }

    public static void updateImgToPhotoAlbum(String imgPath, Context context) {
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), imgPath, "", "");
            Uri uri = Uri.fromFile(new File(imgPath));
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        } catch (Exception e) {
            printLogE(e);

        }
    }
}
