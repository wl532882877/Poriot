package com.pay.poriot.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pay.poriot.R;
import com.pay.poriot.bean.EventBusModel;
import com.pay.poriot.dialog.CustomDialog;
import com.pay.poriot.util.BusUtil;
import com.pay.poriot.util.DensityUtils;
import com.pay.poriot.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseFragment<T extends IPresenter> extends Fragment {
    public static final int TITLE_HEIGHT = 140;
    protected T mPresenter;

    protected abstract T getPresenter();

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;
    protected View mViewTitle;
    protected TextView mTvTitle;
    protected TextView mTvTitleLeft;
    private View mViewTitleLeft;
    protected View mViewStatusBar;
    private CustomDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusUtil.register(this);
        mPresenter = getPresenter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow()
                .getDecorView()
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            getActivity().getWindow()
                                    .getDecorView()
                                    .getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);
                        } else {
                            getActivity().getWindow()
                                    .getDecorView()
                                    .getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }
                    }
                });

        isViewCreated = true;
        lazyLoad();
    }

    protected void initTitle(View layout){
        mViewTitle = layout.findViewById(R.id.view_title);
        mViewStatusBar = layout.findViewById(R.id.view_status_bar);
        showStatusBar(false);
        if (mViewTitle != null) {
            measure(mViewTitle, 0, TITLE_HEIGHT);
            mViewTitleLeft = layout.findViewById(R.id.ll_title_left);
            mTvTitle = layout.findViewById(R.id.tv_title_mid);
            mTvTitleLeft = layout.findViewById(R.id.tv_title_left);
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
            measure(mTvTitleLeft, 92, 92);
        }
        if (null != mViewTitleLeft) {
            mViewTitleLeft.setVisibility(View.VISIBLE);
        }
        if (null != mViewTitleLeft & null != listener) {
            mViewTitleLeft.setOnClickListener(listener);
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
            dialog = CustomDialog.instance(getContext());
            dialog.setCancelable(true);
        }
        return dialog;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }


    /**
     * 懒加载
     */
    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            lazyLoadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    public void lazyLoadData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusModel model) {

    }

    protected void measure(View view, int width, int height) {
        DensityUtils.measure(view, width, height);
    }

    public void showToast(final String msg) {
        getActivity().runOnUiThread(() -> ToastUtil.show(getActivity(), msg));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        BusUtil.unregister(this);
    }

}
