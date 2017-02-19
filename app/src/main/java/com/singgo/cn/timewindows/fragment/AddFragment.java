package com.singgo.cn.timewindows.fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dou361.ijkplayer.bean.VideoijkBean;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.facebook.common.media.MediaUtils;
import com.singgo.cn.timewindows.R;
import com.singgo.cn.timewindows.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hxz on 16/9/10 10:25.
 */
public class AddFragment extends BaseFragment {

    @Override
    public void initView(View view) {
        super.initView(view);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add;
    }

    @Override
    protected boolean hasRefreshLayout() {
        return false;
    }



}
