package com.black.vision.portal;

import android.content.Intent;
import android.os.Handler;

import com.black.vision.R;
import com.black.vision.app.VisionApp;
import com.black.vision.base.BaseActivity;
import com.black.vision.text.TextActivity;
import com.black.vision.util.ToastUtil;


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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    printLogE("postDelayed...0...Context==" + DispatchActivity.this);
                    ToastUtil.show("0000000000");
                } catch (Exception e) {
                    printLogE(e);
                }
                try {
                    printLogE("postDelayed...1...ontext==" + DispatchActivity.this.getApplicationContext());
                    ToastUtil.show("1111111111");
                } catch (Exception e) {
                    printLogE(e);
                }
                try {
                    printLogE("postDelayed...2...Context==" + DispatchActivity.this.getApplication());
                    ToastUtil.show("2222222222");
                } catch (Exception e) {
                    printLogE(e);
                }
                try {
                    printLogE("postDelayed...3...Context==" + DispatchActivity.this.getBaseContext());
                    ToastUtil.show("3333333333");
                } catch (Exception e) {
                    printLogE(e);
                }
                try {
                    printLogE("postDelayed...5...Context==" + DispatchActivity.this);
                    ToastUtil.show("5555555555");
                    ToastUtil.show("666666666666");
                } catch (Exception e) {
                    printLogE(e);
                }
            }
        }, 5000);
        startActivity(new Intent(this, TextActivity.class));
        finish();
    }
}
