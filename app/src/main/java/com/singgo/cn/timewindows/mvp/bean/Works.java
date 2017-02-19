package com.singgo.cn.timewindows.mvp.bean;

import com.singgo.cn.timewindows.entity.Base;

import java.util.List;

/**
 * Created by hxz on 2016/12/25.
 */

public class Works extends Base {
    private List<String> roles ;//角色
    private Subjects subject; //电影信息

    public Works(){}

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Subjects getSubject() {
        return subject;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }
}
