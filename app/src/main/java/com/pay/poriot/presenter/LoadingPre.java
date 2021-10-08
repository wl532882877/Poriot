package com.pay.poriot.presenter;

import android.app.Activity;

import com.pay.poriot.base.BasePresenter;
import com.pay.poriot.ui.view.LoadingView;

public class LoadingPre extends BasePresenter<LoadingView> {
    public LoadingPre(Activity activity, LoadingView view) {
        super(activity, view);
    }
}
