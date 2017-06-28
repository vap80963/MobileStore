package com.tin.chigua.mywebo.cache;

import android.os.Environment;
import android.util.Log;

import com.google.gson.JsonObject;
import com.tin.chigua.mywebo.constant.MyApplication;
import com.tin.chigua.mywebo.utils.FileUtils;
import com.tin.chigua.mywebo.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by hasee on 6/18/2017.
 */

public class ConfigCache {

    private static final int CONFIG_CACHE_MOBILE_TIME_OUT = 1000 * 60 * 60 * 24; //移动网络下超过24小时更新
    private static final int CONFIG_CACHE_WIFI_TIME_OUT = 1000 * 60 * 60 * 10; //移动网络下超过10小时更新

    public static final String STATUS_BEAN = "statuses_bean";
    private static final String TAG = "ConfigCache";

    public static String getUrlCache(String url){

        if (url == null){
            return null;
        }

        String result = null;
        File file = new File(Environment.getExternalStorageDirectory() + "/" + getCacheDecodeString(url));
        Log.e("filePath = ",file.getAbsolutePath());
        if(file.exists() && file.isFile()){
            long expiredTime = System.currentTimeMillis() - file.lastModified();
            int networkState = MyApplication.mNetWorkState;
            if(networkState == NetworkUtils.NETWORK_NONE && expiredTime < 0){
                return null;
            }
            if (networkState == NetworkUtils.NETWORK_WIFI && expiredTime > CONFIG_CACHE_WIFI_TIME_OUT){
                return null;
            }
            if (networkState == NetworkUtils.NETWORK_MOBILE && expiredTime > CONFIG_CACHE_MOBILE_TIME_OUT){
                return null;
            }
            try {
                result = FileUtils.readTextFile(file);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static void setUrlCache(JsonObject data, String url) {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + getCacheDecodeString(url));
        Log.e("filePath = ?",file.getAbsolutePath());
        try {
            //创建缓存数据到磁盘，就是创建文件
            FileUtils.writeTextFile(file, data.toString());
//            Log.e("jsonObject = ", data.get(STATUS_BEAN).toString());
//            Log.e("jsonObject = ", data.toString() + "");
        } catch (IOException e) {
            Log.e("Config", "write " + file.getAbsolutePath() + " data failed!");
            e.printStackTrace();
        }
    }

    //1. 处理特殊字符
    public static String getCacheDecodeString(String url) {

        //2. 去除后缀名带来的文件浏览器的视图凌乱(特别是图片更需要如此类似处理，否则有的手机打开图库，全是我们的缓存图片)
        if (url != null) {
            return url.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
        }
        return null;
    }

    //返回获取缓存文件结果
    public static String  getConfigCacheState(String url) {
        if (url == null){
            Log.e("error_url","url的值为空");
            return null;
        }
        String cacheConfigUrl = ConfigCache.getCacheDecodeString(url);
        String cacheString = ConfigCache.getUrlCache(cacheConfigUrl);
        Log.e(TAG,"cacheString = " + cacheString);
        if (null != cacheString){
            Log.e(TAG,"我是用通过本地数据来获取数据的");
            return cacheString;
        }else {
            Log.e(TAG,"我通过网络获取数据");
            return null;
        }
    }

}
