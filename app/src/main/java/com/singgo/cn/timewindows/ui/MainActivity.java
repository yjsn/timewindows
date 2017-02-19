package com.singgo.cn.timewindows.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.singgo.cn.timewindows.R;
import com.singgo.cn.timewindows.app.AppContext;
import com.singgo.cn.timewindows.base.BaseMainActivity;
import com.singgo.cn.timewindows.cache.CacheManage;
import com.singgo.cn.timewindows.entity.Constants;
import com.singgo.cn.timewindows.fragment.AddFragment;
import com.singgo.cn.timewindows.fragment.InformationFragment;
import com.singgo.cn.timewindows.fragment.MainFragment;
import com.singgo.cn.timewindows.fragment.MovieListFragment;
import com.singgo.cn.timewindows.fragment.MzImgFragment;
import com.singgo.cn.timewindows.fragment.SearchFragment;
import com.singgo.cn.timewindows.mvp.bean.User;
import com.singgo.cn.timewindows.mvp.presenter.UserPresenter;
import com.singgo.cn.timewindows.mvp.view.IUserInfoView;
import com.singgo.cn.timewindows.ui.webview.WebViewActivity;
import com.singgo.cn.timewindows.util.StateBarTranslucentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseMainActivity implements NavigationView.OnNavigationItemSelectedListener, IUserInfoView {
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
//    @BindView(R.id.sw_refresh)
//    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.vp_view)
    ViewPager viewPager;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<Fragment> mViewList = new ArrayList<>();//页卡视图集合

    private UserPresenter presenter = new UserPresenter();
    public User user;

    public void initView() {
        //presenter.getUser(this);
        //initSwipeRefreshLayout();
        initDrawerLayout();
        initViewPage();
        ivSearch.setOnClickListener(this);
    }


    private void initViewPage() {
        mTitleList.add("电影");
        mTitleList.add("资讯");
        mTitleList.add("妹子");
        //mTitleList.add("图书");
       // mTitleList.add("其他");
        mViewList.add(new MainFragment());
        mViewList.add(new InformationFragment());
        mViewList.add(new MzImgFragment());
        //mViewList.add(new AddFragment());
        //mViewList.add(new AddFragment());
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for(String title:mTitleList) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        MyViewPageAdapter mAdapter = new MyViewPageAdapter(getSupportFragmentManager(),mViewList,mTitleList);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 初始化DrawerLayout
     */
    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, mainToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        //禁止侧滑
        // drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        // drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_search:
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(android.R.id.content, new SearchFragment(), "fragment_my")
                        .addToBackStack("fragment:reveal")
                        .commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    private void initSwipeRefreshLayout() {
//        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
//    }

//    /*
//    设置下拉刷新控件可用
//     */
//    public void setSLEnabled(boolean f){
//        if(!swipeRefreshLayout.isRefreshing()) {
//            swipeRefreshLayout.setEnabled(f);
//        }
//    }

    protected int getLayoutId() {
        return R.layout.activity_drawerlayout;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, WebViewActivity.class);
        switch (item.getItemId()){
            case R.id.blog:
                intent.putExtra("mTitle",item.getTitle());
                intent.putExtra("mUrl","http://hxzxx.me");
                intent.putExtra("flag",false);
                startActivity(intent);
                break;
            case R.id.vue:
                intent.putExtra("mTitle",item.getTitle());
                intent.putExtra("mUrl","http://hxzxx.me/vuedb/");
                intent.putExtra("flag",false);
                startActivity(intent);
                break;
            case R.id.about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.ab_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setUser(User user) {
        CacheManage.cache.put("user", user);
        Intent intent = new Intent();
        intent.setAction(Constants.GET_USER_SUCCESS);
        MainActivity.this.sendBroadcast(intent);
    }

    @Override
    public User getUser() {
        return user;
    }

    public String getSid() {
//       if(StringUtils.isEmpty(CacheManage.getSid())){
//           Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//           startActivity(intent);
//           finish();
//           return "";
//       }
        return "";
    }

    class MyViewPageAdapter extends FragmentStatePagerAdapter{
        private List<Fragment>mList;
        private List<String>mTitleList;
        public MyViewPageAdapter(FragmentManager fm,List<Fragment>mList,List<String>mTitleList) {
            super(fm);
            this.mList = mList;
            this.mTitleList = mTitleList;
        }
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }
}
