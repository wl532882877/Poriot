package com.pay.poriot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.presenter.AboutPre;
import com.pay.poriot.ui.view.AboutView;
import com.pay.poriot.util.DensityUtils;
import com.pay.poriot.util.PackageUtil;
import com.pay.poriot.util.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 关于我们
 */
public class AboutActivity extends BaseActivity<AboutPre> implements AboutView, View.OnClickListener {

    @BindView(R.id.iv_about)
    ImageView mIvAbout;
    @BindView(R.id.tv_version)
    TextView mTvVersion;

    public static void startActivity(Context activity) {
        Intent intent = new Intent(activity, AboutActivity.class);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
    }
    @Override
    protected AboutPre getPresenter() {
        return new AboutPre(this,this);
    }

    private void initViews() {
        super.initTitle();
        setTitleLeftIcon(R.drawable.btn_back_black, this);
        measure(mIvAbout, 288, 288);
        DensityUtils.setMargins(mIvAbout, 0, 308, 0, 30);
        mTvVersion.setText("v".concat(PackageUtil.getVersionName()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        ViewUtil.left2RightOut(this);
    }
}
