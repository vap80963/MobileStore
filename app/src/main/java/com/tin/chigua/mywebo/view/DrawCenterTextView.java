package com.tin.chigua.mywebo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by hasee on 5/17/2017.
 */

public class DrawCenterTextView extends TextView {

    public DrawCenterTextView(Context context) {
        super(context);
    }

    public DrawCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawCenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawCenterTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable[] = getCompoundDrawables();
        Drawable drawableLeft = drawable[0];
        if(null != drawableLeft) {
            setGravity(Gravity.START); //设置text起始位置方向
            int drawWidth = drawableLeft.getIntrinsicWidth();
            int drawPadding = getCompoundDrawablePadding();
            int textWidth = (int) getPaint().measureText(getText().toString());
            int bodyWidth = drawWidth + drawPadding + textWidth;
            canvas.translate((getWidth() - bodyWidth) / 2, 0);
        }
        Drawable drawableRight = drawable[1];
        if(null != drawableRight) {
            setGravity(Gravity.END);
            int drawWidth = drawableLeft.getIntrinsicWidth();
            int drawPadding = getCompoundDrawablePadding();
            int textWidth = (int) getPaint().measureText(getText().toString());
            int bodyWidth = drawWidth + drawPadding + textWidth;
            canvas.translate(-(getWidth() - bodyWidth) / 2, 0);
        }
        super.onDraw(canvas);
    }
}
