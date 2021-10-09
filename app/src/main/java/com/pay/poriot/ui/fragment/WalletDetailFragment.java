package com.pay.poriot.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseFragment;
import com.pay.poriot.base.IPresenter;
import butterknife.ButterKnife;

public class WalletDetailFragment extends BaseFragment {
    private static final String KEY_TAB_POSITION = "KEY_TAB_POSITION";

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    public static WalletDetailFragment newInstance(int position) {
        WalletDetailFragment walletDetailFragment = new WalletDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TAB_POSITION, position);
        walletDetailFragment.setArguments(bundle);
        return walletDetailFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initVariables();
        View mRootView = inflater.inflate(R.layout.fragment_wallet_detail, container, false);
        initViews(mRootView);
        return mRootView;
    }

    private void initVariables() {

    }

    private void initViews(final View view) {
        ButterKnife.bind(this, view);
    }
}
