package com.example.gt.circleviewwidgetapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.gt.circleviewwidgetapplication.R;

/**
 * @des:
 * @author: gutao
 * @date: 2018/2/06 9:38
 */

public class CircleViewWidget extends ViewGroup {

    private final String TAG = CircleViewWidget.class.getSimpleName();

    int mAngle;

    float x;
    float y;
    ImageView mVehicleImageView, mVehicleBackgroundImageView, mVehicleShadowImageView;

    Matrix mCarMatrix = new Matrix();
    Matrix mShadowMatrix = new Matrix();
    int mCarWidth;
    int mCarHeigth;
    int mBackgroundWidth;
    int mBackgroundHeigth;
    int mShadowWidth;
    int mShadowHeigth;

    public CircleViewWidget(Context context) {
        super(context);
    }

    public CircleViewWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleViewWidget);
        mAngle = a.getInteger(R.styleable.CircleViewWidget_car_angle, 0);
        x = a.getDimension(R.styleable.CircleViewWidget_circle_x, 0);
        y = a.getDimension(R.styleable.CircleViewWidget_circle_y, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(sizeWidth, sizeHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int cWidth = childView.getMeasuredWidth();
            int cHeight = childView.getMeasuredHeight();
            if (i == 0) {
                mVehicleBackgroundImageView = (ImageView) childView;
                mBackgroundWidth = cWidth;
                mBackgroundHeigth = cHeight;
            }
            if (i == 1) {
                mVehicleShadowImageView = (ImageView) childView;
                mShadowWidth = cWidth;
                mShadowHeigth = cHeight;
            }
            if (i == 2) {
                mVehicleImageView = (ImageView) childView;
                mCarWidth = cWidth;
                mCarHeigth = cHeight;
                break;
            }
        }
        int xL = 0, yT = 0, xR = 0, yB = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int cWidth = childView.getMeasuredWidth();
            int cHeight = childView.getMeasuredHeight();
            if (i == 0) {
                xL = (int) x - mBackgroundWidth / 2;
                yT = (int) y - mBackgroundHeigth / 2;
                xR = (int) x + mBackgroundWidth / 2;
                yB = (int) y + mBackgroundHeigth / 2;
            } else if (i == 1) {
                xL = (int) (x - mShadowWidth / 2 + mShadowWidth / 2 - mCarWidth / 2);
                yT = (int) ((y - mShadowHeigth / 2) + (mShadowHeigth / 2 - mCarHeigth / 2));
                xR = (int) ((x - mShadowWidth / 2 + mShadowWidth / 2 - mCarWidth / 2) + mShadowWidth);
                yB = (int) (((y - mShadowHeigth / 2) + (mShadowHeigth / 2 - mCarHeigth / 2)) + mShadowHeigth);
            } else if (i == 2) {
                xL = (int) x - mCarWidth / 2;
                yT = (int) y - mCarHeigth / 2;
                xR = (int) x + mCarWidth / 2;
                yB = (int) y + mCarHeigth / 2;
            } else {
                if (childView instanceof MyImageButton) {
                    MyImageButton myImageButton = (MyImageButton) childView;
                    int xTrans = myImageButton.getxTrans();
                    int yTrans = myImageButton.getyTrans();
                    Log.d(TAG,"xTrans="+xTrans+" yTrans="+yTrans);
                    xL = (int) x - cWidth / 2 + xTrans;
                    yT = (int) y - cHeight / 2 + yTrans;
                    xR = (int) x + cWidth / 2 + xTrans;
                    yB = (int) y + cHeight / 2 + yTrans;
                }
            }
            childView.layout(xL, yT, xR, yB);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mAngle != 0) {
            Log.d(TAG, "dispatchDraw");
            mCarMatrix.reset();
            mShadowMatrix.reset();

            mCarMatrix.preTranslate(mCarWidth / 2, mCarHeigth / 2);
            mCarMatrix.preRotate(mAngle);
            mCarMatrix.preTranslate(-mCarWidth / 2, -mCarHeigth / 2);
            mVehicleImageView.setImageMatrix(mCarMatrix);


            mShadowMatrix.preTranslate(mCarWidth / 2, mCarHeigth / 2);
            mShadowMatrix.preRotate(mAngle);
            mShadowMatrix.preTranslate(-mCarWidth / 2, -mCarHeigth / 2);
            mVehicleShadowImageView.setImageMatrix(mShadowMatrix);
        }

    }
}
