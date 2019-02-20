package com.black.vision.text;

import android.graphics.Typeface;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.black.vision.R;
import com.black.vision.base.BaseActivity;

/**
 * Description: Text activity. text collections.
 * Date：19-1-2-下午5:19
 * Author: black
 */
public class TextActivity extends BaseActivity {

    private TextView tvContent;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_text;
    }

    @Override
    protected void init() {
        tvContent = findViewById(R.id.tv_content);
        String content = "政治，它指对社会治理的行为，亦指维护+91 9711302264统治的行为。政治是各种团体进行集体决策的一个过程，尤指对于某一政治实体的统治，例如统治一个国家，亦指对于一国内外事务之监督与管制。";
        tvContent.setText(content);
    }

    @Override
    protected void unInit() {

    }

    public void onSkipClick(View view) {
        printLogE("onSkipClick...");
        tvContent.setAutoLinkMask(Linkify.PHONE_NUMBERS);
//        if (view instanceof TextView) {
//            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
//            ((TextView) view).setTypeface(Typeface.MONOSPACE);
//        }
    }
}
