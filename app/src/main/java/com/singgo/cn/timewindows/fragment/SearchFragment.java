package com.singgo.cn.timewindows.fragment;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.singgo.cn.timewindows.R;
import com.singgo.cn.timewindows.adapter.CommonAdapter;
import com.singgo.cn.timewindows.app.AppContext;
import com.singgo.cn.timewindows.base.BaseRecyclerView;
import com.singgo.cn.timewindows.base.BaseViewHolder;
import com.singgo.cn.timewindows.cache.CacheManage;
import com.singgo.cn.timewindows.mvp.presenter.MoviePresenter;
import com.singgo.cn.timewindows.util.MathUtils;
import com.singgo.cn.timewindows.util.StringUtils;
import com.singgo.cn.timewindows.util.UIHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.ViewAnimationUtils;

/**
 * Created by hxz on 2016/12/28.
 */

public class SearchFragment extends Fragment implements View.OnClickListener ,BaseRecyclerView.OnItemClickListener{
    private static String SEARCH_CACHE = SearchFragment.class.getSimpleName();
    private View content;
    //    private SupportAnimator mRevealAnimator;
    private EditText edit_search;
    private BaseRecyclerView recyclerView;
    private ImageView imgSearch;
    private int centerX;
    private int centerY;
    private TextView tvClear;
    private JSONArray array; //缓存的搜索字段

    private CommonAdapter<String>adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search,container,false);
        rootView.setOnClickListener(this);
        content = rootView.findViewById(R.id.content);
        recyclerView = (BaseRecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setOnItemClickListener(this);
        imgSearch = (ImageView) rootView.findViewById(R.id.img_search);
        imgSearch.setOnClickListener(this);
        tvClear = (TextView) rootView.findViewById(R.id.tv_clear);
        tvClear.setOnClickListener(this);
        edit_search = (EditText) rootView.findViewById(R.id.edit_search);
        final ImageView img_search = (ImageView) rootView.findViewById(R.id.img_search);
        final View edit_lay = rootView.findViewById(R.id.edit_lay);
        final View items = rootView.findViewById(R.id.items);
        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    search(null);
                    onBackPressed();
                    return true;
                }
                return false;
            }
        });
        edit_lay.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                edit_lay.getViewTreeObserver().removeOnPreDrawListener(this);
                items.setVisibility(View.INVISIBLE);
                items.setOnClickListener(SearchFragment.this);
                edit_lay.setVisibility(View.INVISIBLE);

                centerX = img_search.getLeft()+img_search.getWidth()/2;
                centerY = img_search.getTop()+img_search.getHeight()/2;
                Animator animator = ViewAnimationUtils.createCircularReveal(edit_lay, centerX, centerY, 20, MathUtils.hypo(edit_lay.getWidth(), edit_lay.getHeight()));
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        edit_lay.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                items.setVisibility(View.VISIBLE);
                                edit_search.requestFocus();
                                if (getActivity()!=null) {
                                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(edit_search, InputMethodManager.SHOW_IMPLICIT);
                                }
                            }
                        }, 100);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                animator.setDuration(200);
                animator.setStartDelay(100);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.start();
                return true;
            }
        });
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView(){
        array = CacheManage.getArray(SearchFragment.SEARCH_CACHE);
        if(array == null){
            array = new JSONArray();
        }
        if(array.length()==0){
            tvClear.setVisibility(View.GONE);
        }
        List<String>list = new ArrayList<>();
        for(int i=array.length()-1;i>=0;i--){
            try {
                list.add(array.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        recyclerView.setLineLayoutManager();
        adapter = new CommonAdapter<String>(getActivity(),R.layout.fragment_search_item,list) {
            @Override
            public void convert(BaseViewHolder holder, String o) {
                holder.setText(R.id.tv_search,o);
            }

        };
        recyclerView.setAdapter(adapter);
    }

    public boolean onBackPressed() {
        Animator mRevealAnimator = ViewAnimationUtils.createCircularReveal(content, centerX, centerY,  MathUtils.hypo(content.getWidth(), content.getHeight()),20);
        if (mRevealAnimator==null) return false;
        mRevealAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                content.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                content.setVisibility(View.INVISIBLE);
                if (getActivity()!=null)
                    getActivity().getSupportFragmentManager().popBackStack();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mRevealAnimator.setDuration(200);
        mRevealAnimator.setStartDelay(100);
        mRevealAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mRevealAnimator.start();
        return true;
    }

    private void search(String search){
        if(search==null){
            search = edit_search.getText().toString();
        }
        if(!StringUtils.isEmpty(search)){
            if(!StringUtils.isEmpty(edit_search.getText().toString())) {
                if (array.length() == 0) {
                    array = new JSONArray();
                } else {
                    array = CacheManage.getArray(SearchFragment.SEARCH_CACHE);
                }
                array.put(search);
                CacheManage.cacheList(SearchFragment.SEARCH_CACHE, array);
            }
            Bundle bundle = new Bundle();
            bundle.putString("search",search);
            UIHelper.showSimpleBack(getActivity(),SimpleBackPage.MOVIE_LIST_SEARCH,bundle);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_clear:
                array = new JSONArray();
                CacheManage.cacheList(SearchFragment.SEARCH_CACHE,array);
                adapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.GONE);
                tvClear.setVisibility(View.GONE);
                break;
            case R.id.img_search:
                //AppContext.showToast(getActivity(),"asadasda");
                search(null);
            case R.id.ll_root:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onItemClick(int position, View itemView) {

        try {
            //Log.i("tag",array.getString(position));
            search(array.getString(array.length()-position-1));
            onBackPressed();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
