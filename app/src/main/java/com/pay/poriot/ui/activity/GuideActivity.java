package com.pay.poriot.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.listener.IPageChangeListener;
import com.pay.poriot.ui.adapter.GuideAdapter;
import com.pay.poriot.ui.widget.CircleFlowIndicator;
import com.pay.poriot.util.DensityUtils;
import com.pay.poriot.util.LanguageUtil;
import com.pay.poriot.util.ViewUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends BaseActivity {
    @BindView(R.id.vp_guide)
    ViewPager mVpGuide;
    @BindView(R.id.view_indicator)
    CircleFlowIndicator mFlowIndicator;
    private List<Integer> mResIds;
    private GuideAdapter mGuideAdapter;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, GuideActivity.class);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
        mResIds = new ArrayList<>();
        String lan = LanguageUtil.getAppLanguage();
        if (lan.equals("en")) {
            mResIds.add(R.mipmap.ic_guide_4);
            mResIds.add(R.mipmap.ic_guide_5);
            mResIds.add(R.mipmap.ic_guide_6);
        } else {
            mResIds.add(R.mipmap.ic_guide_1);
            mResIds.add(R.mipmap.ic_guide_2);
            mResIds.add(R.mipmap.ic_guide_3);
        }
        mGuideAdapter = new GuideAdapter(this, mResIds);
    }

    private void initViews() {
        DensityUtils.setMargins(mFlowIndicator, 0, 500, 0, 0);
        mVpGuide.setAdapter(mGuideAdapter);
        mFlowIndicator.setCount(mResIds.size());
        mVpGuide.addOnPageChangeListener(new IPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mFlowIndicator.setSelection(position % mResIds.size());
            }
        });
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }
}
