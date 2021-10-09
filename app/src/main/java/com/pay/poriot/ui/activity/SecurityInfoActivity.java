package com.pay.poriot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.dialog.BackupMneBottomPopup;
import com.pay.poriot.dialog.ChangeNameBottomPopup;
import com.pay.poriot.util.ViewUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 安全信息
 */
public class SecurityInfoActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.wallet_name)
    TextView mTvWalletName;
    @BindView(R.id.wallet_address)
    TextView mTvWalletAddress;
    private String walletName;
    private String walletAddress;

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    public static void startActivity(Context activity, String name, String address) {
        Intent intent = new Intent(activity, SecurityInfoActivity.class);
        intent.putExtra("wallet_name", name);
        intent.putExtra("wallet_address", address);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_info);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString(R.string.security_information));
        setTitleLeftIcon(R.drawable.btn_back_black, this);
        walletName = getIntent().getStringExtra("wallet_name");
        walletAddress = getIntent().getStringExtra("wallet_address");
        mTvWalletName.setText(walletName);
        mTvWalletAddress.setText(walletAddress);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
            case R.id.ll_change_name:
                new XPopup.Builder(this)
                        .isViewMode(true)
                        .autoOpenSoftInput(true)
                        .moveUpToKeyboard(false)
                        .enableDrag(true)
                        .asCustom(new ChangeNameBottomPopup(this))
                        .show();
                break;
            case R.id.ll_export_private_key:
            case R.id.ll_export_keystore:
                new XPopup.Builder(this)
                        .isViewMode(true)
                        .autoOpenSoftInput(true)
                        .moveUpToKeyboard(false)
                        .enableDrag(true)
                        .asCustom(new BackupMneBottomPopup(this))
                        .show();
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
