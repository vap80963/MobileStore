package com.tin.chigua.mywebo.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.adapter.HomePagerAdapter;
import com.tin.chigua.mywebo.ui.ToolbarX;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.offset;

/**
 * Created by hasee on 5/5/2017.
 */

public class HomeFragment extends BaseFragment {

    public static ViewPager mViewPager;
    private ToolbarX mToolbarX;

    private TextView mFriendsTv;
    private TextView mHotTv;
    private ImageView mImgvButtom;
    private List<Fragment> mFragmentList;

    private int currentIndex = 0;
    private DisplayMetrics metrics;
    private double mOffset;
    private int mFTvW;
    private int mHTvW;
    private int mScreenW;

    private View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentList  = new ArrayList<>();
        mFragmentList.add(FriendsFragment.newInstance());
        mFragmentList.add(HotFragment.newInstance());
//        init();
    }

    private void init() {

//        HomeFragment fragment = new HomeFragment();
//        Bundle args = this.getArguments();
//        mToolbarX = (ToolbarX) args.getSerializable("toolbarX");
//        View v =
//        mToolbarX.setCustomeView()
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_home,container,false);
            initWidth(view);
            initTextView(view);
            initViewPager(view);
        }
        ViewGroup parent = (ViewGroup)view.getParent();
        if(parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    private void initTextView(View view) {
        mFriendsTv = (TextView) view.findViewById(R.id.tab_friends_tv);
        mHotTv = (TextView) view.findViewById(R.id.tab_hot_tv);

        mFriendsTv.setOnClickListener(new MyOnClickListener(0));
        mHotTv.setOnClickListener(new MyOnClickListener(1));
    }

    private void initViewPager(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.home_viewpager);
        mViewPager.setAdapter(new HomePagerAdapter(getChildFragmentManager(),mFragmentList));
        mViewPager.addOnPageChangeListener(new MyPageChangeListener());
        mViewPager.setCurrentItem(0);
    }

    private void initWidth(View view) {
        mImgvButtom = (ImageView) view.findViewById(R.id.tab_line_igv);
        int imgW = mImgvButtom.getLayoutParams().width;
//        mFriendsTv = new TextView(getActivity());
//        mHotTv = new TextView(getActivity());
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        mFriendsTv.setLayoutParams(params);
//        mHotTv.setLayoutParams(params);
//        mFTvW = mFriendsTv.getLeft();
//        mHTvW = mHotTv.getLeft();
//        LUtils.logD(getActivity(),"mFTvW = " + mFTvW);
//        LUtils.logD(getActivity(),"mHTvW = " + mHTvW);
        metrics = new DisplayMetrics();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        mScreenW = metrics.widthPixels;
//        mOffset = mFTvW - mHTvW;
        mOffset = imgW;
        Matrix matrix = new Matrix();
        matrix.postTranslate((float) offset, 0);
        mImgvButtom.setImageMatrix(matrix);
    }

    class MyPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            ObjectAnimator anim = ObjectAnimator.ofFloat(mImgvButtom,"scaleX",(float)(positionOffset * () ),);
        }

        @Override
        public void onPageSelected(int position) {

            switch (position){
                case 1:
                    mHotTv.setTextColor(getResources().getColor(R.color.black));
                    mFriendsTv.setTextColor(getResources().getColor(R.color.gray));
                    break;
                case 0:
                    mFriendsTv.setTextColor(getResources().getColor(R.color.black));
                    mHotTv.setTextColor(getResources().getColor(R.color.gray));
                    break;
            }
            ObjectAnimator anim = ObjectAnimator.ofFloat(mImgvButtom,"translateX",(float)(currentIndex * (mOffset + 40)),
                    (float)(position * (mOffset + 40)));
            anim.setDuration(200)
                .start();
            currentIndex = position;
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float val = (Float) animation.getAnimatedValue();
                    mImgvButtom.setTranslationX(val);
                }
            });
//            Animation animation = new TranslateAnimation((int)(currentIndex*offset + mScreenW * 0.2),(int)(position*offset),
//                    0,0);
//            animation.setDuration(300);
//            animation.setFillAfter(true);
//            mImgvButtom.setAnimation(animation);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyOnClickListener implements View.OnClickListener{

        private int mId;

        public MyOnClickListener(int id){
            mId = id;
        }

        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(mId);
        }
    }

    public static HomeFragment newInstance(){
        HomeFragment fragment = null;
        if(null == fragment){
            fragment = new HomeFragment();
        }
        return fragment;

    };

}
