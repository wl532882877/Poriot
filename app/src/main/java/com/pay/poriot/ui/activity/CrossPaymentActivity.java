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
 * 跨入、跨出
 */
public class CrossPaymentActivity extends BaseActivity implements View.OnClickListener {
    private int type;

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    public static void startActivity(Context activity, int type) {
        Intent intent = new Intent(activity, CrossPaymentActivity.class);
        intent.putExtra("payType", type);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_payment);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("payType", 0);
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString(type == 0 ? R.string.forward : R.string.step));
        setTitleLeftIcon(R.drawable.btn_back_black, this);
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
