package com.singgo.cn.timewindows.listener;

import android.support.v4.widget.NestedScrollView;

/**
 * Created by hxz on 2016/12/17.
 * scrollview滑动监听事件
 */

public interface ScrollViewListener {
    void onScrollChanged(NestedScrollView scrollView, int x, int y, int oldx, int oldy);
}
