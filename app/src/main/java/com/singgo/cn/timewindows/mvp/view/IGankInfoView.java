package com.singgo.cn.timewindows.mvp.view;

import com.singgo.cn.timewindows.mvp.bean.InformationList;
import com.singgo.cn.timewindows.mvp.bean.MzImgList;

/**
 * Created by hxz on 2017/1/9.
 */

public interface IGankInfoView {
    int count();
    int page();
    void getInformationList(InformationList list);
}
