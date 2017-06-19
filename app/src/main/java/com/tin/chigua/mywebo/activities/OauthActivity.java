package com.tin.chigua.mywebo.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.constant.Constants;
import com.tin.chigua.mywebo.constant.MyApplication;
import com.tin.chigua.mywebo.ui.ToolbarX;
import com.tin.chigua.mywebo.utils.LUtils;
import com.tin.chigua.mywebo.utils.MySharePreferences;

import java.text.SimpleDateFormat;

/**
 * Created by hasee on 5/7/2017.
 */

public class OauthActivity extends BaseActivity {

    private SsoHandler mSsoHandler;
    private static Oauth2AccessToken mAuthToken;
    private ToolbarX mToolbarX;
    private TextView mMidTv;

    private AnimatorSet animatorSet = null;

    private ImageView mStartAuth;
    private TextView mTokenTv;

    private static MyApplication mApplication;
    private static MySharePreferences mPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (MyApplication) getApplication();
        initOAuth();
        init();
//        mAuthToken =  AccessTokenKeeper.readAccessToken(LoginActivity.this);
        mMidTv.setText("授权");
        mAuthToken = mPreferences.getFromSP(OauthActivity.this);
        if(mAuthToken.getToken().equals("")){
            beginOAuth();
        }else {
            startActivity(new Intent(this,MainActivity.class));
            finish();
//            MySharePreferences.clearToken(LoginActivity.this);
        }
    }

    private void init() {
        mStartAuth = (ImageView) findViewById(R.id.start_oauth);
        mTokenTv = (TextView) findViewById(R.id.auth_token);
        mMidTv = (TextView) findViewById(R.id.tool_mid_tv);
        mStartAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSsoHandler.authorize(new MyWbAuthListener());
            }
        });
        mStartAuth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        beginAnimatorSet(1.0f,1.1f);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        beginAnimatorSet(1.1f,1.0f);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void beginAnimatorSet(float startValue, float endValue){
        animatorSet = new AnimatorSet();
        final ObjectAnimator animatorX = ObjectAnimator.ofFloat(mStartAuth,"scaleX",startValue,endValue);
        final ObjectAnimator animatorY = ObjectAnimator.ofFloat(mStartAuth,"scaleY",startValue,endValue);

        animatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mStartAuth.setTranslationX(value);
            }
        });
        animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mStartAuth.setTranslationY(value);
            }
        });
        animatorSet.playTogether(animatorX,animatorY);
        animatorSet.setDuration(200)
                .start();
    }

    private void beginOAuth() {



    }

    public static void refreshToken(){

        if(!TextUtils.isEmpty(mAuthToken.getRefreshToken())){
            AccessTokenKeeper.refreshToken(Constants.APP_KEY, mApplication.getApplicationContext(), new RequestListener() {
                @Override
                public void onComplete(String s) {
                    MySharePreferences.refreshAccessToken(mApplication.getApplicationContext(),mAuthToken);
                }

                @Override
                public void onWeiboException(WeiboException e) {
                    LUtils.toastShort(mApplication.getApplicationContext(),"授权失败，请重试！");
                }
            });
        }

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
//                        updateTokenView(false);
                        AccessTokenKeeper.writeAccessToken(OauthActivity.this,mAuthToken);
                        MySharePreferences.writeToSP(OauthActivity.this,mAuthToken);
                        startActivity(new Intent(OauthActivity.this,MainActivity.class));
                        LUtils.toastShort(OauthActivity.this,"授权成功,即将进入微博界面");
                        LUtils.logE(OauthActivity.this,mAuthToken+"");
                        finish();
                    }
                }
            });
        }

        @Override
        public void cancel() {
            LUtils.toastShort(OauthActivity.this,"授权请求取消");
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




