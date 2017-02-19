package com.singgo.cn.timewindows.mvp.biz;

import com.singgo.cn.timewindows.mvp.bean.InformationList;
import com.singgo.cn.timewindows.mvp.bean.MovieList;
import com.singgo.cn.timewindows.mvp.bean.MzImgList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by hxz on 2017/1/7.
 */

public interface IGankBiz {
    /**
     count 单页数
     start 开始个数
     */
    @GET("福利/{count}/{page}")
    Call<MzImgList> getMzImgList(@Path("count") int count, @Path("page") int start);

    @GET("all/{count}/{page}")
    Call<InformationList> getInformationList(@Path("count") int count, @Path("page") int start);

}
