package com.tin.chigua.mywebo.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.constant.Constants;
import com.tin.chigua.mywebo.ui.ToolbarX;
import com.tin.chigua.mywebo.utils.LUtils;
import com.tin.chigua.mywebo.utils.MySharePreferences;

/**
 * Created by hasee on 5/6/2017.
 */

public class FirstShowActivity extends AppCompatActivity {

    private ToolbarX mToolbarX;
    private ActionBar mActionBar;
    private AuthInfo mAuthInfo;
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mToolbarX = getToolbarX();
//        mToolbarX.hide();
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first_show);
        if(!this.isTaskRoot()){
            Intent intent = getIntent();
            if(null != intent){
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)){
//                    finish();
                    LUtils.toastShort(this,"activity栈中存在该Activity");
                }
            }
        }
        mImageView = (ImageView) findViewById(R.id.firstImgv);
        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView,"alpha",0.1f,1.0f);
        animator.setDuration(2000)
                .start();
//        Intent intent = new Intent(FirstShowActivity.this, EmojiDownService.class);
//        FirstShowActivity.this.startService(intent);
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY,
                Constants.REDIRECT_URL,Constants.SCOPE);
        WbSdk.install(this,mAuthInfo);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                String token = MySharePreferences.getToken(FirstShowActivity.this);
                if(token.equals("")){
                    startActivity(new Intent(FirstShowActivity.this,OauthActivity.class));
                }else {
                    startActivity(new Intent(FirstShowActivity.this,MainActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_in_right_left,R.anim.anim_out_right_left);
    }

//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_first_show;
//    }


}
