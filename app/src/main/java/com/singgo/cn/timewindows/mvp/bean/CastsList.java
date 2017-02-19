package com.singgo.cn.timewindows.mvp.bean;

import com.singgo.cn.timewindows.entity.BaseList;

import java.util.List;

/**
 * Created by hxz on 2016/12/8.
 */

public class CastsList extends BaseList<Casts> {

    @Override
    public List<Casts> getSubjects() {
        return subjects;
    }
}
