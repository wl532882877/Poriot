package com.pay.poriot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.ui.adapter.WalletDetailFragmentAdapter;
import com.pay.poriot.ui.adapter.WalletPageFragmentAdapter;
import com.pay.poriot.util.DensityUtils;
import com.pay.poriot.util.ViewUtil;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.TextWidthColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 钱包详情
 */
public class WalletDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.wallet_address)
    TextView mTvWalletAddress;
    @BindView(R.id.indicator_view)
    ScrollIndicatorView indicatorView;
    @BindView(R.id.vp_load_wallet)
    ViewPager vpLoadWallet;
    private String tokenName;
    private String walletAddress;
    private String assetName;
    private WalletDetailFragmentAdapter walletDetailFragmentAdapter;
    private IndicatorViewPager indicatorViewPager;

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    public static void startActivity(Context activity, String name, String address, String assetName) {
        Intent intent = new Intent(activity, WalletDetailActivity.class);
        intent.putExtra("token_name", name);
        intent.putExtra("wallet_address", address);
        intent.putExtra("asset_name", assetName);
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
        tokenName = getIntent().getStringExtra("token_name");
        walletAddress = getIntent().getStringExtra("wallet_address");
        assetName = getIntent().getStringExtra("asset_name");
        setTitleText(tokenName);
        setTitleLeftIcon(R.drawable.btn_back_black, this);
        setTitleRightIcon(R.mipmap.bg_assets, this);
        mTvWalletAddress.setText(walletAddress);

        indicatorView.setSplitAuto(true);
        indicatorView.setOnTransitionListener(new OnTransitionTextListener()
                .setColor(getResources().getColor(R.color.color_ff), getResources().getColor(R.color.color_2))
                .setSize(14, 14));
        indicatorView.setScrollBar(new TextWidthColorBar(this, indicatorView, getResources().getColor(R.color.color_ff), DensityUtils.dip2px(2)));
        indicatorView.setScrollBarSize(50);
        indicatorViewPager = new IndicatorViewPager(indicatorView, vpLoadWallet);
        walletDetailFragmentAdapter = new WalletDetailFragmentAdapter(this, getSupportFragmentManager());
        indicatorViewPager.setAdapter(walletDetailFragmentAdapter);
        indicatorViewPager.setCurrentItem(0, false);
        vpLoadWallet.setOffscreenPageLimit(3);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
            case R.id.ll_title_right:
                AssetInfoActivity.startActivity(this, tokenName, assetName);
                break;
            case R.id.bt_transfer_account:
                SelectAddressActivity.startActivity(this);
                break;
            case R.id.bt_collection:
                QRCollectionActivity.startActivity(this, tokenName, walletAddress);
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
