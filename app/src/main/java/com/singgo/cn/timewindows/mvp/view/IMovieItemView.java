package com.singgo.cn.timewindows.mvp.view;

import com.singgo.cn.timewindows.mvp.bean.Subjects;

/**
 * Created by hxz on 2016/12/21.
 */

public interface IMovieItemView {
    String getMovieId();
    void getMovieItem(Subjects subjects);
}
