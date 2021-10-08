package com.pay.poriot.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseFragment;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.dao.ETHWallet;
import com.pay.poriot.interact.CreateWalletInteract;
import com.pay.poriot.ui.widget.CleanableEditText;
import com.pay.poriot.util.ETHWalletUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 导入私钥
 */
public class ImportPrivateKeyFragment extends BaseFragment implements View.OnClickListener{
    @BindView(R.id.edt_content)
    CleanableEditText mEdtPrivateKey;
    @BindView(R.id.edt_password)
    CleanableEditText mEdtWalletPassword;
    @BindView(R.id.edt_sure_password)
    CleanableEditText mEdtWalletSurePassword;
    @BindView(R.id.bt_determine)
    Button mBtDetermine;
    CreateWalletInteract createWalletInteract;
    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initVariables();
        View mRootView = inflater.inflate(R.layout.fragment_wallet_import, container, false);
        initViews(mRootView);
        return mRootView;
    }

    private void initVariables() {
        createWalletInteract = new CreateWalletInteract();
    }

    private void initViews(View view) {
        super.initTitle(view);
        ButterKnife.bind(this, view);
        mEdtPrivateKey.setHint(getString(R.string.enter_private_key));
        mBtDetermine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_determine:
                String privateKey = mEdtPrivateKey.getText().toString().trim();
                String walletPwd = mEdtWalletPassword.getText().toString().trim();
                String confirmPwd = mEdtWalletSurePassword.getText().toString().trim();
                boolean verifyWalletInfo = verifyInfo(privateKey, walletPwd, confirmPwd);
                if (verifyWalletInfo) {
                    showDialog(getString(R.string.import_wallet_tip));
                    createWalletInteract.loadWalletByPrivateKey(privateKey, walletPwd).subscribe(this::loadSuccess, this::onError);
                }
                break;
        }
    }

    private boolean verifyInfo(String privateKey, String walletPwd, String confirmPwd) {
        if (TextUtils.isEmpty(privateKey)) {
            showToast(getString(R.string.invalid_private_key));
            return false;
        } else if (TextUtils.isEmpty(walletPwd)) {
            showToast(getString(R.string.edit_input_wallet_password));
            return false;
        } else if (TextUtils.isEmpty(confirmPwd) || !TextUtils.equals(confirmPwd, walletPwd)) {
            showToast(getString(R.string.edit_input_wallet_password2));
            return false;
        }
        return true;
    }

    public void loadSuccess(ETHWallet wallet) {
        showToast(getString(R.string.success_imported_wallet));
        dismissDialog();
        getActivity().finish();

    }

    private void onError(Throwable error) {
        showToast(getString(R.string.invalid_private_key));
        dismissDialog();
    }
}
