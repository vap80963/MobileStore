package com.tin.chigua.mywebo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.adapter.ShowImageViewPagerAdPater;
import com.tin.chigua.mywebo.bean.PicUrlBean;
import com.tin.chigua.mywebo.ui.ToolbarX;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 5/21/2017.
 */

public class ShowImageActivity extends BaseActivity {

    private int position;
    private List<PicUrlBean> picUrlBeanList;
    private ViewPager mViewPager;
    private ShowImageViewPagerAdPater mAdPater;
    private ProgressBar mProgressBar;

    private ToolbarX mToolbarX;
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.TranslucentTheme);
        super.onCreate(savedInstanceState);
        init();
        mToolbarX = getToolbarX();
        mToolbarX.hide();
        mAdPater = new ShowImageViewPagerAdPater(this,picUrlBeanList);
        mViewPager.setAdapter(mAdPater);
        mViewPager.setCurrentItem(position);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(KeyEvent.KEYCODE_BACK == keyCode){
//            finish();
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    private void init() {
        mProgressBar = (ProgressBar) findViewById(R.id.show_image_progressbar);
        mViewPager = (ViewPager) findViewById(R.id.show_image_viewpager);
        mImageView = (ImageView) findViewById(R.id.activity_show_image_img);

        mProgressBar.setVisibility(View.GONE);
        mViewPager.setOffscreenPageLimit(9);
        //获取从GridRclvAdapter传过来的图片url集合
        position = getIntent().getIntExtra("position",0);
        picUrlBeanList = new ArrayList<>();
        Bundle bundle = getIntent().getBundleExtra("img_url_list");
        picUrlBeanList = (List<PicUrlBean>) bundle.getSerializable("url_list");
        //设置点击图片则返回上一个界面
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_image;
    }
}
