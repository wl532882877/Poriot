package com.pay.poriot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.dao.ETHWallet;
import com.pay.poriot.interact.CreateWalletInteract;
import com.pay.poriot.ui.widget.CleanableEditText;
import com.pay.poriot.util.ViewUtil;
import com.pay.poriot.util.WalletDaoUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建钱包
 */
public class CreateWalletActivity extends BaseActivity implements View.OnClickListener {

    private static final int CREATE_WALLET_RESULT = 2202;
    private static final int LOAD_WALLET_REQUEST = 1101;
    @BindView(R.id.edt_name)
    CleanableEditText mEtName;
    @BindView(R.id.edt_password)
    CleanableEditText mEtPassWord;
    @BindView(R.id.edt_sure_password)
    CleanableEditText mEtSurePassWord;
    @BindView(R.id.bt_create_wallet)
    Button mBtCreateWallet;
    private CreateWalletInteract createWalletInteract;


    public static void startActivity(Context activity) {
        Intent intent = new Intent(activity, CreateWalletActivity.class);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
        createWalletInteract = new CreateWalletInteract();
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString(R.string.create_wallet));
        setTitleLeftIcon(R.drawable.btn_back_black, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
            case R.id.bt_create_wallet:
                String walletName = mEtName.getText().toString().trim();
                String walletPwd = mEtPassWord.getText().toString().trim();
                String confirmPwd = mEtSurePassWord.getText().toString().trim();
                boolean verifyWalletInfo = verifyInfo(walletName, walletPwd, confirmPwd);
                if (verifyWalletInfo) {
                    showDialog(getString(R.string.creating_wallet_tip));
                    createWalletInteract.create(walletName, walletPwd, confirmPwd).subscribe(this::jumpToWalletBackUp, this::showError);
                }
            default:
                break;
        }
    }

    private boolean verifyInfo(String walletName, String walletPwd, String confirmPwd) {
        if (WalletDaoUtils.walletNameChecking(walletName)) {
            showToast(getString(R.string.wallet_name_repeat));
            return false;
        } else if (TextUtils.isEmpty(walletName)) {
            showToast(getString(R.string.edit_input_wallet_name));
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


    public void jumpToWalletBackUp(ETHWallet wallet) {
        showToast(getString(R.string.wallet_created_successfully));
        dismissDialog();
      /*  setResult(CREATE_WALLET_RESULT, new Intent());
        Intent intent = new Intent(this, WalletBackupActivity.class);
        intent.putExtra("walletId", wallet.getId());
        intent.putExtra("walletPwd", wallet.getPassword());
        intent.putExtra("walletAddress", wallet.getAddress());
        intent.putExtra("walletName", wallet.getName());
        intent.putExtra("walletMnemonic", wallet.getMnemonic());
        intent.putExtra("first_account", true);
        startActivity(intent);*/
        MainActivity.startActivity(CreateWalletActivity.this);
        finish();
    }


    public void showError(Throwable errorInfo) {
        dismissDialog();
        showToast(errorInfo.toString());
    }

    @Override
    public void finish() {
        super.finish();
        ViewUtil.left2RightOut(this);
    }
}
