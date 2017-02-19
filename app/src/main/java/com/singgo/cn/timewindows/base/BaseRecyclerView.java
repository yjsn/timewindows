package com.singgo.cn.timewindows.base;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.singgo.cn.timewindows.R;
import com.singgo.cn.timewindows.adapter.CommonAdapter;
import com.singgo.cn.timewindows.adapter.LoadMoreWrapper;
import com.singgo.cn.timewindows.widget.DividerItemDecoration;

/**
 * Created by hxz on 2016/12/18.
 */

public class BaseRecyclerView extends RecyclerView {
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnScrollListener onScrollListener;
    private GestureDetector gestureDetector; //自带手势识别
   // private LoadMoreWrapper.OnLoadMoreListener onLoadMoreListener;
    private LoadMoreWrapper moreWrapper;
    private boolean isHasLoadMoreWrapper;    //是否使用了下拉加载组件
    private Adapter mAdapter;

    public BaseRecyclerView(Context context){
        this(context,null);
    }

    public BaseRecyclerView(Context context,@Nullable AttributeSet attrs){
        this(context,attrs,0);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initListener();
    }

    private void initListener() {
        gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                if(onItemLongClickListener!=null){
                    View itemView = findChildViewUnder(e.getX(),e.getY());
                    if(itemView != null){
                        int posititon = getChildLayoutPosition(itemView);
                        onItemLongClickListener.onItemLongClick(posititon,itemView);
                    }
                }
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if(onItemClickListener!=null){
                    View itemView = findChildViewUnder(e.getX(),e.getY());
                    if(itemView!=null){
                        int position = getChildLayoutPosition(itemView);
                        onItemClickListener.onItemClick(position,itemView);
                    }
                    return true;
                }
                return super.onSingleTapUp(e);
            }


        });
        addOnItemTouchListener(new SimpleOnItemTouchListener(){
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if(gestureDetector.onTouchEvent(e)){
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * recylerview是否可以下拉加载更多
     * @param adapter
     */
    public void hasLoadMore(CommonAdapter adapter){
        isHasLoadMoreWrapper = true;
        moreWrapper = new LoadMoreWrapper(adapter);
        moreWrapper.setLoadMoreView(R.layout.load_more);
    }

    private boolean hideLoadFlag;

    /**
     * 没有更多的数据了，隐藏footview
     */
    public void hideLoadMore(){
        if(!hideLoadFlag){
            Message msg = new Message();
            hideLoadFlag = true;
            msg.what = 1;
            handler.sendMessage(msg);
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                moreWrapper.hideLoadMoreView();
            }
        }
    };

    /**
     * 重写setApapter
     * @param adapter
     */
    public void setAdapter(Adapter adapter) {
        if(isHasLoadMoreWrapper){
            super.setAdapter(moreWrapper);
        }else {
            mAdapter = adapter;
            super.setAdapter(adapter);
        }
    }

    /**
     * 刷新adpter
     */
    public void notifyData(){
        if(isHasLoadMoreWrapper) {
            moreWrapper.notifyDataSetChanged();
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 网格布局
     * @param rows
     */
    public void setGridLayoutManager(int rows){
        GridLayoutManager manager = new GridLayoutManager(context, rows);
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        this.setLayoutManager(manager);
        this.setHasFixedSize(true);
        this.setNestedScrollingEnabled(false);
    }

    /**
     * 线性布局
     */
    public void setLineLayoutManager(){
        this.setLayoutManager(new LinearLayoutManager(context));
    }

    /**
     * 横向线性布局
     */
    public void setHorizontalLayoutManager(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.setLayoutManager(layoutManager);
    }

    /**
     * 瀑布布局
     */
    public void setStaggeredGridLayoutManager(int rows){
        this.setLayoutManager(new StaggeredGridLayoutManager(rows,StaggeredGridLayoutManager.VERTICAL));
    }

    /**
     * 绘制边框
     */
    public void setLineDecoration(){
        this.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL_LIST));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    /**
     * Item项点击事件
     */
    public interface OnItemClickListener {

        void onItemClick(int position, View itemView);
    }

    /**
     * Item项长按点击事件
     */
    public interface OnItemLongClickListener {

        void onItemLongClick(int position, View itemView);
    }

    public void setOnLoadMoreListener(LoadMoreWrapper.OnLoadMoreListener onLoadMoreListener) {
        //this.onLoadMoreListener = onLoadMoreListener;
        moreWrapper.setOnLoadMoreListener(onLoadMoreListener);
    }
}
