package com.pay.poriot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.util.ViewUtil;
import butterknife.ButterKnife;

/**
 * 选择地址
 */
public class SelectAddressActivity extends BaseActivity implements View.OnClickListener{
    private static final int QRCODE_SCANNER_REQUEST = 1100;
    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    public static void startActivity(Context activity) {
        Intent intent = new Intent(activity, SelectAddressActivity.class);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString(R.string.select_address));
        setTitleLeftIcon(R.drawable.btn_back_black, this);
        setTitleRightIcon(R.mipmap.bg_scan,this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
            case R.id.ll_title_right:
                Intent intent = new Intent(mContext, QRCodeScannerActivity.class);
                startActivityForResult(intent, QRCODE_SCANNER_REQUEST);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QRCODE_SCANNER_REQUEST) {
            if (data != null) {
                String scanResult = data.getStringExtra("scan_result");
            }
        }
    }
}
