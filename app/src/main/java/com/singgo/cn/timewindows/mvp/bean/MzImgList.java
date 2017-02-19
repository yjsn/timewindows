package com.singgo.cn.timewindows.mvp.bean;

import com.singgo.cn.timewindows.entity.Base;

import java.util.List;

/**
 * Created by hxz on 2017/1/7.
 */

public class MzImgList extends Base {
    private boolean error;
    private List<MzImg>results;

    public MzImgList(){}

    public List<MzImg> getResults() {
        return results;
    }

    public void setResults(List<MzImg> results) {
        this.results = results;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
