package com.tin.chigua.mywebo.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.adapter.HomePagerAdapter;
import com.tin.chigua.mywebo.ui.ToolbarX;
import com.tin.chigua.mywebo.utils.LUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.R.attr.offset;

/**
 * Created by hasee on 5/5/2017.
 */

public class HomeFragment extends BaseFragment {

    public static ViewPager mViewPager;
    private ToolbarX mToolbarX;

    private RelativeLayout mLayout;
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
    private static int firstClickTime = 0;

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

        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentTime = (int) Calendar.getInstance().getTimeInMillis();
                if (firstClickTime == 0){
                    firstClickTime = (int) Calendar.getInstance().getTimeInMillis();
                }
                int index = currentTime - firstClickTime;
                LUtils.logE(getActivity(),"currentTime = " + currentTime + "firstTime = " + firstClickTime);
                if (index <= 300 && index > 0 && firstClickTime != 0){
                    LUtils.logE(getActivity(),"currentTime = " + currentTime + "firstTime = " + firstClickTime);
                    LUtils.toastShort(getActivity(),"你双击了RelativeLayout");
                    LUtils.logE(getContext(),"index = " + index);
                    firstClickTime = 0;
                }else if (index > 300){
                    LUtils.logE(getContext(),"index = " + index);
                    firstClickTime = 0;
                }

            }
        });
    }

    private void initViewPager(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.home_viewpager);
        mViewPager.setAdapter(new HomePagerAdapter(getChildFragmentManager(),mFragmentList));
        mViewPager.addOnPageChangeListener(new MyPageChangeListener());
        mViewPager.setCurrentItem(0);
    }

    private void initWidth(View view) {
        mImgvButtom = (ImageView) view.findViewById(R.id.tab_line_igv);
        mLayout = (RelativeLayout) view.findViewById(R.id.switch_tab_rl);
        //获取ImageView的宽度
        int imgW = mImgvButtom.getLayoutParams().width;
        //获取RelativeLayout的宽度
        mLayout.measure(0,0);
        int switchLlW = mLayout.getMeasuredWidth();
//        mOffset = (switchLlW / 2) - imgW;
        //获取ImageView（子控件）的margin
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mImgvButtom.getLayoutParams();
        int leftMargin = lp.leftMargin;
        mOffset = switchLlW - imgW - leftMargin * 2;
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
            ObjectAnimator anim = ObjectAnimator.ofFloat(mImgvButtom,"translateX",(float)(currentIndex * (mOffset)),
                    (float)(position * (mOffset)));
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
