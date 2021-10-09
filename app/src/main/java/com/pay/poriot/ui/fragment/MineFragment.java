package com.pay.poriot.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseFragment;
import com.pay.poriot.dialog.BackupMneBottomPopup;
import com.pay.poriot.dialog.PagerBottomPopup;
import com.pay.poriot.presenter.MinePre;
import com.pay.poriot.ui.activity.AboutActivity;
import com.pay.poriot.ui.activity.LanguageSettingActivity;
import com.pay.poriot.ui.activity.ModifyPasswordActivity;
import com.pay.poriot.ui.activity.NetworkSettingsActivity;
import com.pay.poriot.ui.activity.QRCodeShareActivity;
import com.pay.poriot.ui.view.MineView;
import com.pay.poriot.ui.widget.MyItemView;
import com.pay.poriot.util.CacheUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的
 */
public class MineFragment extends BaseFragment<MinePre> implements MineView, MyItemView.OnRootClickListener {
    @BindView(R.id.wallet_manager)
    MyItemView mWalletManager;
    @BindView(R.id.backup_mnemonic)
    MyItemView mBackupMnemonic;
    @BindView(R.id.change_password)
    MyItemView mChangePassword;
    @BindView(R.id.network_settings)
    MyItemView mNetworkSettings;
    @BindView(R.id.language_settings)
    MyItemView mLanguageSettings;
    @BindView(R.id.share)
    MyItemView mShare;
    @BindView(R.id.notice)
    MyItemView mNotice;
    @BindView(R.id.clean_cache)
    MyItemView mCleanCache;
    @BindView(R.id.about)
    MyItemView mAbout;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected MinePre getPresenter() {
        return new MinePre(getActivity(), this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View mRootView = inflater.inflate(R.layout.fragment_mine, container, false);
        initViews(mRootView);
        return mRootView;
    }

    private void initViews(final View view) {
        super.initTitle(view);
        ButterKnife.bind(this, view);
        setTitleText(getString(R.string.tab_three));
        mWalletManager.initMine(R.mipmap.bg_wallet, getString(R.string.wallet_manager), "", true).setOnRootClickListener(this, 1);
        mBackupMnemonic.initMine(R.mipmap.bg_mnemonic, getString(R.string.backup_mnemonics), "", true).setOnRootClickListener(this, 2);
        mChangePassword.initMine(R.mipmap.bg_key, getString(R.string.change_password), "", true).setOnRootClickListener(this, 3);
        mNetworkSettings.initMine(R.mipmap.bg_net, getString(R.string.network_settings), "", true).setOnRootClickListener(this, 4);
        mLanguageSettings.initMine(R.mipmap.bg_language, getString(R.string.language_settings), "", true).setOnRootClickListener(this, 5);
        mShare.initMine(R.mipmap.bg_share, getString(R.string.share), "", true).setOnRootClickListener(this, 6);
        mNotice.initMine(R.mipmap.bg_notice, getString(R.string.notice), "", true).setOnRootClickListener(this, 7);
        mAbout.initMine(R.mipmap.bg_about, getString(R.string.about), "", true).setOnRootClickListener(this, 9).showDivider(true, false);
        try {
            mCleanCache.initMine(R.mipmap.bg_cache, getString(R.string.clean_cache), CacheUtil.getTotalCacheSize(getContext()), false).setOnRootClickListener(this, 8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRootClick(View view) {
        switch ((int) view.getTag()) {
            case 1:
                new XPopup.Builder(getContext())
                        .isViewMode(true)
                        .asCustom(new PagerBottomPopup(getContext(),getActivity()))
                        .show();
                break;
            case 2:
                new XPopup.Builder(getContext())
                        .isViewMode(true)
                        .autoOpenSoftInput(true)
                        .moveUpToKeyboard(false)
                        .enableDrag(true)
                        .asCustom(new BackupMneBottomPopup(getContext()))
                        .show();
                break;
            case 3:
                ModifyPasswordActivity.startActivity(getActivity());
                break;
            case 4:
                NetworkSettingsActivity.startActivity(getActivity());
                break;
            case 5:
                LanguageSettingActivity.startActivity(getActivity());
                break;
            case 6:
                QRCodeShareActivity.startActivity(getActivity());
                break;
            case 7:
                showToast(getString(R.string.no_message));
                break;
            case 8:
                CacheUtil.clearAllCache(getContext());
                showToast(getString(R.string.cache_cleared));
                break;
            case 9:
                AboutActivity.startActivity(getActivity());
                break;
            default:
                break;
        }
    }
}
