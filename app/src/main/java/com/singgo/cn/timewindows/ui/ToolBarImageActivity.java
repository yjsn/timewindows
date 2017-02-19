package com.singgo.cn.timewindows.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.singgo.cn.timewindows.R;
import com.singgo.cn.timewindows.fragment.SearchFragment;
import com.singgo.cn.timewindows.mvp.bean.Subjects;
import com.singgo.cn.timewindows.util.ImageViewLoad;
import com.singgo.cn.timewindows.util.StateBarTranslucentUtils;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.utils.AutoUtils;


/**
 * Created by hxz on 2016/12/21.
 */

public class ToolBarImageActivity extends AutoLayoutActivity {
    protected Toolbar toolbar_img;
    private ImageView ivHead;
    protected LinearLayout toolbarLayout;
    private LinearLayout mainview;
    protected Toolbar toolbar_simple;
    protected ActionBar actionBar;
    private LinearLayout contentView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StateBarTranslucentUtils.setStateBarTranslucent(this);
        //StateBarTranslucentUtils.setStateBarColor(this);
//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
//        params.height = 400;
//        appBarLayout.setLayoutParams(params);
        initToolBar();
        initContentView();
    }

    private void initToolBar() {
        toolbarLayout = new LinearLayout(this);
        LayoutInflater.from(this).inflate(R.layout.tool_bar_image, toolbarLayout,
                true);
        contentView = (LinearLayout) toolbarLayout.findViewById(R.id.rl_content);
       // contentView.setBackgroundColor(getResources().getColor(R.color.colorBar));
       // LayoutInflater.from(this).inflate(R.layout.load_more,contentView,true);

        if(showImageToolBar()){
            collapsingToolbarLayout = (CollapsingToolbarLayout) toolbarLayout.findViewById(R.id.collapsing_toolbar);
            toolbar_img = (Toolbar) toolbarLayout.findViewById(R.id.toolbar);
            ivHead = (ImageView) toolbarLayout.findViewById(R.id.sdv_head);
            toolbar_simple = (Toolbar) toolbarLayout.findViewById(R.id.toolbar2);
            floatingActionButton = (FloatingActionButton) toolbarLayout.findViewById(R.id.fab);
            toolbar_simple.setVisibility(View.GONE);
            collapsingToolbarLayout.setVisibility(View.VISIBLE);
            setSupportActionBar(toolbar_img);
        }else{
            toolbar_simple = (Toolbar) toolbarLayout.findViewById(R.id.toolbar2);
            setSupportActionBar(toolbar_simple);
        }
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(setToolBarTitle());
        }
        if(showFloatBtn()){
            floatingActionButton.setVisibility(View.VISIBLE);
        }
        if(!hasToolBar()) {
            toolbar_simple.setVisibility(View.GONE);
            if(Build.VERSION.SDK_INT >= 21) {
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
                getWindow().setNavigationBarColor(Color.TRANSPARENT);
            }
        }
    }

    /**
     * 初始化内容布局
     */
    private void initContentView(){
        mainview = new LinearLayout(this);
        mainview.setOrientation(LinearLayout.VERTICAL);
    }
    /**
    activity是否有toolbar
     */
    protected boolean hasToolBar(){
        return true;
    }
    /**
     * toolbar是否显示图片，默认不显示
     * @return
     */
    protected boolean showImageToolBar(){
        return false;
    }

    /**
     * 是否显示fab按钮，默认不显示
     * @return
     */
    protected boolean showFloatBtn(){
        return false;
    }

    /**
     * 设置toolbar 标题
     * @return
     */
    protected String setToolBarTitle() {
       return getString(R.string.app_name);
    }

    /**
     * 设置toolbar 图片的url
     * @param url
     */
    public void setImageUrl(String url) {
        new ImageViewLoad(ivHead).execute(url);
    }
    public void setImageUrl(int resId){
        ivHead.setImageResource(resId);
    }

    /**
     * 设置图片bar的标题
     * @param title
     */
    public void setImgTitle(String title){
        if(actionBar != null){
            actionBar.setTitle(title);
        }
    }

    public void hideToolBar(){
        if(actionBar != null){
            actionBar.hide();
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        // 添加toolbar布局
        mainview.addView(toolbarLayout,new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        LayoutInflater.from(this).inflate(layoutResID,contentView,true);
//        mainview.addView(contentView,new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
        AutoUtils.autoSize(mainview);
        super.setContentView(mainview, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

//    @Override
//    public void setContentView(View view) {
//        setNewContentView(view, view.getLayoutParams());
//    }
//
//    @Override
//    public void setContentView(View view, ViewGroup.LayoutParams params) {
//        setNewContentView(view, params);
//    }
//
//    private void setNewContentView(View view, ViewGroup.LayoutParams params) {
//        // 添加toolbar布局
//        mainview.addView(toolbarLayout, new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));
//
//        // content FrameLayout
//        FrameLayout contentLayout = new FrameLayout(this);
//        if (params == null) {
//            params = new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT);
//        }
//        contentLayout.addView(view, params);
//
//        // 添加content布局
//        mainview.addView(contentLayout, new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//
//        super.setContentView(mainview, new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            back();
            return true;
        }
        if(item.getItemId() == R.id.ab_search){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, new SearchFragment(), "fragment_my")
                    .addToBackStack("fragment:reveal")
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void back() {
        super.onBackPressed();
    }
}
