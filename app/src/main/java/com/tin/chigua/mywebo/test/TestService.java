package com.tin.chigua.mywebo.test;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by hasee on 6/22/2017.
 */

public class TestService extends Service {

    boolean threadDisable;
    int count;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!threadDisable){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
            }
        }).start();
    }

    public int getCount(){
        return count;
    }

    class TestBinder extends Binder {
        public TestService getService() {
            return TestService.this;
        }
    }

}


