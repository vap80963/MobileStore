package com.tin.chigua.mywebo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.tin.chigua.mywebo.net.BaseNetwork;
import com.tin.chigua.mywebo.adapter.BaseRclvAdapter;
import com.tin.chigua.mywebo.bean.HttpResponse;
import com.tin.chigua.mywebo.bean.StatusesBean;
import com.tin.chigua.mywebo.constant.Constants;
import com.tin.chigua.mywebo.constant.ParameterKeySet;
import com.tin.chigua.mywebo.utils.LUtils;
import com.tin.chigua.mywebo.utils.MySharePreferences;
import com.tin.chigua.mywebo.utils.UrlUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 5/6/2017.
 */

public class HotFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private BaseRclvAdapter mAdapter;
    private List<StatusesBean> mList;
    volatile boolean isFinished = false;

    private AsyncWeiboRunner mWeiboRunner;
    private WeiboParameters mParameters;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rcylv_common,container,false);
        init();
        startRequestData(UrlUtil.PUBLIC_TIMELINE,false);
        initRecylV(view);
        return view;
    }

    private void startRequestData(String url,final boolean isLoadMore) {

        new BaseNetwork(getActivity(), url) {
            public WeiboParameters onPrepare() {
                mParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, MySharePreferences.getToken(getActivity()));
                mParameters.put(ParameterKeySet.PAGE, page);
                mParameters.put(ParameterKeySet.COUNT, 20);
                return mParameters;
            }

            public void onFinish(HttpResponse response, boolean success) {
                if (success) {
//                    LUtils.logD(getActivity(),response.response.toString());
                    List<StatusesBean> list = new ArrayList<>();
                    final GsonBuilder gsonBuilder = new GsonBuilder();
                    final Gson gson = gsonBuilder.create();
                    Type type = new TypeToken<ArrayList<StatusesBean>>(){}.getType();
                    list = gson.fromJson(response.response, type);
                    if(!isLoadMore){
                        mList.clear();
                    }
                    mList.addAll(list);
//                    LUtils.toastShort(getActivity(),"list.size = " + mList.size());
                    mAdapter.notifyDataSetChanged();
                } else {
                    LUtils.logE(getActivity(),response.message);
                }
            }
        }.get();

    }

    private void init() {
        mList = new ArrayList<StatusesBean>();
        mParameters = new WeiboParameters(Constants.APP_KEY);
        mWeiboRunner = new AsyncWeiboRunner(getActivity());
        mParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, MySharePreferences.getToken(getActivity()));

    }

    private void initRecylV(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.home_common_rcylV);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new BaseRclvAdapter(getActivity(),mList);
        mAdapter.setOnItemClickLitener(new BaseRclvAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }

    public static Fragment newInstance(){
        HotFragment fragment = null;
        if (null == fragment){
            fragment = new HotFragment();
        }
        return fragment;
    }
}
