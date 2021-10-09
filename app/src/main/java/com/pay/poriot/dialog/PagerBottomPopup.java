package com.pay.poriot.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.pay.poriot.R;
import com.pay.poriot.dao.ETHWallet;
import com.pay.poriot.entity.Tab;
import com.pay.poriot.interact.FetchWalletInteract;
import com.pay.poriot.viewmodel.TokensViewModel;
import com.pay.poriot.viewmodel.TokensViewModelFactory;
import java.util.ArrayList;
import java.util.List;

public class PagerBottomPopup extends BottomPopupView {
    FetchWalletInteract fetchWalletInteract;
    private WalletAdapter walletAdapter;
    private TabAdapter tabAdapter;
    private RecyclerView walletRecy;
    private RecyclerView tabRecy;
    private TextView mTvAdd;
    private List<ETHWallet> walletList;
    private List<Tab> tabList;
    private TokensViewModel tokensViewModel;
    TokensViewModelFactory tokensViewModelFactory;
    private FragmentActivity mActivity;

    public PagerBottomPopup(@NonNull Context context, FragmentActivity activity) {
        super(context);
        this.mActivity = activity;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_pager_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initData();
        initView();
    }

    private void initData() {
        walletList = new ArrayList<>();
        tabList = new ArrayList<>();
        fetchWalletInteract = new FetchWalletInteract();
        fetchWalletInteract.fetch().subscribe(this::showWallet);
        tokensViewModelFactory = new TokensViewModelFactory();
        tokensViewModel = ViewModelProviders.of(mActivity, tokensViewModelFactory)
                .get(TokensViewModel.class);
    }


    private void initView() {
        walletRecy = findViewById(R.id.recy_wallet);
        tabRecy = findViewById(R.id.recy_tab);
        mTvAdd = findViewById(R.id.tv_add);
        walletAdapter = new WalletAdapter();
        walletRecy.setLayoutManager(new LinearLayoutManager(getContext()));
        walletRecy.setAdapter(walletAdapter);
        walletAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ETHWallet> wallet = adapter.getData();
                tokensViewModel.updateDefaultWallet(wallet.get(position).getId());
                walletAdapter.notifyDataSetChanged();
                dismiss();
            }
        });
        tabAdapter = new TabAdapter();
        tabRecy.setLayoutManager(new LinearLayoutManager(getContext()));
        tabRecy.setAdapter(tabAdapter);
        tabAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < tabList.size(); i++) {
                    if (position == i) {
                        tabList.get(i).setCurrent(true);
                    } else {
                        tabList.get(i).setCurrent(false);
                    }
                }
                tabAdapter.notifyDataSetChanged();
            }
        });
        setTab();
        mTvAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new XPopup.Builder(getContext())
                        .isViewMode(true)
                        .asCustom(new WalltCreatBottomPopup(getContext()))
                        .show();
            }
        });
    }

    private void setTab() {
        Tab tab = new Tab();
        tab.setName("Ethereum");
        tab.setCurrent(false);

        Tab tab1 = new Tab();
        tab1.setName("Poriot");
        tab1.setCurrent(true);


        Tab tab2 = new Tab();
        tab2.setName("BSC");
        tab2.setCurrent(false);


        Tab tab3 = new Tab();
        tab3.setName("HECO");
        tab3.setCurrent(false);


        Tab tab4 = new Tab();
        tab4.setName("Tron");
        tab4.setCurrent(false);

        tabList.add(tab);
        tabList.add(tab1);
        tabList.add(tab2);
        tabList.add(tab3);
        tabList.add(tab4);
        tabAdapter.replaceData(tabList);
        tabAdapter.notifyDataSetChanged();
    }

    public void showWallet(final List<ETHWallet> ethWallets) {
        walletList.clear();
        walletList.addAll(ethWallets);
        walletAdapter.replaceData(walletList);
        walletAdapter.notifyDataSetChanged();
    }

    public class WalletAdapter extends BaseQuickAdapter<ETHWallet, BaseViewHolder> {
        public WalletAdapter() {
            super(R.layout.list_item_wallet);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, ETHWallet bean) {
            viewHolder.setText(R.id.wallet_name, bean.getName())
                    .setText(R.id.wallet_address, bean.getAddress())
                    .setVisible(R.id.iv_current, bean.getIsCurrent());
        }
    }

    public class TabAdapter extends BaseQuickAdapter<Tab, BaseViewHolder> {
        public TabAdapter() {
            super(R.layout.item_tab_list);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Tab bean) {
            LinearLayout mLlRoot = viewHolder.getView(R.id.ll_root);
            if (bean.isCurrent()) {
                mLlRoot.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_fafafa));
            } else {
                mLlRoot.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            }
            ImageView mIvIcon = viewHolder.getView(R.id.iv_icon);
            if ("Ethereum".equals(bean.getName())) {
                if (bean.isCurrent()) {
                    mIvIcon.setImageResource(R.mipmap.bg_ethereum_state);
                } else {
                    mIvIcon.setImageResource(R.mipmap.bg_ethereum_nomal);
                }
            } else if ("Poriot".equals(bean.getName())) {
                if (bean.isCurrent()) {
                    mIvIcon.setImageResource(R.mipmap.wallet_icon);
                } else {
                    mIvIcon.setImageResource(R.mipmap.bg_poroit_nomal);
                }
            } else if ("BSC".equals(bean.getName())) {
                if (bean.isCurrent()) {
                    mIvIcon.setImageResource(R.mipmap.bg_bsc_state);
                } else {
                    mIvIcon.setImageResource(R.mipmap.bg_bsc_nomal);
                }
            } else if ("HECO".equals(bean.getName())) {
                if (bean.isCurrent()) {
                    mIvIcon.setImageResource(R.mipmap.bg_huobi_state);
                } else {
                    mIvIcon.setImageResource(R.mipmap.bg_huobi_nomal);
                }
            } else {
                if (bean.isCurrent()) {
                    mIvIcon.setImageResource(R.mipmap.bg_tron_state);
                } else {
                    mIvIcon.setImageResource(R.mipmap.bg_tron_nomal);
                }
            }
        }
    }

    @Override
    protected void onShow() {
        super.onShow();
    }

    @Override
    protected void onDismiss() {
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getScreenHeight(getContext()) * .7f);
    }
}
