package com.example.gt.circleviewwidgetapplication.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.gt.circleviewwidgetapplication.R;


/**
 * @des:
 * @author: gutao
 * @date: 2018/2/06 10:29
 */

@SuppressLint("AppCompatCustomView")
public class MyImageButton extends Button {
    private int xTrans;
    private int yTrans;
    public MyImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyImageButton);
        xTrans = a.getDimensionPixelSize(R.styleable.MyImageButton_xTrans, 0);
        yTrans = a.getDimensionPixelSize(R.styleable.MyImageButton_yTrans, 0);
        a.recycle();
    }

    public int getxTrans() {
        return xTrans;
    }

    public int getyTrans() {
        return yTrans;
    }

    public void setxTrans(int xTrans) {
        this.xTrans = xTrans;
    }

    public void setyTrans(int yTrans) {
        this.yTrans = yTrans;
    }

}
