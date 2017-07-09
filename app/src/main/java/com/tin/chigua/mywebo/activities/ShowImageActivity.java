package com.tin.chigua.mywebo.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.adapter.ShowImageViewPagerAdPater;
import com.tin.chigua.mywebo.bean.PicUrlBean;
import com.tin.chigua.mywebo.ui.ToolbarX;
import com.tin.chigua.mywebo.utils.AndroidRomUtil;
import com.tin.chigua.mywebo.utils.ScreenTools;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by hasee on 5/21/2017.
 */

public class ShowImageActivity extends AppCompatActivity {

    private int position;
    private List<PicUrlBean> mPicUrlBeanList;
    private ViewPager mViewPager;
    private ShowImageViewPagerAdPater mAdPater;
    private ProgressBar mProgressBar;
    private PhotoView mPhotoView;
    private TextView mCountTV;

    private ToolbarX mToolbarX;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_FullScreen);
        setContentView(R.layout.activity_show_image);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && !AndroidRomUtil.isEMUI()) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            setImmersePaddingTop();
        }
        init();
        mAdPater = new ShowImageViewPagerAdPater(this, mPicUrlBeanList);
        mViewPager.setAdapter(mAdPater);
        mViewPager.setCurrentItem(position);
        if (mPicUrlBeanList.size() <= 1) {
            mCountTV.setVisibility(View.GONE);
        } else {
            mCountTV.setVisibility(View.VISIBLE);
            int currentItem = position + 1;
            mCountTV.setText(currentItem + "/" + mPicUrlBeanList.size());
        }
//        overridePendingTransition();
    }

    private void setImmersePaddingTop() {
        ViewGroup viewGroup = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        viewGroup.setPadding(0, ScreenTools.instance(this).getStatusBarHeight(), 0, 0);
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
        mPhotoView = (PhotoView) findViewById(R.id.activity_show_image_img);
        mCountTV = (TextView) findViewById(R.id.show_image_img_count);

        mViewPager.setOffscreenPageLimit(9);
        //获取从GridRclvAdapter传过来的图片url集合
        position = getIntent().getIntExtra("position", 0);
        mPicUrlBeanList = new ArrayList<>();
        Bundle bundle = getIntent().getBundleExtra("img_url_list");
        mPicUrlBeanList = (List<PicUrlBean>) bundle.getSerializable("url_list");
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());
        //设置点击图片则返回上一个界面
//        mPhotoView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    public class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageSelected(int position) {
            String url = mPicUrlBeanList.get(position).original_pic;

            mCountTV.setVisibility(View.VISIBLE);
            int currentItem = position + 1;
            mCountTV.setText(currentItem + "/" + mPicUrlBeanList.size());

            //加载图片
//            Glide.with(ShowImageActivity.this)
//                    .load(url)
//                    .placeholder(R.drawable.camera)
//                    .centerCrop()
////                .animate(R.anim.)
//                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                    .error(R.drawable.image_error)
//                    .into(mPhotoView);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_show_image;
//    }
}
