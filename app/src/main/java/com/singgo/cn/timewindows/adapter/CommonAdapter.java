package com.singgo.cn.timewindows.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.singgo.cn.timewindows.base.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by hxz on 2016/12/17.
 * recyclerView万能适配器
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context context;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater layoutInflater;

    public CommonAdapter(Context context, int layoutId, List<T> datas){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.mLayoutId = layoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = BaseViewHolder.createViewHolder(context,parent,mLayoutId);
        AutoUtils.autoSize(holder.getConvertView());
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.updatePosition(position);
        convert(holder,mDatas.get(position));
    }

    public abstract void convert(BaseViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
