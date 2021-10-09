package com.pay.poriot.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.interact.ModifyWalletInteract;
import com.pay.poriot.util.ViewUtil;
import com.pay.poriot.util.WalletDaoUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 私钥信息
 */
public class PrivateKeyInfoActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.click_show_key)
    TextView mTvShowKey;
    @BindView(R.id.click_show_mnemonic)
    TextView mTvShowMnemonic;
    @BindView(R.id.click_show_key_store)
    TextView mTvShowKeyStore;
    @BindView(R.id.tv_private_key)
    TextView mTvPrivateKey;
    @BindView(R.id.tv_mnemonic)
    TextView mTvMnemonic;
    @BindView(R.id.tv_key_store)
    TextView mTvKeyStore;
    private String privateKey;
    private String mnemonic;
    private String keyStore;
    private String password;
    private ModifyWalletInteract modifyWalletInteract;

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    public static void startActivity(Context activity, String password) {
        Intent intent = new Intent(activity, PrivateKeyInfoActivity.class);
        intent.putExtra("password", password);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_key_info);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
        mnemonic = WalletDaoUtils.getCurrent().getMnemonic().trim();
        password = getIntent().getStringExtra("password");
        modifyWalletInteract = new ModifyWalletInteract();
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString(R.string.private_key_info));
        setTitleRightText(getString(R.string.complete), this);
    }

    public void showDerivePrivateKey(String privatekey) {
        if (TextUtils.isEmpty(privatekey)) {
            mTvShowKey.setVisibility(View.VISIBLE);
            mTvPrivateKey.setVisibility(View.GONE);
        } else {
            mTvPrivateKey.setText(privatekey);
            mTvShowKey.setVisibility(View.GONE);
            mTvPrivateKey.setVisibility(View.VISIBLE);
            this.privateKey = privatekey;
        }
    }

    public void showDeriveKeystore(String keystore) {
        if (TextUtils.isEmpty(keystore)) {
            mTvShowKeyStore.setVisibility(View.VISIBLE);
            mTvKeyStore.setVisibility(View.GONE);
        } else {
            mTvKeyStore.setText(keystore);
            mTvShowKeyStore.setVisibility(View.GONE);
            mTvKeyStore.setVisibility(View.VISIBLE);
            this.keyStore = keystore;
        }
    }

    private void copyWallet(String copy) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm != null) {
            ClipData mClipData = ClipData.newPlainText("Label", copy);
            cm.setPrimaryClip(mClipData);
        }
        showToast(getString(R.string.qrcode_copy_success));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_right:
                finish();
                break;
            case R.id.click_show_key:
                modifyWalletInteract.deriveWalletPrivateKey(
                        WalletDaoUtils.getCurrent().getId(), password).subscribe(PrivateKeyInfoActivity.this::showDerivePrivateKey);
                break;
            case R.id.click_show_mnemonic:
                if (TextUtils.isEmpty(mnemonic)) {
                    mTvShowMnemonic.setVisibility(View.VISIBLE);
                    mTvMnemonic.setVisibility(View.GONE);
                } else {
                    mTvMnemonic.setText(mnemonic);
                    mTvShowMnemonic.setVisibility(View.GONE);
                    mTvMnemonic.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.click_show_key_store:
                modifyWalletInteract.deriveWalletKeystore(
                        WalletDaoUtils.getCurrent().getId(), password).subscribe(PrivateKeyInfoActivity.this::showDeriveKeystore);
                break;
            case R.id.bt_copy_private_key:
                if (TextUtils.isEmpty(privateKey)) {
                    return;
                }
                copyWallet(privateKey);
                break;
            case R.id.bt_copy_mnemonic:
                if (TextUtils.isEmpty(mnemonic)) {
                    return;
                }
                copyWallet(mnemonic);
                break;
            case R.id.bt_copy_key_store:
                if (TextUtils.isEmpty(keyStore)) {
                    return;
                }
                copyWallet(keyStore);
                break;
        }
    }
}
