package com.pay.poriot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.util.ViewUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 钱包详情
 */
public class WalletDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.wallet_address)
    TextView mTvWalletAddress;
    private String walletName;
    private String walletAddress;

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    public static void startActivity(Context activity, String name, String address) {
        Intent intent = new Intent(activity, WalletDetailActivity.class);
        intent.putExtra("wallet_name", name);
        intent.putExtra("wallet_address", address);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
    }

    private void initViews() {
        super.initTitle();
        walletName = getIntent().getStringExtra("wallet_name");
        walletAddress = getIntent().getStringExtra("wallet_address");
        setTitleText(walletName);
        setTitleLeftIcon(R.drawable.btn_back_black, this);
        setTitleRightIcon(R.mipmap.bg_assets, this);
        mTvWalletAddress.setText(walletAddress);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
            case R.id.ll_title_right:

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
