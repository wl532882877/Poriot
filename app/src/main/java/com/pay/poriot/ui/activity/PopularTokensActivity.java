package com.pay.poriot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.entity.TokenItem;
import com.pay.poriot.ui.fragment.MainFragment;
import com.pay.poriot.util.ViewUtil;
import com.pay.poriot.viewmodel.AddTokenViewModel;
import com.pay.poriot.viewmodel.AddTokenViewModelFactory;
import com.pay.poriot.viewmodel.TokensViewModel;
import com.pay.poriot.viewmodel.TokensViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 热门代币
 */
public class PopularTokensActivity extends BaseActivity implements View.OnClickListener{
    private TokensViewModelFactory tokensViewModelFactory;
    private TokensViewModel tokensViewModel;
    protected AddTokenViewModelFactory addTokenViewModelFactory;
    private AddTokenViewModel addTokenViewModel;
    private TokenSettingAdapter settingAdapter;
    private List<TokenItem> mItems = new ArrayList<TokenItem>();

    @Override
    protected IPresenter getPresenter() {
        return null;
    }
    public static void startActivity(Context activity) {
        Intent intent = new Intent(activity, PopularTokensActivity.class);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_tokens);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString( R.string.popular_tokens));
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


    public class TokenSettingAdapter extends BaseQuickAdapter<TokenItem, BaseViewHolder> {
        public TokenSettingAdapter() {
            super(R.layout.item_token_add);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, TokenItem bean) {
            if (!bean.added) {
                viewHolder.setImageResource(R.id.iv_icon, bean.iconId)
                        .setText(R.id.tv_name, bean.tokenInfo.name)
                        .setText(R.id.tv_currency, bean.tokenInfo.symbol)
                        .setText(R.id.tv_price, bean.tokenInfo.decimals + "");
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        ViewUtil.left2RightOut(this);
    }

}
