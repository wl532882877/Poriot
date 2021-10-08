package com.pay.poriot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.util.ViewUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 二维码分享
 */
public class QRCodeShareActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.wallet_icon)
    ImageView walletIcon;
    @BindView(R.id.wallet_name)
    TextView walletName;
    @BindView(R.id.qr_code)
    ImageView qrCode;
    @BindView(R.id.wallet_address)
    TextView walletAddress;

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    public static void startActivity(Context activity) {
        Intent intent = new Intent(activity, QRCodeShareActivity.class);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_share);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString(R.string.share));
        setTitleLeftIcon(R.drawable.btn_back_black, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
            case R.id.bt_save:
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
