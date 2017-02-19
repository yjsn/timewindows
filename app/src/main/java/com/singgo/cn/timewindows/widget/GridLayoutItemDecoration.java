package com.singgo.cn.timewindows.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hxz on 2016/12/17.
 * 专为gridLayoutItemDecoration设置的间距
 */

public class GridLayoutItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    public  GridLayoutItemDecoration(int space){
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = space;
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

    }
}
