package com.tin.chigua.mywebo.constant;

import android.app.Application;
import android.content.Context;

/**
 * Created by hasee on 6/15/2017.
 */

public class MyApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
    }



    public Context getMyApplicationContext(){

        return getApplicationContext();
    }

}
