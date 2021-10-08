package com.pay.poriot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.viewpager.widget.ViewPager;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.BaseFragment;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.ui.adapter.WalletPageFragmentAdapter;
import com.pay.poriot.ui.fragment.ImportKeystoreFragment;
import com.pay.poriot.ui.fragment.ImportMnemonicFragment;
import com.pay.poriot.ui.fragment.ImportPrivateKeyFragment;
import com.pay.poriot.util.DensityUtils;
import com.pay.poriot.util.ViewUtil;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.TextWidthColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 导入钱包
 */
public class ImportWalletActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.indicator_view)
    ScrollIndicatorView indicatorView;
    @BindView(R.id.vp_load_wallet)
    ViewPager vpLoadWallet;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private WalletPageFragmentAdapter loadWalletPageFragmentAdapter;
    private IndicatorViewPager indicatorViewPager;


    @Override
    protected IPresenter getPresenter() {
        return null;
    }
    public static void startActivity(Context activity) {
        Intent intent = new Intent(activity, ImportWalletActivity.class);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_wallet);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
        fragmentList.add(new ImportPrivateKeyFragment());
        fragmentList.add(new ImportMnemonicFragment());
        fragmentList.add(new ImportKeystoreFragment());
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString(R.string.import_wallet));
        setTitleLeftIcon(R.drawable.btn_back_black, this);

        indicatorView.setSplitAuto(true);
        indicatorView.setOnTransitionListener(new OnTransitionTextListener()
                .setColor(getResources().getColor(R.color.color_168), getResources().getColor(R.color.color_ff))
                .setSize(14, 14));
        indicatorView.setScrollBar(new TextWidthColorBar(this, indicatorView, getResources().getColor(R.color.color_168), DensityUtils.dip2px(2)));
        indicatorView.setScrollBarSize(50);
        indicatorViewPager = new IndicatorViewPager(indicatorView, vpLoadWallet);
        loadWalletPageFragmentAdapter = new WalletPageFragmentAdapter(this, getSupportFragmentManager(), fragmentList);
        indicatorViewPager.setAdapter(loadWalletPageFragmentAdapter);
        indicatorViewPager.setCurrentItem(0, false);
        vpLoadWallet.setOffscreenPageLimit(3);
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
