package com.singgo.cn.timewindows.mvp.bean;

import com.singgo.cn.timewindows.entity.Base;

/**
 * Created by hxz on 2016/12/8.
 */
public class Casts extends Base {
    private String id;
    private String name;
    private String alt;
    private Avatars avatars;

    public Casts(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Avatars getAvatars() {
        return avatars;
    }

    public void setAvatars(Avatars avatars) {
        this.avatars = avatars;
    }
}
