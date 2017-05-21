
package com.tin.chigua.mywebo.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenTools {

    private static ScreenTools mScreenTools;
    private static Context mContext;

    private static final DisplayMetrics mMetrics = new DisplayMetrics();
    private static final float mScreenWidth = 0;
    private static final float mScreenHeighth = 0;



    private ScreenTools(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static ScreenTools instance(Context context) {
        mContext = context;
        if (mScreenTools == null)
            mScreenTools = new ScreenTools(context);
        return mScreenTools;
    }

    public int dip2px(float f) {
        return (int) (0.5D + (double) (f * getDensity(mContext)));
    }

    public int dip2px(int i) {
        return (int) (0.5D + (double) (getDensity(mContext) * (float) i));
    }

    public int get480Height(int i) {
        return (i * getScreenWidth()) / 480;
    }

    public float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public int getScal() {
        return (100 * getScreenWidth()) / 480;
    }

    public int getScreenDensityDpi() {
        return mContext.getResources().getDisplayMetrics().densityDpi;
    }

    public int getScreenHeight() {
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }

    public int getScreenWidth() {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }


    public float getXdpi() {
        return mContext.getResources().getDisplayMetrics().xdpi;
    }

    public float getYdpi() {
        return mContext.getResources().getDisplayMetrics().ydpi;
    }

    public int px2dip(float f) {
        float f1 = getDensity(mContext);
        return (int) (((double) f - 0.5D) / (double) f1);
    }

    public int px2dip(int i) {
        float f = getDensity(mContext);
        return (int) (((double) i - 0.5D) / (double) f);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
