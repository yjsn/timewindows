package com.singgo.cn.timewindows.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.singgo.cn.timewindows.R;
import com.singgo.cn.timewindows.adapter.CommonAdapter;
import com.singgo.cn.timewindows.app.AppContext;
import com.singgo.cn.timewindows.base.BaseFragment;
import com.singgo.cn.timewindows.base.BaseRecyclerView;
import com.singgo.cn.timewindows.base.BaseViewHolder;
import com.singgo.cn.timewindows.listener.ScrollViewListener;
import com.singgo.cn.timewindows.mvp.bean.MovieList;
import com.singgo.cn.timewindows.mvp.bean.Subjects;
import com.singgo.cn.timewindows.mvp.presenter.MoviePresenter;
import com.singgo.cn.timewindows.mvp.view.IMovieView;
import com.singgo.cn.timewindows.ui.PlayerActivity;
import com.singgo.cn.timewindows.ui.webview.WebViewActivity;
import com.singgo.cn.timewindows.util.UIHelper;
import com.singgo.cn.timewindows.widget.MyImageLoad;
import com.singgo.cn.timewindows.widget.MyScrollView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by hxz on 16/9/8 14:14.
 */
public class MainFragment extends BaseFragment implements ScrollViewListener, IMovieView ,SwipeRefreshLayout.OnRefreshListener{

//    @BindView(R.id.cv_viewpager)
//    CenterViewPager cvViewpager;

    private static int count = 6;
    private static int start = 0;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.sv_movie)
    MyScrollView svMovie;
    @BindView(R.id.recycler_view)
    BaseRecyclerView recyclerview;
    @BindView(R.id.tv_more_rm)
    TextView tvMoreRm;
    @BindView(R.id.recycler_view_soon)
    BaseRecyclerView recyclerview_soon;
    @BindView(R.id.tv_more_soon)
    TextView tvMoreSoon;
    @BindView(R.id.tv_more_top)
    TextView tvMoreTop;
    @BindView(R.id.recycler_view_top)
    BaseRecyclerView recyclerview_top;

    private MoviePresenter presenter = new MoviePresenter();

    @Override
    public void initView(View view) {
        super.initView(view);
        initScrollView();
        init();
    }

    @Override
    public void initData() {
        getData();
    }

    private void initScrollView() {
        svMovie.setScrollViewListener(this);
    }

    private void init() {
        initBanner();
        tvMoreRm.setOnClickListener(this);
        tvMoreSoon.setOnClickListener(this);
        tvMoreTop.setOnClickListener(this);
    }

    private void initBanner() {
        List<String> imgesUrl = new ArrayList<>();
        imgesUrl.add("https://img1.doubanio.com//view//movie_poster_cover//lpst//public//p2395733377.jpg");
        imgesUrl.add("https://img1.doubanio.com//view//movie_poster_cover//lpst//public//p2395733377.jpg");
        imgesUrl.add("https://img1.doubanio.com//view//movie_poster_cover//lpst//public//p2395733377.jpg");
        imgesUrl.add("https://img1.doubanio.com//view//movie_poster_cover//lpst//public//p2395733377.jpg");
        //banner1.setData(imgesUrl,null);
        banner.setImageLoader(new MyImageLoad());
        banner.setImages(imgesUrl);
        banner.start();
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                startActivity(new Intent(getActivity(), PlayerActivity.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }


    private Bundle bu;
    private void initRecyclerView(List<Subjects> list, BaseRecyclerView ry){
        ry.setGridLayoutManager(3);
        ry.setAdapter(new CommonAdapter<Subjects>(getActivity(), R.layout.fragment_main_movie_item, list) {
            @Override
            public void convert(BaseViewHolder holder, final Subjects s) {
                holder.setText(R.id.tv_title,s.getTitle());
                holder.setFSImage(R.id.sdv_movie_img,s.getImages().getLarge());
                String type ="";
                for(String t:s.getGenres()){
                    type+=t+" ";
                }
                holder.setText(R.id.tv_type,type);
                holder.setText(R.id.tv_rank,"评分："+s.getRating().getAverage());
                holder.setOnClickListener(R.id.sdv_movie_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bu = new Bundle();
                        bu.putString("id",s.getId());
                        bu.putString("title",s.getTitle());
                        bu.putString("image",s.getImages().getLarge());
                        UIHelper.showImgSimpleBack(getActivity(), SimpleBackPage.MOVIE_INFO,bu);
                    }
                });
            }
        });
    }

//    private void initPager(MovieList list) {
//        List<View> views = new ArrayList<>();
//        for (int i = 0; i < list.getSubjects().size(); i++) {
//            View card = LayoutInflater.from(getActivity()).inflate(R.layout.view_card, null);
//            SimpleDraweeView sdv = (SimpleDraweeView) card.findViewById(R.id.sdv_img);
//            sdv.setImageURI(Uri.parse(list.getSubjects().get(i).getImages().getLarge()));
//            card.setTag(i);
//            views.add(card);
//        }
//        CenterViewPagerAdapter adapter = new CenterViewPagerAdapter(getActivity(), views);
//        cvViewpager.setAdapter(adapter);
//        cvViewpager.enableCenterLockOfChilds();
//        cvViewpager.setPageTransformer(true, new ZoomOutPageTransformer());
//
//        cvViewpager.initView();
//        cvViewpager.setOnPageChangeListener(new CenterViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                //weekCalendar.moveToPrevious();
//                //weekCalendar.setSelectedDate(new DateTime("2015-01-01"));
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
//    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public int start() {
        return start;
    }

    @Override
    public String getSearch() {
        return null;
    }

    @Override
    public String getTags() {
        return null;
    }

    private List<String> array_in = new ArrayList<>();
    private List<String> array_soon = new ArrayList<>();
    private List<String> array_top = new ArrayList<>();

    @Override
    public void getMovieList(MovieList list,int type) {
        if(!isRefreshing){
            if(type == 0){
                for(Subjects subjects:list.getSubjects()){
                    array_in.add(subjects.getId());
                }
                initRecyclerView(list.getSubjects(),recyclerview);
            }else if(type == 1){
                for(Subjects subjects:list.getSubjects()){
                    array_soon.add(subjects.getId());
                }
                initRecyclerView(list.getSubjects(),recyclerview_soon);
            }else if(type == 2){
                for(Subjects subjects:list.getSubjects()){
                    array_top.add(subjects.getId());
                }
                initRecyclerView(list.getSubjects(),recyclerview_top);
            }
            svMovie.setVisibility(View.VISIBLE);
        }else{
            recyclerview.notifyData();
            recyclerview_soon.notifyData();
            recyclerview_top.notifyData();
        }
        setRefreshingFalse();
    }

    private void setSLEnabled(boolean f){
        setRefreshEnableFalse(f);
    }

    @Override
    public void onScrollChanged(NestedScrollView scrollView, int x, int y, int oldx, int oldy) {
        if(y<=10){
            setSLEnabled(true);
        }else{
            setSLEnabled(false);
        }
    }

    private Bundle bundle;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_more_rm:
                bundle = new Bundle();
                bundle.putInt("type",MoviePresenter.TYPE_IN_THEATERS);
                UIHelper.showSimpleBack(getActivity(), SimpleBackPage.MOVIE_LIST_IN,bundle);
                break;
            case R.id.tv_more_soon:
                bundle = new Bundle();
                bundle.putInt("type",MoviePresenter.TYPE_COMING_SOON);
                UIHelper.showSimpleBack(getActivity(), SimpleBackPage.MOVIE_LIST_COMING,bundle);
                break;
            case R.id.tv_more_top:
                bundle = new Bundle();
                bundle.putInt("type",MoviePresenter.TYPE_TOP);
                UIHelper.showSimpleBack(getActivity(), SimpleBackPage.MOVIE_LIST_TOP,bundle);
                break;
        }
    }



    @Override
    public void onRefresh() {
        start = 0;
        getData();
    }

    private void getData(){
        presenter.getMovieList(this,MoviePresenter.TYPE_IN_THEATERS);
        presenter.getMovieList(this,MoviePresenter.TYPE_COMING_SOON);
        presenter.getMovieList(this,MoviePresenter.TYPE_TOP);
    }
}
