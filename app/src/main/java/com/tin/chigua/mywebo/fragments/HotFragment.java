package com.tin.chigua.mywebo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.adapter.CommonRclvAdapter;
import com.tin.chigua.mywebo.bean.HttpResponse;
import com.tin.chigua.mywebo.bean.StatusesBean;
import com.tin.chigua.mywebo.cache.ConfigCache;
import com.tin.chigua.mywebo.constant.Constants;
import com.tin.chigua.mywebo.constant.ParameterKeySet;
import com.tin.chigua.mywebo.constant.StaticUtil;
import com.tin.chigua.mywebo.constant.UrlUtil;
import com.tin.chigua.mywebo.net.BaseNetwork;
import com.tin.chigua.mywebo.utils.GsonUtil;
import com.tin.chigua.mywebo.utils.LUtils;
import com.tin.chigua.mywebo.utils.MySharePreferences;
import com.tin.chigua.mywebo.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.tin.chigua.mywebo.fragments.FriendsFragment.isReclvIdle;

/**
 * Created by hasee on 5/6/2017.
 */

public class HotFragment extends BaseFragment {

    public static SwipeRefreshLayout mSwipeLayout;

    private static MyRecyclerView mRecyclerView;
    private static CommonRclvAdapter mAdapter;
    private static List<StatusesBean> mList;
    private static Context mContext;
    private LinearLayoutManager mManager;
    private static int lastVisibleItem = 0;
    private static int count = 30;

    private  static WeiboParameters mParameters;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rcylv_common,container,false);
        init();
        initRcylView(view);
        initSwipLayout(view);
        return view;
    }


    public void startRequestData(final String url, final int loadMode) {

        new BaseNetwork(mContext, url) {
            public WeiboParameters onPrepare() {
                if(loadMode == StaticUtil.REFRESH_DOWN_SIGN){
                    mSwipeLayout.setRefreshing(true);
                }
                mParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, MySharePreferences.getToken(mContext));
                mParameters.put(ParameterKeySet.PAGE, 1);
                if(loadMode == StaticUtil.MORE_DOWN_SIGN){
                    count += 10;
                }
                mParameters.put(ParameterKeySet.COUNT, count);
                return mParameters;
            }

            public void onFinish(HttpResponse response, boolean success) {
                if (success) {
//                    LUtils.logD(mContext,response.response.toString());
                    //解析Json数据，并放入List集合中
                    List<StatusesBean> list = GsonUtil.analyzeJson(response.response);
                    //用JsonObject类把jsonArray包装
                    GsonUtil.wrapperToJsonObject(response.response,url);

                    //用分支判断当前的请求数据状态，决定本次的请求结果的处理方式
                    switch (loadMode){
                        case StaticUtil.FIRST_DOWN_SIGN:
                            mList.clear();
                            mList.addAll(list);
                            break;
                        case StaticUtil.REFRESH_DOWN_SIGN:
                            updateListData(list);
                            break;
                        case StaticUtil.MORE_DOWN_SIGN:
                            addMoreListData(list);
                            break;
                        default:
                            break;
                    }
                    //设置刷新滚动条停止转动
                    if (mSwipeLayout.isRefreshing()){
                        mSwipeLayout.setRefreshing(false);
                    }
//                    LUtils.toastShort(mContext,"list.size = " + mList.size());
                    CommonRclvAdapter.changeMoreStatus(CommonRclvAdapter.LOAD_COMPLETE);
                    mAdapter.notifyDataSetChanged();
                } else {
                    LUtils.logE(mContext,response.message);
                    LUtils.toastShort(mContext,"刷新微博失败，请重试");
                }
            }
        }.get();
    }

    private static void addMoreListData(List<StatusesBean> list){
        if (null != list && list.size() > 0 && mList.size() > 0){
            int position = mList.size() - 1;
            Iterator<StatusesBean> iterator = list.iterator();
            while (iterator.hasNext()){
                StatusesBean statusesBean = iterator.next();
                if (statusesBean.id == mList.get(position).id && iterator.hasNext()){
                    do {
                        statusesBean = iterator.next();
                        mList.add(statusesBean);
                    } while (iterator.hasNext());
                    break;
                }
            }
        }else {
            mList.addAll(list);
        }

    }

    private static void updateListData(List<StatusesBean> list) {
        if (null != list && list.size() > 0 && mList.size() > 0){
//            int position = 0;
//            long firstId = mList.get(0).id;
//            while(firstId != list.get(position).id){
//                mList.add(position,list.get(position));
//                position++;
//            }
            mList.clear();
            mList.addAll(list);
        }else {
            mList.addAll(list);
        }
    }

    public void moveRecylvToPosition(int position){
        CommonRclvAdapter.MoveToPosition(mRecyclerView,position);
    }

    private void init() {
        mList = new ArrayList<>();
        mContext = getActivity();
        mParameters = new WeiboParameters(Constants.APP_KEY);
        String cacheString =  ConfigCache.getConfigCache(UrlUtil.PUBLIC_TIMELINE);
        if (TextUtils.isEmpty(cacheString)) {
            startRequestData(UrlUtil.PUBLIC_TIMELINE, StaticUtil.FIRST_DOWN_SIGN);  //开始请求数据
        }else {
            JsonArray jsonArray = GsonUtil.getJsonArray(cacheString);
            mList = GsonUtil.analyzeJson(jsonArray);
        }
    }

    private void initRcylView(View view) {

        mRecyclerView = (MyRecyclerView) view.findViewById(R.id.home_common_rcylV);
        mManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mAdapter = new CommonRclvAdapter(mContext,mList);
        mAdapter.setOnItemClickLitener(new CommonRclvAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                LUtils.toastShort(mContext,"position = " + position);
            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    isReclvIdle = true;
                    mAdapter.notifyDataSetChanged();
                }else {
                    isReclvIdle = false;
                }if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()){
                    CommonRclvAdapter.changeMoreStatus(CommonRclvAdapter.LOADING_MORE);
                    LUtils.logE(getActivity(),"Hot Fragment lastVisibleItem = " + lastVisibleItem);
                    LUtils.logE(getActivity(),"Hot Fragment mAdapter.getItemCount() = " + mAdapter.getItemCount());
                    startRequestData(UrlUtil.PUBLIC_TIMELINE,StaticUtil.MORE_DOWN_SIGN);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mManager.findLastVisibleItemPosition();
            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initSwipLayout(View view) {
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_common_swip_ll);
        mSwipeLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));
        mSwipeLayout.setColorSchemeColors(getResources().getColor(R.color.weboblue)
                ,getResources().getColor(R.color.weboblue)
                ,getResources().getColor(R.color.white));
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRequestData(UrlUtil.PUBLIC_TIMELINE,StaticUtil.REFRESH_DOWN_SIGN);

            }
        });
    }
    public static HotFragment newInstance(){
        HotFragment fragment = null;
        if (null == fragment){
            fragment = new HotFragment();
        }
        return fragment;
    }
}
