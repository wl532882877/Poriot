package com.pay.poriot.presenter;

import android.app.Activity;
import com.pay.poriot.base.BasePresenter;
import com.pay.poriot.ui.view.MainView;

public class MainPre extends BasePresenter<MainView> {

    public MainPre(Activity activity, MainView view) {
        super(activity, view);
    }
}
