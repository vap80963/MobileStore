package com.tin.chigua.mywebo.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.tin.chigua.mywebo.bean.StatusesBean;
import com.tin.chigua.mywebo.cache.ConfigCache;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.tin.chigua.mywebo.cache.ConfigCache.STATUS_BEAN;

/**
 * Created by hasee on 6/19/2017.
 */

public class GsonUtil {

    public static List<StatusesBean> analyzeJson(JsonArray response) {

        List<StatusesBean> list;
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        Type type = new TypeToken<ArrayList<StatusesBean>>(){}.getType();
        list = gson.fromJson(response, type);
        Log.e("GsonUtil",list.size() + "");
        return list;
    }

//    public static <T> List<T> analyzeJson(JsonArray response, final T... args) {
//
//        List<T> list;
//        final GsonBuilder gsonBuilder = new GsonBuilder();
//        final Gson gson = gsonBuilder.create();
//        Type type = new TypeToken<ArrayList<T>>(){}.getType();
//        list = gson.fromJson(response, type);
//        Log.e("GsonUtil",list.size() + "");
//        return list;
//    }

    /**
     * 将请求返回的JsonAraay结果包装后，放入本地缓存起来
     * @param response
     * @param url
     * @return
     */
    public static JsonObject wrapperToJsonObject(JsonArray response, String url) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.add(STATUS_BEAN,response);
        ConfigCache.setUrlCache(jsonObject, url);
        return jsonObject;
    }

    public static JsonArray getJsonArray(String cacheString){
        JsonObject jsonObject = new JsonParser().parse(cacheString).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray(STATUS_BEAN);
//        Log.e("GsonUtil","jsonArray = " + jsonArray.getAsString());
        return jsonArray;
    }
}
