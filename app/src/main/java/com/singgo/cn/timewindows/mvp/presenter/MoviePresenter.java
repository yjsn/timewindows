package com.singgo.cn.timewindows.mvp.presenter;

import com.singgo.cn.timewindows.mvp.biz.impl.DbMovieBiz;
import com.singgo.cn.timewindows.mvp.view.ICastsInfoView;
import com.singgo.cn.timewindows.mvp.view.IMovieItemView;
import com.singgo.cn.timewindows.mvp.view.IMovieView;

/**
 * Created by hxz on 2016/12/8.
 */

public class MoviePresenter {
    private DbMovieBiz movieBiz = new DbMovieBiz();
    public static final int TYPE_IN_THEATERS = 0; //0 为正在热映  1为即将上映  2为经典电影
    public static final int TYPE_COMING_SOON = 1;
    public static final int TYPE_TOP = 2;
    public void getMovieList(IMovieView view,int type){
        if(type == TYPE_IN_THEATERS){
            movieBiz.getInTheaters(view,type);
        }else if(type == TYPE_COMING_SOON){
            movieBiz.getComingSong(view,type);
        }else if(type == TYPE_TOP){
            movieBiz.getTop250(view,type);
        }
    }

    public void getSearchList(IMovieView view){
        movieBiz.getSearchList(view);
    }

    public void getTagList(IMovieView view){
        movieBiz.getTagList(view);
    }

    public void getMovieItem(IMovieItemView view){
        movieBiz.getMovie(view);
    }

    public void getCastsInfo(ICastsInfoView view){movieBiz.getCelebrity(view);}
}
