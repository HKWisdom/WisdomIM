package com.wisdom.im.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hyphenate.util.DensityUtil;
import com.wisdom.im.R;

/**
 * Created by HKWisdom on 2017/3/31.
 */

public class SliderBar extends View {

    private static final String[] SECTIONS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Paint mPaint;
    private float mUnitHeight;
    private float mBaseLine;
    private int currentIndex = 0;
    private OnIndexChangeListener mOnIndexChangeListener;

    public SliderBar(Context context) {
        this(context, null);
    }

    public SliderBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(DensityUtil.sp2px(getContext(), 12)); //12sp
        mPaint.setColor(Color.parseColor("#8c8c8c"));
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < SECTIONS.length; i++) {
            float x = getWidth() / 2;
            float y = mBaseLine + mUnitHeight * i;
            canvas.drawText(SECTIONS[i], x, y, mPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //计算view的高度
        mUnitHeight = getHeight() * 1.0f / SECTIONS.length;

        //获取字体的矩阵
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        mBaseLine = mUnitHeight / 2 + (textHeight / 2 - fontMetrics.descent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundResource(R.drawable.sliderbar_pres);
                changeIndex(event);
                break;

            case MotionEvent.ACTION_UP:
                setBackgroundResource(0);//清空之前的背景
                setBackgroundColor(Color.TRANSPARENT);

                if (mOnIndexChangeListener != null) {
                    mOnIndexChangeListener.onFinish();
                }
                break;

            case MotionEvent.ACTION_MOVE:

                changeIndex(event);
                break;
            default:
                break;
        }
        return true;
    }

    private void changeIndex(MotionEvent event) {
        int index = (int) (event.getY() / mUnitHeight);//获取当前字母的索引
        if (index < 0) {
            index = 0;
        } else if (index > 25) {
            index = 25;
        }
        //把索引传给fragment
        if (mOnIndexChangeListener != null && index != currentIndex) {
            mOnIndexChangeListener.onIndexChange(SECTIONS[index]);
        }
    }


    public void setOnIndexChangeListener(OnIndexChangeListener onIndexChangeListener) {
        mOnIndexChangeListener = onIndexChangeListener;
    }

    public interface OnIndexChangeListener {
        void onIndexChange(String latter);
        void onFinish();
    }
}
