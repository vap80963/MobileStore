package com.tin.chigua.mywebo.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.constant.StaticUtil;
import com.tin.chigua.mywebo.fragments.FriendsFragment;
import com.tin.chigua.mywebo.fragments.HomeFragment;
import com.tin.chigua.mywebo.fragments.HotFragment;
import com.tin.chigua.mywebo.fragments.MessageFragment;
import com.tin.chigua.mywebo.fragments.MineFragment;
import com.tin.chigua.mywebo.fragments.NewWeboFragment;
import com.tin.chigua.mywebo.fragments.SquareFragment;
import com.tin.chigua.mywebo.ui.FragmentTabhost;
import com.tin.chigua.mywebo.ui.ToolbarX;
import com.tin.chigua.mywebo.utils.UrlUtil;

public class MainActivity extends BaseActivity {

    private FragmentTabhost mTabHost;
    private FrameLayout mLayout;
    private ImageView mImagv;
    private TextView mTv;
    private ToolbarX mToolbarX;

    private final String[] tabNames = {"首页","消息","","社区","我的"};
    private int[] tabImageSelector = new int[]{R.drawable.home_icon_selector,R.drawable.message_icon_selector,
                            R.drawable.ic_tabbar_new_webo,R.drawable.search_icon_selector,R.drawable.mime_icon_selector};
    private int tabTextSelector = R.drawable.home_text_selector;
    private Class mFragmentArray[] = new Class[]{HomeFragment.class, MessageFragment.class,
            NewWeboFragment.class,SquareFragment.class, MineFragment.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbarX = getToolbarX();
        mToolbarX.hide();
        initialize();
    }

    private void initialize() {

        mTabHost = (FragmentTabhost) findViewById(R.id.fragment_tabhost);
        mTabHost.setup(MainActivity.this,getSupportFragmentManager(),R.id.fragment_layout);
        mTabHost.getTabWidget().setDividerDrawable(null);
        for(int i = 0;i < tabNames.length;i++){
            View v = getLayoutInflater().inflate(R.layout.item_tabhost,null);
            mImagv = (ImageView) v.findViewById(R.id.tab_imgv);
            mTv = (TextView) v.findViewById(R.id.tab_tv);
            mImagv.setImageResource(tabImageSelector[i]);
            mTv.setText(tabNames[i]);
            mTv.setTextColor(Integer.valueOf(tabTextSelector));
            if("".equals(tabNames[i])) {
                mTv.setVisibility(View.GONE);
            }
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec("" + i).setIndicator(v);
            Bundle args = new Bundle();
//            args.putSerializable("toolbarX", mToolbarX);
            args.putString("title",tabNames[i]);
            mTabHost.addTab(tabSpec,mFragmentArray[i],args);

        }
        mTabHost.getTabWidget().getChildTabViewAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentTab = mTabHost.getCurrentTab();
                if(0 == currentTab){
                    int currentItem = HomeFragment.mViewPager.getCurrentItem();
                    switch (currentItem){
                        case 0:
                            FriendsFragment.mSwipeLayout.setRefreshing(true);
                            FriendsFragment friendsFragment = (FriendsFragment) FriendsFragment.newInstance();
                            friendsFragment.moveRecylvToPosition(0);
                            friendsFragment.startRequestData(UrlUtil.HOME_TIMELINE, StaticUtil.REFRESH_DOWN_SIGN);
                            break;
                        case 1:
                            HotFragment.mSwipeLayout.setRefreshing(true);
                            HotFragment hotFragment = (HotFragment) HotFragment.newInstance();
                            hotFragment.moveRecylvToPosition(0);
                            hotFragment.startRequestData(UrlUtil.PUBLIC_TIMELINE, StaticUtil.REFRESH_DOWN_SIGN);
                            break;
                    }
                }else {
                    mTabHost.setCurrentTab(0);
                }
//                LUtils.toastShort(MainActivity.this," " + v.getId());
            }
        });
        mTabHost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewWeboFragment dialog = (NewWeboFragment) NewWeboFragment.newInsatance();
                dialog.show(getSupportFragmentManager(),"new_webo");
            }
        });
        //监听Tab变化，设置Toolbar内容
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int id = Integer.parseInt(tabId);
                switch (id){
                    case 0:
                        mToolbarX.hide();
                        break;
                    case 1:
                        mToolbarX.setNavigationIcon(R.string.cancel);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


}
