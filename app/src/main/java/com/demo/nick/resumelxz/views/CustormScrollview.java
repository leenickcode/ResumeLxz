package com.demo.nick.resumelxz.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by Nick on 2017/9/10.
 * author:nicklxz
 * email:nick_lxz@163.com
 */

public class CustormScrollview extends ScrollView {
    public CustormScrollview(Context context) {
        super(context);
    }

    public CustormScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustormScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        View child = getChildAt(0);
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        //得到layoutparams
        MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        //子 view的宽度
        int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
        //子VIEW的高度
        int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
        Log.e("aaaaaa",childHeight+""+child.getId());
        widthMeasureSpec=MeasureSpec.makeMeasureSpec(childWidth,MeasureSpec.EXACTLY);
        heightMeasureSpec=MeasureSpec.makeMeasureSpec(childHeight,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
