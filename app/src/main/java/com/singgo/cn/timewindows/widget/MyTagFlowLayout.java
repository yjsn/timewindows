package com.singgo.cn.timewindows.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.zhy.view.flowlayout.TagFlowLayout;
import com.zhy.view.flowlayout.TagView;

/**
 * Created by hxz on 2017/1/3.
 */

public class MyTagFlowLayout extends TagFlowLayout {

    public MyTagFlowLayout(Context context){
        super(context);
    }

    public MyTagFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTagFlowLayout(Context context, AttributeSet attrs,int p){
        super(context,attrs,p);
    }
    private int maxWidth = 0;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count =  getChildCount();
        for (int i = 0; i < count; i++){
            TagView tagView = (TagView) getChildAt(i);
            maxWidth+=tagView.getWidth();
        }
    }

    public int getMaxWidth(){
        return  maxWidth;
    }
}
