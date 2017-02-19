package com.singgo.cn.timewindows.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.singgo.cn.timewindows.R;
import com.singgo.cn.timewindows.adapter.CommonAdapter;
import com.singgo.cn.timewindows.base.BaseFragment;
import com.singgo.cn.timewindows.base.BaseRecyclerView;
import com.singgo.cn.timewindows.base.BaseViewHolder;
import com.singgo.cn.timewindows.mvp.bean.Avatars;
import com.singgo.cn.timewindows.mvp.bean.Casts;
import com.singgo.cn.timewindows.mvp.bean.Subjects;
import com.singgo.cn.timewindows.mvp.presenter.MoviePresenter;
import com.singgo.cn.timewindows.mvp.view.IMovieItemView;
import com.singgo.cn.timewindows.ui.ImgSimpleBackActivity;
import com.singgo.cn.timewindows.util.UIHelper;
import com.singgo.cn.timewindows.widget.CenterViewPager;
import com.singgo.cn.timewindows.widget.CenterViewPagerAdapter;
import com.singgo.cn.timewindows.widget.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hxz on 2016/12/21.
 */

public class MovieInfoFragment extends BaseFragment implements IMovieItemView,BaseRecyclerView.OnItemClickListener {
    @BindView(R.id.tv_directors)
    TextView tvDirectors;
    @BindView(R.id.tv_casts)
    TextView tvCasts;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_rename)
    TextView tvRename;
    @BindView(R.id.tv_summary)
    TextView tvSummary;

    @BindView(R.id.recycler_view)
    BaseRecyclerView recyclerView;
    private String id = null;

    private MoviePresenter presenter;
    private List<Casts> castsList;

    @Override
    public void initView(View view) {
        super.initView(view);
        recyclerView.setOnItemClickListener(this);
        init();
    }

    private void initRecyclerView() {
        recyclerView.setGridLayoutManager(2);
        CommonAdapter<Casts> adapter = new CommonAdapter<Casts>(getActivity(),R.layout.fragment_casts_item,castsList) {
            @Override
            public void convert(BaseViewHolder holder, Casts casts) {
                holder.setFSImage(R.id.sdv_img,casts.getAvatars().getLarge());
                holder.setText(R.id.tv_casts_name,casts.getName());
            }
        };
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected boolean hasRefreshLayout() {
        return false;
    }

    private void init() {
        Bundle b = getActivity().getIntent().getBundleExtra(ImgSimpleBackActivity.BUNDLE_KEY_ARGS);
        id =  b.getString("id");
        String title = b.getString("title");
        String image = b.getString("image");
        ((ImgSimpleBackActivity)getActivity()).setImageUrl(image);
        ((ImgSimpleBackActivity)getActivity()).setImgTitle(title);
        if(id!= null){
            presenter = new MoviePresenter();
            presenter.getMovieItem(this);
        }
    }

    private void initInfo(Subjects subjects){
        StringBuilder sb = new StringBuilder();
        sb.append("导演：");
        for(Casts cast:subjects.getDirectors()){
            sb.append(cast.getName()).append("\t");
        }
        tvDirectors.setText(sb.toString());
        sb.setLength(0);
        sb.append("主演：");
        for(Casts cast:subjects.getCasts()){
            sb.append(cast.getName()).append("\t");
        }
        tvCasts.setText(sb.toString());
        sb.setLength(0);
        sb.append("类型：");
        for(String type:subjects.getGenres()){
            sb.append(type).append("\t");
        }
        tvType.setText(sb.toString());
        sb.setLength(0);
        sb.append("国家：");
        for(String c:subjects.getCountries()){
            sb.append(c).append("\t");
        }
        tvAddress.setText(sb.toString());
        tvRank.setText("评分："+subjects.getRating().getAverage());
        tvSummary.setText("简介："+subjects.getSummary());
        tvRename.setText("原名："+subjects.getOriginal_title());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie_info;
    }



    @Override
    public String getMovieId() {
        return id;
    }

    @Override
    public void getMovieItem(Subjects subjects) {
        initInfo(subjects);
        castsList = subjects.getCasts();
        initRecyclerView();
    }

    @Override
    public void onItemClick(int position, View itemView) {
        Bundle b = new Bundle();
        Casts casts = castsList.get(position);
        b.putString("id",casts.getId());
        b.putString("title",casts.getName());
        b.putString("image",casts.getAvatars().getLarge());
        UIHelper.showImgSimpleBack(getActivity(),SimpleBackPage.CASTS_INFO,b);
    }
}
