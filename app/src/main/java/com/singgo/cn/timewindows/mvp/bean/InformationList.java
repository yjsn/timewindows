package com.singgo.cn.timewindows.mvp.bean;

import com.singgo.cn.timewindows.entity.Base;

import java.util.List;

/**
 * Created by hxz on 2017/1/9.
 */

public class InformationList extends Base {
    private boolean error;
    private List<Information> results;

    public InformationList(){}

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Information> getResults() {
        return results;
    }

    public void setResults(List<Information> results) {
        this.results = results;
    }
}
