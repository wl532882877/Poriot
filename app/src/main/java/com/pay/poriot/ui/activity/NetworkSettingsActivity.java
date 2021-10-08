package com.pay.poriot.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.ui.widget.MyItemView;
import com.pay.poriot.util.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 网络设置
 */
public class NetworkSettingsActivity extends BaseActivity implements View.OnClickListener, MyItemView.OnRootClickListener {
    @BindView(R.id.ethereum_main_net)
    MyItemView ethereumMainNet;
    @BindView(R.id.poriot_net)
    MyItemView poriotNet;
    @BindView(R.id.bsc)
    MyItemView bscNet;
    @BindView(R.id.huobi_eco)
    MyItemView huobiEcoNet;
    @BindView(R.id.tron)
    MyItemView tronNet;

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, NetworkSettingsActivity.class);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_setting);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString(R.string.network_settings));
        setTitleLeftIcon(R.drawable.btn_back_black, this);
        ethereumMainNet.initMine(R.mipmap.bg_ethereum_state, getString(R.string.ethereum_net), "", true).setOnRootClickListener(this, 1);
        poriotNet.initMine(R.mipmap.ic_launcher, getString(R.string.poriot_net), "", true).setOnRootClickListener(this, 2);
        bscNet.initMine(R.mipmap.bg_bsc_state, getString(R.string.bsc_net), "", true).setOnRootClickListener(this, 3);
        huobiEcoNet.initMine(R.mipmap.bg_huobi_state, getString(R.string.huobi_net), "", true).setOnRootClickListener(this, 4);
        tronNet.initMine(R.mipmap.bg_tron_state, getString(R.string.tron_net), "", true).setOnRootClickListener(this, 5);
    }

    @Override
    public void onRootClick(View view) {
        int position = (int) view.getTag();
        InformationActivity.startActivity(this,position);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_left:
                finish();
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
