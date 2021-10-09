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
 * 资产信息
 */
public class AssetInfoActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_asset_name)
    TextView mTvAssetName;
    @BindView(R.id.tv_contract_address)
    TextView mTvContractAddress;
    @BindView(R.id.tv_start_time)
    TextView mTvStarTime;
    private String tokenName;
    private String assetName;


    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    public static void startActivity(Context activity, String tokenName, String assetName) {
        Intent intent = new Intent(activity, AssetInfoActivity.class);
        intent.putExtra("token_name", tokenName);
        intent.putExtra("asset_name", assetName);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_info);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString(R.string.asset_information));
        setTitleLeftIcon(R.drawable.btn_back_black, this);
        tokenName = getIntent().getStringExtra("token_name");
        assetName = getIntent().getStringExtra("asset_name");
        mTvName.setText(tokenName);
        mTvAssetName.setText(assetName);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
        }
    }
}
