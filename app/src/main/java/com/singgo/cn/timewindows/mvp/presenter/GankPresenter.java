package com.singgo.cn.timewindows.mvp.presenter;

import com.singgo.cn.timewindows.mvp.biz.impl.GankBiz;
import com.singgo.cn.timewindows.mvp.view.IGankInfoView;
import com.singgo.cn.timewindows.mvp.view.IGankMzView;

/**
 * Created by hxz on 2017/1/7.
 */

public class GankPresenter {
    private GankBiz gankBiz = new GankBiz();
    public  void getMzImgList(IGankMzView view){
        gankBiz.getMzImgList(view);
    }

    public void getInformationList(IGankInfoView view){
        gankBiz.getInformationList(view);
    }
}
