package com.tin.chigua.mywebo.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.utils.LUtils;
import com.tin.chigua.mywebo.utils.MySharePreferences;

import java.text.SimpleDateFormat;

/**
 * Created by hasee on 5/7/2017.
 */

public class OauthActivity extends BaseActivity {

    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAuthToken;

    private Button mStartAuth;
    private TextView mTokenTv;

    private static SharedPreferences mPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initOAuth();
        mStartAuth = (Button) findViewById(R.id.start_oauth);
        mTokenTv = (TextView) findViewById(R.id.auth_token);
//        mAuthToken =  AccessTokenKeeper.readAccessToken(LoginActivity.this);
        mAuthToken = MySharePreferences.getFromSP(OauthActivity.this);
        if(mAuthToken.getToken().equals("")){
            mSsoHandler.authorize(new MyWbAuthListener());
        }else {
            startActivity(new Intent(this,MainActivity.class));
//            MySharePreferences.clearToken(LoginActivity.this);
        }
    }

    private void beginOAuth() {
    }

    private void initOAuth() {
        mSsoHandler = new SsoHandler(OauthActivity.this);
    }

    private class MyWbAuthListener implements WbAuthListener {

        @Override
        public void onSuccess(final Oauth2AccessToken token) {
            OauthActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAuthToken = token;
                    if(mAuthToken.isSessionValid()){
                        updateTokenView(false);
                        AccessTokenKeeper.writeAccessToken(OauthActivity.this,mAuthToken);
                        MySharePreferences.writeToSP(OauthActivity.this,mAuthToken);
                        startActivity(new Intent(OauthActivity.this,MainActivity.class));
                        LUtils.toastShort(OauthActivity.this,"请求成功：" + mAuthToken + " 》》》》》》》》》》》");
                    }
                }
            });
        }

        @Override
        public void cancel() {
            LUtils.toastShort(OauthActivity.this,"请求取消：》》》》》》》》》》》");
        }

        @Override
        public void onFailure(WbConnectErrorMessage message) {
            LUtils.toastLong(OauthActivity.this,"请求失败：" + message.toString() +  " 》》》》》》》》》》》");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mSsoHandler != null){
            mSsoHandler.authorizeCallBack(requestCode,resultCode,data);
        }
    }

    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new java.util.Date(mAuthToken.getExpiresTime()));
        String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
        mTokenTv.setText(String.format(format, mAuthToken.getToken(), date));

        String message = String.format(format, mAuthToken.getToken(), date);
        if (hasExisted) {
            message = getString(R.string.weibosdk_demo_token_has_existed) + "\n" + message;
        }
        mTokenTv.setText(message);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
}




