package com.pay.poriot.dialog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.pay.poriot.R;
import com.pay.poriot.ui.activity.ImportWalletActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalltCreatBottomPopup extends BottomPopupView implements View.OnClickListener {
    @BindView(R.id.iv_back)
    LinearLayout mIvBack;
    @BindView(R.id.bt_create_wallet)
    Button mBtCreatWallet;
    @BindView(R.id.bt_import_wallet)
    Button mBtImportWallet;

    public WalltCreatBottomPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_wallet_creat_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
    }


    private void initView() {
        ButterKnife.bind(this, this);
        mIvBack.setOnClickListener(this);
        mBtCreatWallet.setOnClickListener(this);
        mBtImportWallet.setOnClickListener(this);
    }


    @Override
    protected void onShow() {
        super.onShow();
    }

    @Override
    protected void onDismiss() {
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getScreenHeight(getContext()) * .7f);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                dismiss();
                break;
            case R.id.bt_create_wallet:
                break;
            case R.id.bt_import_wallet:
                ImportWalletActivity.startActivity(getContext());
                break;
        }
    }
}
