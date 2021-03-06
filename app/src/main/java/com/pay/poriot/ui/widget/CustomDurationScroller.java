package com.pay.poriot.ui.widget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class CustomDurationScroller extends Scroller {

    private double mScrollFactor = 1;

    /**
     * 构造函数
     *
     * @param context 上下文对象
     */
    public CustomDurationScroller(Context context) {
        super(context);
    }

    /**
     * 构造函数
     *
     * @param context      上下文对象
     * @param interpolator 加速器
     */
    public CustomDurationScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    /**
     * Set the factor by which the duration will change
     *
     * @param scrollFactor 切换时间
     */
    public void setScrollDurationFactor(double scrollFactor) {
        this.mScrollFactor = scrollFactor;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        int scrollDuration = (int) (duration * mScrollFactor);
        super.startScroll(startX, startY, dx, dy, scrollDuration);
    }
}
