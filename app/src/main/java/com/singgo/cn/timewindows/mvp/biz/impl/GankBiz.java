package com.singgo.cn.timewindows.mvp.biz.impl;

import com.singgo.cn.timewindows.mvp.bean.InformationList;
import com.singgo.cn.timewindows.mvp.bean.MzImgList;
import com.singgo.cn.timewindows.mvp.view.IGankInfoView;
import com.singgo.cn.timewindows.mvp.view.IGankMzView;
import com.singgo.cn.timewindows.retrofit.ApiFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hxz on 2017/1/7.
 */

public class GankBiz {
    public void getMzImgList(final IGankMzView view){
        Call<MzImgList>call = ApiFactory.getGinkApi().getMzImgList(view.count(),view.page());
        call.enqueue(new Callback<MzImgList>() {
            @Override
            public void onResponse(Call<MzImgList> call, Response<MzImgList> response) {
                view.getMzImgList(response.body());
            }

            @Override
            public void onFailure(Call<MzImgList> call, Throwable t) {

            }
        });
    }

    public void getInformationList(final IGankInfoView view){
        Call<InformationList>call = ApiFactory.getGinkApi().getInformationList(view.count(),view.page());
        call.enqueue(new Callback<InformationList>() {
            @Override
            public void onResponse(Call<InformationList> call, Response<InformationList> response) {
                view.getInformationList(response.body());
            }

            @Override
            public void onFailure(Call<InformationList> call, Throwable t) {

            }
        });
    }
}
