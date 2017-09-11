package com.demo.nick.resumelxz.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.demo.nick.resumelxz.R;

/**
 * Created by Nick on 2017/9/10.
 * author:nicklxz
 * email:nick_lxz@163.com
 */

public class ColumnLine extends View {
    Paint paint=new Paint();
    public ColumnLine(Context context) {
        super(context);
    }

    public ColumnLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ColumnLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(10);
        paint.setColor(getResources().getColor(R.color.columnColor));
        canvas.drawLine(0,0,100,0,paint);
        paint.setColor(getResources().getColor(R.color.gray));
        canvas.drawLine(100,0,getWidth(),0,paint);
    }
}
