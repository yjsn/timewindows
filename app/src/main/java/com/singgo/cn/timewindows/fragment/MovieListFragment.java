package com.singgo.cn.timewindows.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.singgo.cn.timewindows.R;
import com.singgo.cn.timewindows.adapter.CommonAdapter;
import com.singgo.cn.timewindows.adapter.LoadMoreWrapper;
import com.singgo.cn.timewindows.base.BaseFragment;
import com.singgo.cn.timewindows.base.BaseRecyclerView;
import com.singgo.cn.timewindows.base.BaseViewHolder;
import com.singgo.cn.timewindows.mvp.bean.Casts;
import com.singgo.cn.timewindows.mvp.bean.MovieList;
import com.singgo.cn.timewindows.mvp.bean.Subjects;
import com.singgo.cn.timewindows.mvp.presenter.MoviePresenter;
import com.singgo.cn.timewindows.mvp.view.IMovieView;
import com.singgo.cn.timewindows.ui.SimpleBackActivity;
import com.singgo.cn.timewindows.util.AnimationUtils;
import com.singgo.cn.timewindows.util.DataUtils;
import com.singgo.cn.timewindows.util.StringUtils;
import com.singgo.cn.timewindows.util.UIHelper;
import com.singgo.cn.timewindows.widget.MySwipeRefreshLayout;
import com.singgo.cn.timewindows.widget.MyTagFlowLayout;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hxz on 2016/12/18.
 */

public class MovieListFragment extends BaseFragment implements IMovieView,BaseRecyclerView.OnItemClickListener {

    @BindView(R.id.recycler_view)
    BaseRecyclerView recyclerView;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout flowLayout;
    @BindView(R.id.recycler_tag)
    BaseRecyclerView recyclerTag;
    @BindView(R.id.ll_bg)
    LinearLayout layoutBg;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    private int start = 0;
    private int count = 10;
    private MoviePresenter presenter = new MoviePresenter();

    private MovieList mList;
    private List<Subjects> subjectses = new ArrayList<>();
    private int type ;
    @Override
    public void initView(View view) {
        super.initView(view);
        init();
        initFlowLayout();
    }
    private View lastView = null;
    private List<String>tagList = DataUtils.getTagList();
    private void initFlowLayout() {
        recyclerTag.setHorizontalLayoutManager();
        recyclerTag.setAdapter(new CommonAdapter<String>(getActivity(),R.layout.tag_textview,tagList) {
            @Override
            public void convert(final BaseViewHolder holder, String s) {
                holder.setText(R.id.tv_tag,s);
            }
        });
        recyclerTag.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View itemView) {
                TextView tv = (TextView) itemView.findViewById(R.id.tv_tag);
                tv.setTextColor(getResources().getColor(R.color.colorBar));
                tv.setBackgroundResource(R.drawable.checked_bg);
                if(lastView!=null){
                    TextView tvLast = (TextView) lastView.findViewById(R.id.tv_tag);
                    tvLast.setTextColor(getResources().getColor(R.color.tag_normal_text));
                    tvLast.setBackgroundResource(R.drawable.normal_bg);
                }
                lastView = itemView;
                tags = tagList.get(position);
//                start = 0;
//                isRefreshing = true;
//                subjectses.clear();
//                presenter.getTagList(MovieListFragment.this);
                showRefreshLayout();
                onRefresh();
            }
        });
        flowLayout.setAdapter(new TagAdapter<String>(tagList) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tag_textview,
                        flowLayout, false);
                tv.setText(o);
                return tv;
            }
        });
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                TextView tv = (TextView) view.findViewById(R.id.tv_tag);
//                tv.setTextColor(getResources().getColor(R.color.colorBar));
//                tv.setBackgroundResource(R.drawable.checked_bg);
                tags = tagList.get(position);
                showRefreshLayout();
                onRefresh();
                hiddenMoreView();
                return false;
            }
        });
        layoutBg.setOnClickListener(this);
        ivMore.setOnClickListener(this);
    }

    private String searchs;
    private String tags;
    private void init() {
        Bundle bundle = getActivity().getIntent().getBundleExtra(SimpleBackActivity.BUNDLE_KEY_ARGS);
        type = bundle.getInt("type");
        String search = bundle.getString("search"); //搜索
        String tag = bundle.getString("tag"); //标签
        if(!StringUtils.isEmpty(search)){
            searchs = search;
            presenter.getSearchList(this);
        }else if(!StringUtils.isEmpty(tag)){
            tags = tag;
        }else{
            presenter.getMovieList(this,type);
        }
        recyclerView.setOnItemClickListener(this);
    }

    private void initRecyclerView() {
        CommonAdapter<Subjects> commonAdapter = new CommonAdapter<Subjects>(getActivity(),R.layout.fragment_movie_list_item,subjectses) {
            @Override
            public void convert(BaseViewHolder holder, Subjects subjects) {
                holder.setText(R.id.tv_title,subjects.getTitle());
                holder.setFSImage(R.id.sdv_movie_img,subjects.getImages().getLarge());
                holder.setText(R.id.tv_year,"年份："+subjects.getYear());
                StringBuilder sb = new StringBuilder();
                for(Casts casts:subjects.getDirectors()){
                    sb.append(casts.getName());
                    sb.append("\t");
                }
                holder.setText(R.id.tv_directors,"导演："+sb.toString());
                sb.setLength(0);
                for(Casts casts:subjects.getCasts()){
                    sb.append(casts.getName());
                    sb.append("\t");
                }
                holder.setText(R.id.tv_casts,"主演："+sb.toString());
                sb.setLength(0);
                for(String type:subjects.getGenres()){
                    sb.append(type);
                    sb.append("\t");
                }
                holder.setText(R.id.tv_type,"类型："+sb.toString());
                holder.setText(R.id.tv_rank,"评分："+subjects.getRating().getAverage());
            }
        };
        //行数超过一页，才加载下拉刷新
        if(mList.getTotal()>count) {
            recyclerView.hasLoadMore(commonAdapter);
            recyclerView.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    if(start<mList.getTotal()){
                        start = start+10;
                        if(!StringUtils.isEmpty(searchs)){
                            presenter.getSearchList(MovieListFragment.this);
                        }else if(!StringUtils.isEmpty(tags)){
                            presenter.getTagList(MovieListFragment.this);
                        }else{
                            presenter.getMovieList(MovieListFragment.this,type);
                        }
                    }else{
                        recyclerView.hideLoadMore();
                    }
                }
            });
        }
        recyclerView.setLineLayoutManager();
        recyclerView.setLineDecoration();
        recyclerView.setAdapter(commonAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie_list;
    }


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
        return searchs;
    }

    @Override
    public String getTags() {
        return tags;
    }

    @Override
    public void getMovieList(MovieList list,int type) {
        if(start == 0){
            mList = list;
            subjectses.addAll(list.getSubjects());
            setRefreshingFalse();
            if(!isRefreshing){
                initRecyclerView();
            }else{
                recyclerView.notifyData();
            }
        }else{
            subjectses.addAll(list.getSubjects());
            recyclerView.notifyData();
        }
    }

    @Override
    public void onRefresh() {
        start = 0;
        isRefreshing = true;
        subjectses.clear();
        if(!StringUtils.isEmpty(searchs)){
            presenter.getSearchList(MovieListFragment.this);
        }else if(!StringUtils.isEmpty(tags)){
            presenter.getTagList(MovieListFragment.this);
        }else{
            presenter.getMovieList(MovieListFragment.this,type);
        }
    }

    @Override
    public void onItemClick(int position, View itemView) {
        Subjects su = subjectses.get(position);
        Bundle bu = new Bundle();
        bu.putString("id",su.getId());
        bu.putString("title",su.getTitle());
        bu.putString("image",su.getImages().getLarge());
        UIHelper.showImgSimpleBack(getActivity(), SimpleBackPage.MOVIE_INFO,bu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_bg:
                hiddenMoreView();
                break;
            case R.id.iv_more:
                flowLayout.startAnimation(AnimationUtils.showAnimation(150));
                flowLayout.setVisibility(View.VISIBLE);
                layoutBg.setVisibility(View.VISIBLE);
                break;
        }
    }


    private void hiddenMoreView(){
        TranslateAnimation hiddenAmin = AnimationUtils.hideAnimation(150);
        flowLayout.startAnimation(hiddenAmin);
        flowLayout.setVisibility(View.GONE);
        hiddenAmin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layoutBg.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
