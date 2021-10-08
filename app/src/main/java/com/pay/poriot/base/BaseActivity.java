package com.pay.poriot.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.pay.poriot.R;
import com.pay.poriot.bean.EventBusModel;
import com.pay.poriot.dialog.CustomDialog;
import com.pay.poriot.util.BusUtil;
import com.pay.poriot.util.DensityUtils;
import com.pay.poriot.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity {
    public static final int TITLE_HEIGHT = 140;
    protected String TAG = BaseActivity.class.getName();
    protected Activity mContext;
    protected T mPresenter;
    protected abstract T getPresenter();
    protected View mViewTitle;
    protected TextView mTvTitle;
    protected TextView mTvTitleLeft;
    protected TextView mTvTitleRight;
    private View mViewTitleLeft;
    protected View mViewTitleRight;
    protected View mViewStatusBar;
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        mContext = this;
        ActivityCollector.addActivity(this);
        BusUtil.register(this);
        mPresenter = getPresenter();
    }


    public void showDialog(String progressTip) {
        getDialog().show();
        if (progressTip != null) {
            getDialog().setTvProgress(progressTip);
        }
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(this);
            dialog.setCancelable(true);
        }
        return dialog;
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    protected void initTitle(){
        mViewTitle = findViewById(R.id.view_title);
        mViewStatusBar = findViewById(R.id.view_status_bar);
        showStatusBar(false);
        if (mViewTitle != null) {
            measure(mViewTitle, 0, TITLE_HEIGHT);
            mViewTitleLeft = findViewById(R.id.ll_title_left);
            mViewTitleRight = findViewById(R.id.ll_title_right);
            mTvTitle = findViewById(R.id.tv_title_mid);
            mTvTitleLeft = findViewById(R.id.tv_title_left);
            mTvTitleRight = findViewById(R.id.tv_title_right);
        }
    }

    protected void setTitleText(String title) {
        if (null != mTvTitle) {
            mTvTitle.setText(title);
        }
    }

    protected void setTitleLeftIcon(int resId, View.OnClickListener listener) {
        if (null != mTvTitleLeft) {
            mTvTitleLeft.setBackgroundResource(resId);
            measure(mTvTitleLeft, 40, 40);
        }
        if (null != mViewTitleLeft) {
            mViewTitleLeft.setVisibility(View.VISIBLE);
        }
        if (null != mViewTitleLeft & null != listener) {
            mViewTitleLeft.setOnClickListener(listener);
        }
    }

    protected void setTitleRightText(String title, View.OnClickListener listener) {
        if (null != mTvTitleRight) {
            mTvTitleRight.setText(title);
        }
        if (null != mViewTitleRight) {
            mViewTitleRight.setVisibility(View.VISIBLE);
        }
        if (null != listener & null != mViewTitleRight) {
            mViewTitleRight.setOnClickListener(listener);
        }
    }

    protected void setTitleRightIcon(int resId, View.OnClickListener listener) {
        if (null != mTvTitleRight) {
            mTvTitleRight.setBackgroundResource(resId);
            measure(mTvTitleRight, 50, 50);
        }
        if (null != mViewTitleRight) {
            mViewTitleRight.setVisibility(View.VISIBLE);
        }
        if (null != listener & null != mViewTitleRight) {
            mViewTitleRight.setOnClickListener(listener);
        }
    }


    protected void showStatusBar(boolean show) {
        if (null == mViewStatusBar) {
            return;
        }
        if (show) {
            mViewStatusBar.setVisibility(View.VISIBLE);
            DensityUtils.setViewHeight(mViewStatusBar, DensityUtils.getStatusHeight());
        } else {
            mViewStatusBar.setVisibility(View.GONE);
        }
    }


    protected void measure(View view, int width, int height) {
        DensityUtils.measure(view, width, height);
    }

    public void showToast(final String msg) {
        runOnUiThread(() -> ToastUtil.show(BaseActivity.this, msg));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager mFragmentManager = getSupportFragmentManager();
        List<Fragment> list = mFragmentManager.getFragments();
        for (Fragment fragment : list) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1) {
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusModel model) {
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        ActivityCollector.removeActivity(this);
        BusUtil.unregister(this);
    }
}
