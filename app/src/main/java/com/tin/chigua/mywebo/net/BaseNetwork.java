package com.tin.chigua.mywebo.net;

import android.content.Context;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.tin.chigua.mywebo.bean.HttpResponse;

/**
 * Created by hasee on 5/10/2017.
 * WBConstants   常用的key
 * AsyncWeiboRunner  网络请求类
 * WeiboParameters  请求参数
 */

public abstract class BaseNetwork {

    private AsyncWeiboRunner mAsyncWeiboRunner;
    private String mUrl;

    public BaseNetwork(Context context,String url){
        mAsyncWeiboRunner = new AsyncWeiboRunner(context);
        this.mUrl = url;
    }

    private RequestListener mRequestListener = new RequestListener() {
        @Override
        public void onComplete(String s) {
            boolean success = false;
            HttpResponse response = new HttpResponse();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(s);

            if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                if (object.has("error_code")) {
                    response.code = object.get("error_code").getAsInt();
                }
                if ((object.has("error"))) {
                    response.message = object.get("error").getAsString();
                }
                if (object.has("statuses")) {
                    response.response = object.get("statuses").getAsJsonArray();
                    success = true;
                } else if (object.has("users")) {
                    response.response = object.get("users").getAsJsonArray();
                    success = true;
                } else if (object.has("comments")) {
                    response.response = object.get("comments").getAsJsonArray();
                    success = true;
                }
                else {
                    response.response = object.getAsJsonArray();
                    success = true;
                }
                onFinish(response, success);
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            HttpResponse response = new HttpResponse();
            response.message = e.getMessage();
            onFinish(response,false);
        }
    };

    public void get() {
        mAsyncWeiboRunner.requestAsync(mUrl, onPrepare(), "GET", mRequestListener);
    }

    public void post() {
        mAsyncWeiboRunner.requestAsync(mUrl, onPrepare(), "POST", mRequestListener);
    }

    public void delete() {
        mAsyncWeiboRunner.requestAsync(mUrl, onPrepare(), "DELETE", mRequestListener);

    }

    /**
     * 将一个函数当做参数传递给一个方法，这个函数是一个回调函数
     * @return
     */
    public abstract WeiboParameters onPrepare();

    public abstract void onFinish(HttpResponse response, boolean success);

}
