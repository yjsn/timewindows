package com.singgo.cn.timewindows.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singgo.cn.timewindows.R;
import com.singgo.cn.timewindows.app.AppContext;
import com.singgo.cn.timewindows.listener.BaseFragmentInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hxz on 16/9/5 11:11.
 */
public abstract class BaseFragment extends Fragment implements BaseFragmentInterface,View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {
    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;
    public static final int STATE_NOMORE = 3;
    public static final int STATE_PRESSNONE = 4;// 正在下拉但还没有到刷新的状态
    public static int mState = STATE_NONE;
    protected LayoutInflater mInflater;
    private boolean isFirstVisible = true;   //第一次可见
    private boolean isFirstInvisible = true;  //第一次不可见
    private boolean isPrepared;              //准备

    protected String TAG;

    //@BindView(R.id.sw_refresh)
    protected SwipeRefreshLayout swipeRefreshLayout;

    protected boolean isRefreshing = false;  //判断是否是刷新

    public AppContext getApplication() {
        return (AppContext) getActivity().getApplication();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = inflater;
        View view = inflater.inflate(getLayoutId(),container,false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sw_refresh);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        if(!hasRefreshLayout()){
            swipeRefreshLayout.setEnabled(false);
        }else {
            initSwipeRefreshLayout();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    private synchronized void initPrepare() {
        if (isPrepared) {
            initData();
        } else {
            isPrepared = true;
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //Log.i("tag",TAG+isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    protected  void onUserVisible(){};
    private void onFirstUserInvisible() {}
    protected  void onUserInvisible(){};

    protected int getLayoutId() {
        return 0;
    }

    private void initSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        showRefreshLayout();
    }


    protected boolean hasRefreshLayout(){
        return true;
    }

    /**
     * 自动刷新
     */
    protected void showRefreshLayout(){
        //swipeRefreshLayout.measure(0,0);
        swipeRefreshLayout.setRefreshing(true);
    }

    /**
     * 设置刷新状态为false
     */
    protected void setRefreshingFalse(){
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * 设置刷新控件是否可用
     * @param f
     */
    protected void setRefreshEnableFalse(boolean f){
        if(!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setEnabled(f);
        }
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onRefresh() {

    }
}
