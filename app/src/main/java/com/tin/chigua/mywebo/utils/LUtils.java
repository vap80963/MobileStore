package com.tin.chigua.mywebo.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by hasee on 4/27/2017.
 */

public class LUtils {

    private static boolean isPrint = true;

    public static void logE(Context context,String str){
        if (isPrint){
            Log.e(context.getClass().toString(), "logE: " + str);
        }
    }

    public static void logD(Context context,String str){
        if (isPrint){
            Log.d(context.getClass().toString(), "logD: " + str);
        }
    }

    public static void logW(Context context,String str){
        Log.w(context.getClass().toString(), "logW: " + str);
    }

    public static void logI(Context context,String str){
        if (isPrint){

        }
        Log.i(context.getClass().toString(), "logI: " + str);
    }

    public static void logV(Context context,String str){
        if (isPrint){
            Log.v(context.getClass().toString(), "logV: " + str);
        }
    }

    public static void toastShort(Context context, String str){
        if (isPrint){
            Toast.makeText(context,"result = " + str, Toast.LENGTH_SHORT).show();
        }
    }

    public static void toastLong(Context context, String str){
        if (isPrint){
            Toast.makeText(context,"result = " + str, Toast.LENGTH_LONG).show();
        }
    }
}
