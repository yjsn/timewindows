package com.singgo.cn.timewindows.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singgo.cn.timewindows.R;
import com.singgo.cn.timewindows.adapter.CommonAdapter;
import com.singgo.cn.timewindows.adapter.LoadMoreWrapper;
import com.singgo.cn.timewindows.base.BaseFragment;
import com.singgo.cn.timewindows.base.BaseRecyclerView;
import com.singgo.cn.timewindows.base.BaseViewHolder;
import com.singgo.cn.timewindows.mvp.bean.MzImg;
import com.singgo.cn.timewindows.mvp.bean.MzImgList;
import com.singgo.cn.timewindows.mvp.presenter.GankPresenter;
import com.singgo.cn.timewindows.mvp.view.IGankMzView;
import com.singgo.cn.timewindows.ui.BigImgViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hxz on 2017/1/7.
 */

public class MzImgFragment extends BaseFragment implements IGankMzView ,BaseRecyclerView.OnItemClickListener{
    @BindView(R.id.recycler_view)
    BaseRecyclerView recyclerView;
    private int count = 10;
    private int page = 1;

    private GankPresenter presenter = new GankPresenter();
    private List<MzImg> mList = new ArrayList<>();

    @Override
    public void initView(View view) {
        super.initView(view);
        recyclerView.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        presenter.getMzImgList(this);
    }

    private void initRecyclerView() {
        recyclerView.setStaggeredGridLayoutManager(2);
        CommonAdapter<MzImg>adapter = new CommonAdapter<MzImg>(getActivity(),R.layout.fragment_mz_img_item,mList) {
            @Override
            public void convert(BaseViewHolder holder, MzImg mzImg) {
                holder.setFSImage(R.id.sdv_img,mzImg.getUrl());
            }
        };
        recyclerView.hasLoadMore(adapter);
        recyclerView.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                presenter.getMzImgList(MzImgFragment.this);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mz_img;
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
    public void getMzImgList(MzImgList mzImgList) {
        setRefreshingFalse();
        if(page == 1){
            mList.clear();
            mList.addAll(mzImgList.getResults());
            if(!isRefreshing){
                initRecyclerView();
            }else{
                recyclerView.notifyData();
            }
        }else {
            mList.addAll(mzImgList.getResults());
            recyclerView.notifyData();
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        isRefreshing = true;
        presenter.getMzImgList(this);
    }

    private ArrayList<String>list = new ArrayList<>();
    @Override
    public void onItemClick(int position, View itemView) {
        Bundle bundle = new Bundle();
        bundle.putInt("code",position); //位置
        bundle.putInt("selet", 2);// 2,大图显示当前页数，1,头像，不显示页数
        list.clear();
        for(MzImg mz:mList){
            list.add(mz.getUrl());
        }
        bundle.putStringArrayList("imageuri",list);
        Intent intent = new Intent(getActivity(), BigImgViewActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }
}
