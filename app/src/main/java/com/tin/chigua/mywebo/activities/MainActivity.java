package com.tin.chigua.mywebo.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.constant.StaticUtil;
import com.tin.chigua.mywebo.constant.UrlUtil;
import com.tin.chigua.mywebo.fragments.FriendsFragment;
import com.tin.chigua.mywebo.fragments.HomeFragment;
import com.tin.chigua.mywebo.fragments.HotFragment;
import com.tin.chigua.mywebo.fragments.MessageFragment;
import com.tin.chigua.mywebo.fragments.MineFragment;
import com.tin.chigua.mywebo.fragments.NewWeboFragment;
import com.tin.chigua.mywebo.fragments.SquareFragment;
import com.tin.chigua.mywebo.ui.FragmentTabhost;
import com.tin.chigua.mywebo.ui.ToolbarX;

public class MainActivity extends BaseActivity {

    private FragmentTabhost mTabHost;
    private FrameLayout mLayout;
    private ImageView mTabImagv;
    private TextView mTabTv, mLeftTv, mMidTv, mRightTv;
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
        initView();
        initialize();
    }



    private void initView() {

        mLeftTv = (TextView) findViewById(R.id.tool_left_tv);
        mMidTv = (TextView) findViewById(R.id.tool_mid_tv);
        mRightTv = (TextView) findViewById(R.id.tool_right_tv);
        mTabHost = (FragmentTabhost) findViewById(R.id.fragment_tabhost);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 初始化tabhost
     */
    private void initialize() {

        mTabHost.setup(MainActivity.this,getSupportFragmentManager(),R.id.fragment_layout);
        //设置tabhost子控件之间的分割线为null
        mTabHost.getTabWidget().setDividerDrawable(null);
        for(int i = 0;i < tabNames.length;i++){
            /**
             * 对每个tabhost子控件对应的页面进行绘制
             * 此处没有进行懒加载，因为我只写了第一个页面
             */
            View v = getLayoutInflater().inflate(R.layout.item_tabhost,null);
            mTabImagv = (ImageView) v.findViewById(R.id.tab_imgv);
            mTabTv = (TextView) v.findViewById(R.id.tab_tv);
            mTabImagv.setImageResource(tabImageSelector[i]);
            mTabTv.setText(tabNames[i]);
            mTabTv.setTextColor(Integer.valueOf(tabTextSelector));
            //如果是新建微博控件，则隐藏其对应的标题
            if("".equals(tabNames[i])) {
                mTabTv.setVisibility(View.GONE);
            }
            //将前面绘制好的页面，通过newTabSpec方法新建一个tabSpec页面
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec("" + i).setIndicator(v);
            //此处进行传值。作为该页面的tag值
            Bundle args = new Bundle();
//            args.putSerializable("toolbarX", mToolbarX);
            args.putString("title",tabNames[i]);
            //加入到FragmentTabhost中去
            mTabHost.addTab(tabSpec,mFragmentArray[i],args);

        }
        /**
         * 点击tabhost时判断当前页面是否为首页，
         * 如果是则将RecyclerView移动到第一个item，并请求新的数据刷新界面
         */
        mTabHost.getTabWidget().getChildTabViewAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentTab = mTabHost.getCurrentTab();
                //当前页面为首页，进行刷新
                if(0 == currentTab){
                    int currentItem = HomeFragment.mViewPager.getCurrentItem();
                    //判断当前为ViewPager的哪个页面
                    switch (currentItem){
                        case 0:
                            FriendsFragment.mSwipeLayout.setRefreshing(true);
                            FriendsFragment friendsFragment = FriendsFragment.newInstance();
                            friendsFragment.moveRecylvToPosition(0);
                            friendsFragment.startRequestData(UrlUtil.HOME_TIMELINE, StaticUtil.REFRESH_DOWN_SIGN);
                            break;
                        case 1:
                            HotFragment.mSwipeLayout.setRefreshing(true);
                            HotFragment hotFragment = HotFragment.newInstance();
                            hotFragment.moveRecylvToPosition(0);
                            hotFragment.startRequestData(UrlUtil.PUBLIC_TIMELINE, StaticUtil.REFRESH_DOWN_SIGN);
                            break;
                    }
                }else {
                    //当前非首页，切换到首页，显示首页内容
                    mTabHost.setCurrentTab(0);
                }
//                LUtils.toastShort(MainActivity.this," " + v.getId());
            }
        });
        //点击新建微博，弹出一个DialogFragment
        mTabHost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewWeboFragment dialog = NewWeboFragment.newInsatance();
                dialog.show(getSupportFragmentManager(),"new_webo");
            }
        });
        //监听Tab变化，设置Toolbar内容
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //此处的tabId即为前面public void addTab (TabSpec tabSpec, Class<?> clss, Bundle args) 中传入的args
                int id = Integer.parseInt(tabId);
                switch (id){
                    case 0:
                        mToolbarX.hide();
                        break;
                    case 1:
                        mToolbarX.show();
                        mMidTv.setText(tabNames[id]);
                        break;
                    case 2:
                        mToolbarX.show();
                        mLeftTv.setText("取消");
                        mMidTv.setText("新微博");
                        mRightTv.setText("发送");
                        break;
                    case 3:
                        mToolbarX.hide();
                        break;
                    case 4:
                        mToolbarX.show();
                        mMidTv.setText(tabNames[id]);
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
