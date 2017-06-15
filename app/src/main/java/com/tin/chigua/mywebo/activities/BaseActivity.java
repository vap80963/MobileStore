package com.tin.chigua.mywebo.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.ui.ToolbarX;
import com.tin.chigua.mywebo.utils.AndroidRomUtil;
import com.tin.chigua.mywebo.utils.ScreenTools;

/**
 * Created by hasee on 5/5/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private RelativeLayout mRlContent;
    private ToolbarX mToolbarX;
    private Toolbar mTooolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_base_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && !AndroidRomUtil.isEMUI()) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            setImmersePaddingTop();
        }
        initialize();
        mToolbarX = new ToolbarX(mTooolbar,this);
        View v = getLayoutInflater().inflate(getLayoutId(),mRlContent,false);
        mRlContent.addView(v);
    }
    /*
    如果将activity的theme设置为全屏，那么需要
    设置activity容器与其父容器的PaddingTop，设置为通知栏的高度
     */
    private void setImmersePaddingTop() {
        ViewGroup viewGroup = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        viewGroup.setPadding(0, ScreenTools.instance(this).getStatusBarHeight(), 0, 0);
    }

    public ToolbarX getToolbarX(){
        if(mToolbarX == null){
            mToolbarX = new ToolbarX(mTooolbar,this);
        }
        return mToolbarX;
    }

    private void initialize() {
        mRlContent = (RelativeLayout) findViewById(R.id.rlContent);
        mTooolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    public abstract int getLayoutId();

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_in_right_left,R.anim.anim_out_right_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_in_left_right,R.anim.anim_out_left_right);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.anim_in_right_left,R.anim.anim_out_right_left);
    }


}
