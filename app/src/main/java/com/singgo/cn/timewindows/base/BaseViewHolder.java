package com.singgo.cn.timewindows.base;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by hxz on 2016/12/17.
 * viewholder 基类
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mView;  //缓存item内的view列表
    private View mConvertView;        //当前要对比的view,避免重复findById
    private Context context;
    public int position;

    public BaseViewHolder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        this.mConvertView = itemView;
        mView = new SparseArray<View>();
    }


    /*
    拿到实例
     */
//    public static BaseViewHolder get(Context context, ViewGroup parent, int layoutId){
//        View itemView = LayoutInflater.from(context).inflate(layoutId,parent,false);
//        BaseViewHolder viewHolder = new BaseViewHolder(context,itemView,parent);
//        return viewHolder;
//    }

    public static BaseViewHolder createViewHolder(Context context, View itemView)
    {
        BaseViewHolder holder = new BaseViewHolder(context, itemView);
        return holder;
    }

    public static BaseViewHolder createViewHolder(Context context,
                                              ViewGroup parent, int layoutId)
    {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        BaseViewHolder holder = new BaseViewHolder(context, itemView);
        return holder;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view = mView.get(viewId);
        if(view == null){
            view = mConvertView.findViewById(viewId);
            mView.put(viewId,view);
        }
        return (T) view;
    }

    public  void updatePosition(int position){
        this.position = position;
    }

    public View getConvertView(){
        return mConvertView;
    }

    public BaseViewHolder setText(int id ,String text){
        TextView textView = getView(id);
        textView.setText(text);
        return this;
    }

    public BaseViewHolder setFSImage(int id,String url){
        SimpleDraweeView simpleDraweeView = getView(id);
        simpleDraweeView.setImageURI(Uri.parse(url));
        return this;
    }

    public BaseViewHolder setViewVisibility(int id){
        View view = getView(id);
        view.setVisibility(View.VISIBLE);
        return this;
    }

    public BaseViewHolder setViewGone(int id){
        View view = getView(id);
        view.setVisibility(View.GONE);
        return this;
    }

    public BaseViewHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener)
    {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

}
