package com.tin.chigua.mywebo.constant;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.tin.chigua.mywebo.utils.NetworkUtils;

import java.io.File;

/**
 * Created by hasee on 6/15/2017.
 */

public class MyApplication extends Application {

    public static String mSdcardDataDir;
    public static int mNetWorkState;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        initEnv();
        mContext = getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }

    public void initEnv() {

        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory().getPath() +  "/weboco/config/");
            if(!file.exists()) {
                if (file.mkdirs()) {
                    mSdcardDataDir = file.getAbsolutePath();
                }
            } else {
                mSdcardDataDir = file.getAbsolutePath();
            }
        }

        mNetWorkState = NetworkUtils.getNetWorkState(this);

    }

    public Context getMyApplicationContext(){

        return getApplicationContext();
    }

}
