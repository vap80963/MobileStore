package com.tin.chigua.mywebo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by hasee on 6/18/2017.
 */

public class NetworkUtils {

    public static final int NETWORK_NONE = 0;
    public static final int NETWORK_WIFI = 1;
    public static final int NETWORK_MOBILE = 2;

    public static int getNetWorkState(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //检测是否为WIFI状态
        NetworkInfo.State state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if(state.equals(NetworkInfo.State.CONNECTED) || state.equals(NetworkInfo.State.CONNECTING))
            return NETWORK_WIFI;

        //检测是否为移动数据状态
        NetworkInfo.State state1 = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if(state1.equals(NetworkInfo.State.CONNECTED) || state1.equals(NetworkInfo.State.CONNECTING))
            return NETWORK_MOBILE;

        return NETWORK_NONE;
    }

}
