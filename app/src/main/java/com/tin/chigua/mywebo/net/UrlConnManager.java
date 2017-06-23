package com.tin.chigua.mywebo.net;

import android.text.TextUtils;

import org.apache.http.NameValuePair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by hasee on 6/21/2017.
 */

public class UrlConnManager {

    private static final String REQUEST_POST = "POST";
    private static final int CONNECT_TIME_OUT = 15000;
    private static final int READ_TIME_OUT = 3000;
    private static final String PROPERTY_KEY = "Connection";
    //保持连接
    private static final String PROPERTY_VALUE = "Keep-Alive";
    private static final String ENCODE_METHOD = "UTF-8";
    /**
     *
     * @param url
     * @return 返回一个HttpURLConnection，配置好基本配置
     */
    public static HttpURLConnection getHttpURLConnection(String url){

        HttpURLConnection mHttpURLConnection = null;
        try {
            URL mUrl = new URL(url);
            mHttpURLConnection = (HttpURLConnection) mUrl.openConnection();
            //设置链接超时时间
            mHttpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
            //设置读取超时时间
            mHttpURLConnection.setReadTimeout(READ_TIME_OUT);
            //设置请求方法参数
            mHttpURLConnection.setRequestMethod(REQUEST_POST);
            //添加Header
            mHttpURLConnection.setRequestProperty(PROPERTY_KEY,PROPERTY_VALUE);
            //接收输入流
            mHttpURLConnection.setDoInput(true);
            //传递参数时需要开启
            mHttpURLConnection.setDoOutput(true);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return mHttpURLConnection;
    }

    public static void postParms(OutputStream outputStream, List<NameValuePair> pairList) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        for (NameValuePair pair:pairList) {
            if(!TextUtils.isEmpty(stringBuilder)){
                stringBuilder.append("&");
            }
            stringBuilder.append(URLEncoder.encode(pair.getName(),ENCODE_METHOD));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(pair.getValue(),ENCODE_METHOD));
            /**
             * 为了提高写入的效率，使用了字符流的缓冲区。
             * 创建了一个字符写入流的缓冲区对象，并和指定要被缓冲的流对象相关联。
             */
            BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(outputStream,ENCODE_METHOD));
            //使用缓冲区中的方法将数据写入到缓冲区中。
            bufferWriter.write(stringBuilder.toString());
            //使用缓冲区中的方法，将数据刷新到目的地文件中去。
            bufferWriter.flush();
            //关闭缓冲区,同时关闭了OutputStreamWriter流对象
            bufferWriter.close();
        }
    }

}
