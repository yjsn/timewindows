package com.singgo.cn.timewindows.mvp.view;

import com.singgo.cn.timewindows.mvp.bean.Celebrity;

/**
 * Created by hxz on 2016/12/25.
 */

public interface ICastsInfoView{
   String getCastsId();
    void getCastsInfo(Celebrity celebrity);
}
