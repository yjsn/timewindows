package com.singgo.cn.timewindows.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.singgo.cn.timewindows.R;
import com.singgo.cn.timewindows.adapter.CommonAdapter;
import com.singgo.cn.timewindows.adapter.LoadMoreWrapper;
import com.singgo.cn.timewindows.base.BaseFragment;
import com.singgo.cn.timewindows.base.BaseRecyclerView;
import com.singgo.cn.timewindows.base.BaseViewHolder;
import com.singgo.cn.timewindows.mvp.bean.Information;
import com.singgo.cn.timewindows.mvp.bean.InformationList;
import com.singgo.cn.timewindows.mvp.presenter.GankPresenter;
import com.singgo.cn.timewindows.mvp.view.IGankInfoView;
import com.singgo.cn.timewindows.ui.webview.WebViewActivity;
import com.singgo.cn.timewindows.util.TimeZoneUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hxz on 2017/1/1.
 */

public class InformationFragment extends BaseFragment implements IGankInfoView,BaseRecyclerView.OnItemClickListener {
    @BindView(R.id.recycler_view)
    BaseRecyclerView recyclerView;

    private int count = 20;
    private int page = 1;
    private List<Information> mList = new ArrayList<>();
    private GankPresenter presenter = new GankPresenter();
    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {
        presenter.getInformationList(this);
    }

    private void initRecyclerView() {
        recyclerView.setOnItemClickListener(this);
        recyclerView.setLineLayoutManager();
        recyclerView.setLineDecoration();
        CommonAdapter<Information>adapter = new CommonAdapter<Information>(getActivity(),R.layout.fragment_information_item,mList) {
            @Override
            public void convert(BaseViewHolder holder, Information information) {
                if(!information.getType().equals("福利")){
                    holder.setText(R.id.tv_title,information.getDesc());
                    holder.setViewGone(R.id.sdv_img_pre);
                }else{
                    holder.setFSImage(R.id.sdv_img_pre,information.getUrl());
                    holder.setViewVisibility(R.id.sdv_img_pre);
                }
                holder.setText(R.id.tv_author,information.getWho()+" - "+information.getType());
                holder.setText(R.id.tv_date, TimeZoneUtil.getTranslateTime(information.getPublishedAt()));
            }
        };
        recyclerView.hasLoadMore(adapter);
        recyclerView.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                presenter.getInformationList(InformationFragment.this);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_information;
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public int page() {
        return page;
    }

    @Override
    public void getInformationList(InformationList list) {
        setRefreshingFalse();
        if(page == 1){
            mList.clear();
            mList.addAll(list.getResults());
            if(!isRefreshing){
                initRecyclerView();
            }else{
                recyclerView.notifyData();
            }
        }else {
            mList.addAll(list.getResults());
            recyclerView.notifyData();
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        isRefreshing = true;
        presenter.getInformationList(InformationFragment.this);
    }

    @Override
    public void onItemClick(int position, View itemView) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("mTitle",mList.get(position).getDesc());
        intent.putExtra("mUrl",mList.get(position).getUrl());
        startActivity(intent);
    }
}
