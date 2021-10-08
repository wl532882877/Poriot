package com.pay.poriot.ui.activity;

import android.os.Bundle;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.dao.ETHWallet;
import com.pay.poriot.interact.FetchWalletInteract;
import com.pay.poriot.presenter.LoadingPre;
import com.pay.poriot.util.ViewUtil;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class LoadingActivity extends BaseActivity {

    @Override
    protected LoadingPre getPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new FetchWalletInteract().fetch().observeOn(AndroidSchedulers.mainThread()).delay(2, TimeUnit.SECONDS).subscribe(
                this::onWalltes, this::onError
        );
    }

    public void onWalltes(List<ETHWallet> ethWallets) {
        if (ethWallets.size() == 0) {
            GuideActivity.startActivity(LoadingActivity.this);
        } else {
            MainActivity.startActivity(LoadingActivity.this);
        }
    }

    public void onError(Throwable throwable) {
        GuideActivity.startActivity(LoadingActivity.this);
    }

    @Override
    public void finish() {
        super.finish();
        ViewUtil.left2RightOut(this);
    }
}
