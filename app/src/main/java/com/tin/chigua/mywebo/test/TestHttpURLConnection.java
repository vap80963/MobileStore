package com.tin.chigua.mywebo.test;

import android.util.Log;

import com.tin.chigua.mywebo.net.UrlConnManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 6/21/2017.
 */

public class TestHttpURLConnection {

    public static void userHttpURLConnectionPOST(String url){
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = UrlConnManager.getHttpURLConnection(url);
        try {
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair("username","惟爱v罐子"));
            list.add(new BasicNameValuePair("password","vap80963"));
            UrlConnManager.postParms(httpURLConnection.getOutputStream(),list);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            int reposeCode = httpURLConnection.getResponseCode();
            Log.e("httpConnection","responseCode = " + reposeCode + "  inputstream = " + inputStream.toString());
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
//        AsyncTask



    }


        public void useHttpConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                TestHttpURLConnection.userHttpURLConnectionPOST("http://www.baidu.com");
            }
        }).start();
    }


}
