package com.singgo.cn.timewindows.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.singgo.cn.timewindows.app.AppManager;
import com.singgo.cn.timewindows.listener.BaseViewInterface;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;

/**
 * Created by hxz on 2016/12/16.
 */

public class BaseMainActivity extends AutoLayoutActivity implements BaseViewInterface,View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        // 通过注解绑定控件
        ButterKnife.bind(this);
        initView();
        initData();
    }

    /*
    得到layout
     */
    protected int getLayoutId(){
        return 0;
    }


    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
