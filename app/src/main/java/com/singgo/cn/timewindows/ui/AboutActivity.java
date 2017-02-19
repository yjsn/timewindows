package com.singgo.cn.timewindows.ui;

import com.singgo.cn.timewindows.R;
import com.singgo.cn.timewindows.base.BaseActivity;

public class AboutActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }


    @Override
    public void initView() {
        setImgTitle("关于");
        setImageUrl("https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2206088801.jpg");
    }

    @Override
    protected boolean showFloatBtn() {
        return true;
    }

    @Override
    protected boolean showImageToolBar() {
        return true;
    }

}
