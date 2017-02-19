package com.singgo.cn.timewindows.mvp.biz.impl;

import android.util.Log;

import com.singgo.cn.timewindows.mvp.bean.Celebrity;
import com.singgo.cn.timewindows.mvp.bean.MovieList;
import com.singgo.cn.timewindows.mvp.bean.Subjects;
import com.singgo.cn.timewindows.mvp.view.ICastsInfoView;
import com.singgo.cn.timewindows.mvp.view.IMovieItemView;
import com.singgo.cn.timewindows.mvp.view.IMovieView;
import com.singgo.cn.timewindows.retrofit.ApiFactory;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hxz on 2016/12/8.
 */

public class DbMovieBiz {
    public void getInTheaters(final IMovieView view,final int type){
        Call<MovieList>call = ApiFactory.getDBApi().getInTheaters(view.count(),view.start());
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                view.getMovieList(response.body(),type);
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

            }
        });
    }

    public  void getComingSong(final IMovieView view,final int type){
        Call<MovieList>call = ApiFactory.getDBApi().getComingSoon(view.count(),view.start());
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                view.getMovieList(response.body(),type);
            }
            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
            }
        });
    }

    public  void getTop250(final IMovieView view,final int type){
        Call<MovieList>call = ApiFactory.getDBApi().getTop250(view.count(),view.start());
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                view.getMovieList(response.body(),type);
            }
            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
            }
        });
    }

    /**
     * 得到单个电影信息
      */
    public void getMovie(final IMovieItemView view){
        Call<Subjects>call = ApiFactory.getDBApi().getMovie(view.getMovieId());
        call.enqueue(new Callback<Subjects>() {
            @Override
            public void onResponse(Call<Subjects> call, Response<Subjects> response) {
                view.getMovieItem(response.body());
            }
            @Override
            public void onFailure(Call<Subjects> call, Throwable t) {

            }
        });
    }

    /**
     * 得到单个演员信息
     * @param view
     */
    public void getCelebrity(final ICastsInfoView view){
        Call<Celebrity>call = ApiFactory.getDBApi().getCelebrity(view.getCastsId());
        call.enqueue(new Callback<Celebrity>() {
            @Override
            public void onResponse(Call<Celebrity> call, Response<Celebrity> response) {
                view.getCastsInfo(response.body());
            }

            @Override
            public void onFailure(Call<Celebrity> call, Throwable t) {

            }
        });
    }

    public void getSearchList(final IMovieView view){
        Call<MovieList>call = ApiFactory.getDBApi().getSearchList(view.getSearch(),view.count(),view.start());
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                view.getMovieList(response.body(),-1);
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

            }
        });
    }


    public void getTagList(final IMovieView view){
        Call<MovieList>call = ApiFactory.getDBApi().getTagList(view.getTags(),view.count(),view.start());
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                view.getMovieList(response.body(),-1);
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

            }
        });
    }

}
