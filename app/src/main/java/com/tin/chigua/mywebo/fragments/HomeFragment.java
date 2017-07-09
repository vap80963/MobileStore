package com.tin.chigua.mywebo.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.adapter.HomePagerAdapter;
import com.tin.chigua.mywebo.constant.StaticUtil;
import com.tin.chigua.mywebo.constant.UrlUtil;
import com.tin.chigua.mywebo.ui.ToolbarX;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.R.attr.offset;

/**
 * Created by hasee on 5/5/2017.
 */

public class HomeFragment extends BaseFragment implements NavigationView.OnNavigationItemSelectedListener{

    private Class fragmentArray[] = new Class[]{FriendsFragment.class, HotFragment.class};
    private String urlArray[] = {UrlUtil.HOME_TIMELINE,UrlUtil.PUBLIC_TIMELINE};
    public static ViewPager mViewPager;
    private ToolbarX mToolbarX;
    private Toolbar mToolbar;
    private DrawerLayout drawer;


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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_home1,container,false);
            init(view);
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

    private void init(View v) {
        mToolbar = (Toolbar) v.findViewById(R.id.home_common_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        drawer = (DrawerLayout) v.findViewById(R.id.common_drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
//        mToolbar.setNavigationIcon(R.drawable.ic_menu_camera);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_menu);
        drawer.setDrawerListener(toggle);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        toggle.syncState();

        NavigationView navigationView = (NavigationView) v.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public void moveRecylvToPosition(int position) {

    }

    @Override
    public void startRequestData(String url, int loadMode) {

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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class MyOnClickListener implements View.OnClickListener{

        private int mId;

        public MyOnClickListener(int id){
            mId = id;
        }

        @Override
        public void onClick(View v) {
            if(mViewPager.getCurrentItem() != mId){
                mViewPager.setCurrentItem(mId);
                return;
            }
            int currentTime = (int) Calendar.getInstance().getTimeInMillis();
            if (firstClickTime == 0 || currentTime - firstClickTime > 300){
                firstClickTime = (int) Calendar.getInstance().getTimeInMillis();
            }
            int index = currentTime - firstClickTime;
            if (index <= 300 && index > 0 && firstClickTime != 0) {
                try {
                    BaseFragment fragment = (BaseFragment) fragmentArray[mId].newInstance();
                    fragment.moveRecylvToPosition(0);
                    fragment.startRequestData(urlArray[mId], StaticUtil.REFRESH_DOWN_SIGN);
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                firstClickTime = 0;
            }else if(index > 300){
                firstClickTime = 0;
            }
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
