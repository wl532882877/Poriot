package com.pay.poriot.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.config.Config;
import com.pay.poriot.util.ViewUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 信息
 */
public class InformationActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_term)
    TextView mTvTerm;
    @BindView(R.id.tv_chain)
    TextView mTvChain;
    @BindView(R.id.tv_symbol)
    TextView mTvSymbol;
    private int position;

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    public static void startActivity(Activity activity, int position) {
        Intent intent = new Intent(activity, InformationActivity.class);
        intent.putExtra(Config.POSITION, position);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString(R.string.infromation));
        setTitleLeftIcon(R.drawable.btn_back_black, this);
        position = getIntent().getIntExtra(Config.POSITION, 1);
        switch (position) {
            case 1:
                mTvTerm.setText(getString(R.string.ethereum_net));
                mTvChain.setText("1");
                mTvSymbol.setText("ETH");
                break;
            case 2:
                mTvTerm.setText(getString(R.string.poriot_net));
                mTvChain.setText("128");
                mTvSymbol.setText("ZK");
                break;
            case 3:
                mTvTerm.setText(getString(R.string.bsc_net));
                mTvChain.setText("56");
                mTvSymbol.setText("BNB");
                break;
            case 4:
                mTvTerm.setText(getString(R.string.huobi_net));
                mTvChain.setText("128");
                mTvSymbol.setText("HT");
                break;
            case 5:
                mTvTerm.setText(getString(R.string.tron_net));
                mTvChain.setText("1");
                mTvSymbol.setText("TRX");
                break;
            default:
                break;
        }
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
