package com.pay.poriot.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseFragment;
import com.pay.poriot.dao.ETHWallet;
import com.pay.poriot.dialog.PagerBottomPopup;
import com.pay.poriot.entity.Ticker;
import com.pay.poriot.entity.Token;
import com.pay.poriot.entity.TokenInfo;
import com.pay.poriot.interact.FetchWalletInteract;
import com.pay.poriot.listener.IPageChangeListener;
import com.pay.poriot.presenter.MainPre;
import com.pay.poriot.ui.activity.CrossPaymentActivity;
import com.pay.poriot.ui.activity.PopularTokensActivity;
import com.pay.poriot.ui.activity.WalletDetailActivity;
import com.pay.poriot.ui.adapter.HomeBannerAdapter;
import com.pay.poriot.ui.view.MainView;
import com.pay.poriot.ui.widget.AutoScrollViewPager;
import com.pay.poriot.util.BalanceUtils;
import com.pay.poriot.viewmodel.TokensViewModel;
import com.pay.poriot.viewmodel.TokensViewModelFactory;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 钱包首页
 */
public class MainFragment extends BaseFragment<MainPre> implements View.OnClickListener, MainView {
    @BindView(R.id.refrsh_wallet)
    SmartRefreshLayout refreshWallet;
    @BindView(R.id.recy_wallet)
    RecyclerView recyWallet;
    @BindView(R.id.ll_wallet)
    LinearLayout mRlWallet;
    private TextView mLayer;
    private TextView mLayer1;
    private TextView mLayer2;
    private LinearLayout mLlCrossOut;
    private LinearLayout mLlCrossInto;
    private ImageView mIvAdd;
    private WalletAdapter walletAdapter;
    private AutoScrollViewPager mVpWallet;
    private HomeBannerAdapter homeBannerAdapter;
    private FetchWalletInteract fetchWalletInteract;
    private List<ETHWallet> walletList;
    private List<Token> tokenItems;
    private TokensViewModelFactory tokensViewModelFactory;
    private TokensViewModel tokensViewModel;
    private List<TokenItem> mItems = new ArrayList<>();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected MainPre getPresenter() {
        return new MainPre(getActivity(), this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initVariables();
        View mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        initData();
        initViews(mRootView);
        return mRootView;
    }

    private void initData() {
        mItems.add(new TokenItem(new TokenInfo("0xB8c77482e45F1F44dE1745F52C74426C631bDD52", "ZKT", "ZKT Token", 0), true, R.mipmap.wallet_icon));
        mItems.add(new TokenItem(new TokenInfo("0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48", "ETH-ZK", "ETH-ZK", 0), true, R.mipmap.bg_eth_zk));
        mItems.add(new TokenItem(new TokenInfo("0x9f8f72aa9304c8b593d555f12ef6589cc3a579a2", "USTD-ZK", "USTD-ZK", 0), true, R.mipmap.bg_ustd_zk));
        mItems.add(new TokenItem(new TokenInfo("0xd850942ef8811f2a866692a623011bde52a462c1", "ZK", "ZK", 0), false, R.mipmap.wallet_icon));
    }

    private void initVariables() {
        walletList = new ArrayList<>();
        homeBannerAdapter = new HomeBannerAdapter(getActivity(), walletList);
        fetchWalletInteract = new FetchWalletInteract();
        fetchWalletInteract.fetch().subscribe(
                this::showWalletList
        );
        tokensViewModelFactory = new TokensViewModelFactory();
        tokensViewModel = ViewModelProviders.of(this.getActivity(), tokensViewModelFactory).get(TokensViewModel.class);
        tokensViewModel.tokens().observe(getActivity(), this::onTokens);
        tokensViewModel.prices().observe(getActivity(), this::onPrices);
    }

    private void initViews(View view) {
        super.initTitle(view);
        ButterKnife.bind(this, view);
        mRlWallet.setOnClickListener(this);

        walletAdapter = new WalletAdapter();
        recyWallet.setLayoutManager(new LinearLayoutManager(getContext()));
        recyWallet.setAdapter(walletAdapter);
        walletAdapter.replaceData(mItems);
        walletAdapter.notifyDataSetChanged();

        refreshWallet.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshWallet.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });
        View header = LayoutInflater.from(getContext()).inflate(R.layout.view_wallet_header, recyWallet, false);
        mVpWallet = header.findViewById(R.id.vp_wallet);
        mLayer1 = header.findViewById(R.id.layer1);
        mLayer = header.findViewById(R.id.layer);
        mLayer2 = header.findViewById(R.id.layer2);
        mLlCrossOut = header.findViewById(R.id.ll_cross_out);
        mLlCrossInto = header.findViewById(R.id.ll_cross_into);
        mIvAdd = header.findViewById(R.id.iv_add);

        mLayer1.setOnClickListener(this);
        mLayer2.setOnClickListener(this);
        mLlCrossOut.setOnClickListener(this);
        mLlCrossInto.setOnClickListener(this);
        mIvAdd.setOnClickListener(this);
        walletAdapter.addHeaderView(header);
        walletAdapter.openLoadAnimation();

        mVpWallet.setCycle(false);
        mVpWallet.setAdapter(homeBannerAdapter);
        mVpWallet.setInterval(4000);
        mVpWallet.stopAutoScroll();
        mVpWallet.setScrollDurationFactor(1);
        mVpWallet.setCurrentItem(0);
        mLayer1.setTextColor(getActivity().getResources().getColor(R.color.color_2));
        mLayer2.setTextColor(getActivity().getResources().getColor(R.color.color_ff));

        mVpWallet.addOnPageChangeListener(new IPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mLayer1.setTextColor(getActivity().getResources().getColor(R.color.color_ff));
                    mLayer2.setTextColor(getActivity().getResources().getColor(R.color.color_2));
                } else {
                    mLayer1.setTextColor(getActivity().getResources().getColor(R.color.color_2));
                    mLayer2.setTextColor(getActivity().getResources().getColor(R.color.color_ff));
                }
            }
        });
        walletAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<TokenItem> tokenItems = adapter.getData();
                WalletDetailActivity.startActivity(getContext(),
                        tokenItems.get(position).tokenInfo.name,
                        tokenItems.get(position).tokenInfo.address);
            }
        });
    }


    public static class TokenItem {
        public final TokenInfo tokenInfo;
        public boolean added;
        public int iconId;

        public TokenItem(TokenInfo tokenInfo, boolean added, int id) {
            this.tokenInfo = tokenInfo;
            this.added = added;
            this.iconId = id;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        tokensViewModel.prepare();
    }

    public void showWalletList(List<ETHWallet> ethWallets) {
        walletList.clear();
        walletList.addAll(ethWallets);
        homeBannerAdapter.notifyDataSetChanged();
    }

    private void onTokens(Token[] tokens) {
        Log.e("wl", "余额 ：" + tokens[0].balance);
        tokenItems = Arrays.asList(tokens);

    }

    private void onPrices(Ticker ticker) {
        BigDecimal sum = new BigDecimal(0);
        for (Token token : tokenItems) {
            if (token.tokenInfo.symbol.equals(ticker.symbol)) {
                if (token.balance == null) {
                    token.value = "0";
                } else {
                    token.value = BalanceUtils.ethToUsd(ticker.price, token.balance);
                }
            }
            if (!TextUtils.isEmpty(token.value)) {
                sum = sum.add(new BigDecimal(token.value));
            }
        }
    }

    public class WalletAdapter extends BaseQuickAdapter<TokenItem, BaseViewHolder> {
        public WalletAdapter() {
            super(R.layout.item_home_list);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, TokenItem bean) {
            if (bean.added) {
                viewHolder.setImageResource(R.id.iv_icon, bean.iconId)
                        .setText(R.id.tv_name, bean.tokenInfo.name)
                        .setText(R.id.tv_currency, bean.tokenInfo.symbol)
                        .setText(R.id.tv_price, bean.tokenInfo.decimals + "");
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_wallet:
                new XPopup.Builder(getContext())
                        .isViewMode(true)
                        .asCustom(new PagerBottomPopup(getContext(), getActivity()))
                        .show();
                break;
            case R.id.layer1:
                mVpWallet.setCurrentItem(0);
                mLayer1.setTextColor(getActivity().getResources().getColor(R.color.color_ff));
                mLayer2.setTextColor(getActivity().getResources().getColor(R.color.color_2));
                break;
            case R.id.layer2:
                mVpWallet.setCurrentItem(1);
                mLayer1.setTextColor(getActivity().getResources().getColor(R.color.color_2));
                mLayer2.setTextColor(getActivity().getResources().getColor(R.color.color_ff));
                break;
            case R.id.ll_cross_out:
                CrossPaymentActivity.startActivity(getContext(), 0);
                break;
            case R.id.ll_cross_into:
                CrossPaymentActivity.startActivity(getContext(), 1);
                break;
            case R.id.iv_add:
                PopularTokensActivity.startActivity(getContext());
                break;
        }
    }
}
