package com.singgo.cn.timewindows.entity;

import java.util.List;

/**
 * Created by hxz on 16/11/15 15:18.
 */
public abstract class BaseList<T extends Base> extends Base {
    protected int count;
    protected int start;
    protected int total;
    protected List<T> subjects;

    public BaseList(){}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public abstract List<T> getSubjects();

    public void setSubjects(List<T> subjects) {
        this.subjects = subjects;
    }
}
