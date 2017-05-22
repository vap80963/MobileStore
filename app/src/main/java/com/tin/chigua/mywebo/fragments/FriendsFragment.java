package com.tin.chigua.mywebo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.adapter.BaseRclvAdapter;
import com.tin.chigua.mywebo.bean.HttpResponse;
import com.tin.chigua.mywebo.bean.StatusesBean;
import com.tin.chigua.mywebo.constant.Constants;
import com.tin.chigua.mywebo.constant.ParameterKeySet;
import com.tin.chigua.mywebo.constant.StaticUtil;
import com.tin.chigua.mywebo.net.BaseNetwork;
import com.tin.chigua.mywebo.utils.LUtils;
import com.tin.chigua.mywebo.utils.MySharePreferences;
import com.tin.chigua.mywebo.utils.UrlUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hasee on 5/6/2017.
 */

public class FriendsFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    public static SwipeRefreshLayout mSwipeLayout;
    private static BaseRclvAdapter mAdapter;
    private static List<StatusesBean> mList;
    private static Context mContext;

    private AsyncWeiboRunner mWeiboRunner;
    private static WeiboParameters mParameters;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rcylv_common,container,false);
        init();
        initSwipLayout(view);
        startRequestData(UrlUtil.HOME_TIMELINE, StaticUtil.FIRST_DOWN_SIGN);
        initRcylView(view);
        return view;
    }


    public static void startRequestData(String url,final int loadMode) {

        new BaseNetwork(mContext, url) {
            public WeiboParameters onPrepare() {
                mParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, MySharePreferences.getToken(mContext));
                mParameters.put(ParameterKeySet.PAGE, 1);
                mParameters.put(ParameterKeySet.COUNT, 20);
                return mParameters;
            }

            public void onFinish(HttpResponse response, boolean success) {
                if (success) {
//                    LUtils.logD(mContext,response.response.toString());
                    List<StatusesBean> list = new ArrayList<>();
                    final GsonBuilder gsonBuilder = new GsonBuilder();
                    final Gson gson = gsonBuilder.create();
                    Type type = new TypeToken<ArrayList<StatusesBean>>(){}.getType();
                    list = gson.fromJson(response.response, type);
//                    if(!isLoadMore){
//                        mList.clear();
//                    }
//                    mList.addAll(list);
                    switch (loadMode){
                        case StaticUtil.FIRST_DOWN_SIGN:
                            mList.clear();
                            mList.addAll(list);
                            break;
                        case StaticUtil.REFRESH_DOWN_SIGN:
                            updateListData(list);
                            break;
                        case StaticUtil.MORE_DOWN_SIGN:
                            mList.addAll(list);
                            break;
                        default:
                            break;
                    }
                    if (mSwipeLayout.isRefreshing()){
                        mSwipeLayout.setRefreshing(false);
                    }
//                    LUtils.toastShort(mContext,"list.size = " + mList.size());
                    mAdapter.notifyDataSetChanged();
                } else {
                    LUtils.logE(mContext,response.message);
                }
            }
        }.get();
    }

    private static void updateListData(List<StatusesBean> list) {
        int position = 0;
        long firstId = mList.get(0).id;
        while(firstId != list.get(position).id){
            mList.add(position,list.get(position));
            position++;
        }
    }

    private void init() {
        mList = new LinkedList<>();
        mContext = getActivity();
        mWeiboRunner = new AsyncWeiboRunner(mContext);
        mParameters = new WeiboParameters(Constants.APP_KEY);

    }

    private void initRcylView(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.home_common_rcylV);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mAdapter = new BaseRclvAdapter(mContext,mList);
        mAdapter.setOnItemClickLitener(new BaseRclvAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                LUtils.toastShort(mContext,"position = " + position);
            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initSwipLayout(View view) {
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_common_swip_ll);
        mSwipeLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));
        mSwipeLayout.setColorSchemeColors(getResources().getColor(R.color.weboorange)
                ,getResources().getColor(R.color.weboorange)
                ,getResources().getColor(R.color.white));
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRequestData(UrlUtil.HOME_TIMELINE,StaticUtil.REFRESH_DOWN_SIGN);

            }
        });
    }

    public static Fragment newInstance(){
        FriendsFragment fragment = null;
        if(null == fragment){
            fragment = new FriendsFragment();
        }
        return fragment;
    }
}
