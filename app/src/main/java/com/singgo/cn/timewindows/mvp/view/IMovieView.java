package com.singgo.cn.timewindows.mvp.view;

import com.singgo.cn.timewindows.mvp.bean.MovieList;

/**
 * Created by hxz on 2016/12/8.
 */

public interface IMovieView {
    //MovieList getMovieList(int count,int start);
    int count();
    int start();
    String getSearch();
    String getTags();
    void getMovieList(MovieList list,int type);

}
