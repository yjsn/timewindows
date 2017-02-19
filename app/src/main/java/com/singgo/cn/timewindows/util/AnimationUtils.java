package com.singgo.cn.timewindows.util;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by hxz on 2017/1/3.
 * 动画效果工具类
 */

public class AnimationUtils {
    /**
     *
     * @return
     */
    public static TranslateAnimation showAnimation(long l){
        TranslateAnimation mShowAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF
                ,-1.0f,Animation.RELATIVE_TO_SELF,0.0f);
        mShowAnim.setDuration(l);
        return mShowAnim;
    }

    public static TranslateAnimation hideAnimation(long l){
        TranslateAnimation hiddenAmin = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF
                ,0.0f,Animation.RELATIVE_TO_SELF,-1.0f);
        hiddenAmin.setDuration(l);
        return hiddenAmin;
    }

}
