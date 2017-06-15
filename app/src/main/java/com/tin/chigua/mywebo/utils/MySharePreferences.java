package com.tin.chigua.mywebo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by hasee on 5/8/2017.
 */

public class MySharePreferences {

    private static SharedPreferences mPreferences;

    public static String getToken(Context context){
        if(context == null)
            return "";
        mPreferences = context.getSharedPreferences("com.tin.chigua.mywebo",MODE_PRIVATE);
        String accessToken = mPreferences.getString("access_token","");
        return accessToken;
    }

    public static void clearToken(Context context){
        if(context != null){
            mPreferences = context.getSharedPreferences("com.tin.chigua.mywebo",MODE_PRIVATE);
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.clear();
            LUtils.logD(context,"clear成功");
            editor.commit();
        }
    }

    public static void writeToSP(Context context,Oauth2AccessToken token) {
        if(context == null && token.equals(""))
            return ;
        mPreferences = context.getSharedPreferences("com.tin.chigua.mywebo",MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("uid",token.getUid());
        editor.putString("access_token",token.getToken());
        editor.putString("refresh_token",token.getRefreshToken());
        editor.putLong("expires_time",token.getExpiresTime());
        editor.commit();
    }

    public static Oauth2AccessToken getFromSP(Context context){
        if(context == null)
            return null;
        Oauth2AccessToken token = new Oauth2AccessToken();
        mPreferences = context.getSharedPreferences("com.tin.chigua.mywebo",MODE_PRIVATE);
        token.setUid(mPreferences.getString("uid",""));
        token.setToken(mPreferences.getString("access_token",""));
        token.setRefreshToken(mPreferences.getString("refresh_token",""));
        token.setExpiresTime(mPreferences.getLong("expires_time",0));
        LUtils.logD(context,"token = " + token.toString());
        return token;
    }

    public static void refreshAccessToken(Context context,Oauth2AccessToken token){
        if(context == null && token.equals(""))
            return ;
        clearToken(context);
        mPreferences = context.getSharedPreferences("com.tin.chigua.mywebo",MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("uid",token.getUid());
        editor.putString("access_token",token.getToken());
        editor.putString("refresh_token",token.getRefreshToken());
        editor.putLong("expires_time",token.getExpiresTime());
        editor.commit();
    }

}
