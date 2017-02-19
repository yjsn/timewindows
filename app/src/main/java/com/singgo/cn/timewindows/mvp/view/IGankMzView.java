package com.singgo.cn.timewindows.mvp.view;

import com.singgo.cn.timewindows.mvp.bean.MzImgList;

/**
 * Created by hxz on 2017/1/7.
 */

public interface IGankMzView {
    int count();
    int page();
    void getMzImgList(MzImgList mzImgList);
}
