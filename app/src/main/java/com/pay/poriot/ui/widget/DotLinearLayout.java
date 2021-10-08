package com.pay.poriot.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.pay.poriot.util.DensityUtils;

public class DotLinearLayout extends LinearLayout {

    public DotLinearLayout(Context context) {
        super(context);
        init();
    }

    public DotLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DotLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
    }


    /**
     * link viewPager 指示器
     *
     * @param dotSize     dot圆点的长宽
     * @param marginSpace dot圆点的间距
     * @param dotCounts   需要显示的原点数量
     */
    public void addDots(int dotSize, int marginSpace, int dotCounts) {
        removeAllViews();
        for (int i = 0; i < dotCounts; i++) {
            View view = new View(getContext());
            LayoutParams layoutParams = new LayoutParams(DensityUtils.getMeasureValue(dotSize),
                    DensityUtils.getMeasureValue(dotSize));
            if (i > 0) {
                layoutParams.leftMargin = DensityUtils.getMeasureValue(marginSpace);
            }
            view.setLayoutParams(layoutParams);
            addView(view);
        }
        setSelectPosition(0);
    }

    public void setSelectPosition(int selectPosition) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView == null) {
                return;
            }
        }
    }
}
