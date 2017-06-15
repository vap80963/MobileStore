package com.tin.chigua.mywebo.utils;

import java.io.IOException;

/**
 * 用于判断Android系统类型，针对性地进行适配
 * 已有 华为、小米、魅族
 * Created by hasee on 6/12/2017.
 */

public class AndroidRomUtil {

    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";


    /**
     * 华为rom
     *
     * @return
     */
    public static boolean isEMUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_EMUI_VERSION_CODE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * 小米rom
     *
     * @return
     */
    public static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            /*String rom = "" + prop.getProperty(KEY_MIUI_VERSION_CODE, null) + prop.getProperty(KEY_MIUI_VERSION_NAME, null)+prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null);
            Log.d("Android_Rom", rom);*/
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * 魅族rom
     *
     * @return
     */
    public static boolean isFlyme() {
        if ("Meizu".equals(android.os.Build.MANUFACTURER)) {
            return true;
        }
        return false;
        /*try {
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }*/
    }
}