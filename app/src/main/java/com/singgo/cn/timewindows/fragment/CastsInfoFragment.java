package com.singgo.cn.timewindows.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.singgo.cn.timewindows.R;
import com.singgo.cn.timewindows.base.BaseFragment;
import com.singgo.cn.timewindows.base.BaseRecyclerView;
import com.singgo.cn.timewindows.mvp.bean.Celebrity;
import com.singgo.cn.timewindows.mvp.bean.Subjects;
import com.singgo.cn.timewindows.mvp.bean.Works;
import com.singgo.cn.timewindows.mvp.presenter.MoviePresenter;
import com.singgo.cn.timewindows.mvp.view.ICastsInfoView;
import com.singgo.cn.timewindows.ui.ImgSimpleBackActivity;
import com.singgo.cn.timewindows.util.UIHelper;
import com.singgo.cn.timewindows.widget.CenterViewPager;
import com.singgo.cn.timewindows.widget.CenterViewPagerAdapter;
import com.singgo.cn.timewindows.widget.MySwipeRefreshLayout;
import com.singgo.cn.timewindows.widget.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hxz on 2016/12/25.
 */

public class CastsInfoFragment extends BaseFragment implements ICastsInfoView {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_name_en)
    TextView tvNameEn;
    @BindView(R.id.tv_aka)
    TextView tvAka;
    @BindView(R.id.tv_aka_en)
    TextView tvAkaEn;
    @BindView(R.id.tv_born)
    TextView tvBorn;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.cv_viewpager)
    CenterViewPager cvViewpager;
    private String id = null;
    private MoviePresenter presenter;

    @Override
    public void initView(View view) {
        init();
    }

    private void init() {
        Bundle bundle = getActivity().getIntent().getBundleExtra(ImgSimpleBackActivity.BUNDLE_KEY_ARGS);
        id = bundle.getString("id");
        String title = bundle.getString("title");
        String image = bundle.getString("image");
        ((ImgSimpleBackActivity) getActivity()).setImgTitle(title);
        ((ImgSimpleBackActivity) getActivity()).setImageUrl(image);
        if (id != null) {
            presenter = new MoviePresenter();
            presenter.getCastsInfo(this);
        }
    }

    private void initCelebrity(Celebrity celebrity) {
        tvName.setText("姓名："+celebrity.getName());
        tvNameEn.setText("英文名："+celebrity.getName_en());
        String akas = "";
        for(String aka:celebrity.getAka()){
            akas += aka+" ";
        }
        tvAka.setText("昵称："+akas);
        String akas_en  = "";
        for(String aka_en:celebrity.getAka_en()){
            akas_en+=aka_en+" ";
        }
        tvAkaEn.setText("更多昵称："+akas_en);
        tvBorn.setText("出生地："+celebrity.getBorn_place());
        tvGender.setText("性别："+celebrity.getGender());
    }

        private void initPager(Celebrity celebrity) {
            final List<Works>list = celebrity.getWorks();
            List<View> views = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                View card = LayoutInflater.from(getActivity()).inflate(R.layout.view_card, null);
                SimpleDraweeView sdv = (SimpleDraweeView) card.findViewById(R.id.sdv_img);
                sdv.setImageURI(Uri.parse(list.get(i).getSubject().getImages().getLarge()));
                TextView tvInfo = (TextView) card.findViewById(R.id.tv_info);
                String roles = "";
                for(String role:list.get(i).getRoles()){
                    roles+=role+" ";
                }
                tvInfo.setText(list.get(i).getSubject().getYear()+"\t"+list.get(i).getSubject().getTitle());
                card.setTag(i);
                views.add(card);
                final Subjects su = list.get(i).getSubject();
                card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bu = new Bundle();
                        bu.putString("id",su.getId());
                        bu.putString("title",su.getTitle());
                        bu.putString("image",su.getImages().getLarge());
                        UIHelper.showImgSimpleBack(getActivity(), SimpleBackPage.MOVIE_INFO,bu);
                    }
                });
            }
            CenterViewPagerAdapter adapter = new CenterViewPagerAdapter(getActivity(), views);
            cvViewpager.setAdapter(adapter);
            cvViewpager.enableCenterLockOfChilds();
            cvViewpager.setPageTransformer(true, new ZoomOutPageTransformer());
            cvViewpager.initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_casts_info;
    }

    @Override
    protected boolean hasRefreshLayout() {
        return false;
    }

    @Override
    public String getCastsId() {
        return id;
    }

    @Override
    public void getCastsInfo(Celebrity celebrity) {
        initCelebrity(celebrity);
        initPager(celebrity);
    }



}
