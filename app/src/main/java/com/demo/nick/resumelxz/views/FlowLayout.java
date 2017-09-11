package com.demo.nick.resumelxz.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 2017/9/8.
 */

public class FlowLayout extends ViewGroup {
    private static final String TAG = "FlowLayout";
    //所有行的集合，里面是每一行的view的集合。
    private List<List<View>> mAllViews = new ArrayList<>();
    //每一行的高度的集合
    private List<Integer> mLineHeights = new ArrayList<>();

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int width = 0;//子view的总宽
        int height = 0;//子view的总高
        //每一行的高度与宽度
        int lineWidth = 0;
        int lineHeight = 0;
        int cCount = getChildCount();   //得到子view的个数
        for (int i = 0; i < cCount; i++) {//循环测量子view 并得到Warp_Content下的自身的宽高
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到layoutparams
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //子 view的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //子VIEW的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (lineWidth + childWidth > measureWidth - getPaddingLeft() - getPaddingLeft()) {  //换行
                //得到该行的总宽
                width = Math.max(width, lineWidth);
                //新行初始宽度为0，高度叠加
                lineWidth = childWidth;
                height += lineHeight;
            } else { //不换行
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //到达最后一个
            if (i == cCount-1) {
                width = Math.max(lineWidth, childWidth);
                height += lineHeight;
            }
        }
        //设置自身宽高。如果是AT_MOST模式。则返回子view的总宽和总高，并且要加上padding
        setMeasuredDimension(widthMode == MeasureSpec.AT_MOST ? width + getPaddingLeft() + getPaddingRight() : measureWidth,
                heightMode == MeasureSpec.AT_MOST ? height + getPaddingTop() + getPaddingBottom() : measureHeight);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeights.clear();
        int width = getWidth() - getPaddingLeft() - getPaddingRight();//Group总宽度 注意要减去padding
        int lineWidth = 0;//当前子view的行宽
        int lineHeight = 0;//当前行子view的行高
        List<View> lineView = new ArrayList<>();//每一行的子view的集合
        int count = getChildCount();//子view的数量
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width) {//当前宽度不够，换行
                mLineHeights.add(lineHeight);//记录lineHeight
                mAllViews.add(lineView);
                //重置行宽和行高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                lineView = new ArrayList<>();//这里为什么不用clear  因为lineview要被添加进mAllviews里面，如果清空了，则只有最后一行会显示
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);//取当前行最高的子view的高度为当前行高。
            lineView.add(child);
        }//for end
        //上面的循环并没有添加进最后一行的子view,在这里添加
        mLineHeights.add(lineHeight);
        mAllViews.add(lineView);
        //设置子view的位置  第一个从（0.0)开始 注意加上padding
        int left = getPaddingLeft() + 0;
        int top = getPaddingTop() + 0;
        //行数
        int lineNum = mAllViews.size();
        for (int i = 0; i < lineNum; i++) {
            lineView = mAllViews.get(i);
            lineHeight = mLineHeights.get(i);
            //当前行进行子view的布局
            for (int j = 0; j < lineView.size(); j++) {
                View child = lineView.get(j);
                if (child.getVisibility() == GONE) { //gone就不用给它设置位置了
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            //换行，初始话坐标
            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    /**
     * 与当前 ViewGroup对应的LayoutParams
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
