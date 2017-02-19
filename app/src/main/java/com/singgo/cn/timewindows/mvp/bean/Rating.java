package com.singgo.cn.timewindows.mvp.bean;

import com.singgo.cn.timewindows.entity.Base;

/**
 * Created by hxz on 2016/12/8.
 */

public class Rating extends Base {
    private int max; //最高分
    private float average; //评分
    private String stars; //评分人数
    private int min; //最低分

    public Rating(){}

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }


    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }
}
