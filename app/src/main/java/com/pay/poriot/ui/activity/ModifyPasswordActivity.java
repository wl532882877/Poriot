package com.pay.poriot.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.dao.ETHWallet;
import com.pay.poriot.interact.ModifyWalletInteract;
import com.pay.poriot.ui.widget.CleanableEditText;
import com.pay.poriot.util.Md5Utils;
import com.pay.poriot.util.ViewUtil;
import com.pay.poriot.util.WalletDaoUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 修改钱包密码
 */
public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.edt_old_password)
    CleanableEditText mEtOldPassword;
    @BindView(R.id.edt_new_password)
    CleanableEditText mEtNewPassword;
    @BindView(R.id.edt_confirm_password)
    CleanableEditText mEtConfirmPassword;
    @BindView(R.id.bt_confirm)
    Button mBtConfirm;
    private ModifyWalletInteract modifyWalletInteract;
    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, ModifyPasswordActivity.class);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
        modifyWalletInteract = new ModifyWalletInteract();
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString(R.string.change_password));
        setTitleLeftIcon(R.drawable.btn_back_black, this);
        mEtOldPassword.addTextChangedListener(watcher);
        mEtNewPassword.addTextChangedListener(watcher);
        mEtConfirmPassword.addTextChangedListener(watcher);
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String oldPwd = mEtOldPassword.getText().toString().trim();
            String newPwd = mEtNewPassword.getText().toString().trim();
            String newPwdAgain = mEtConfirmPassword.getText().toString().trim();
            if (TextUtils.isEmpty(oldPwd) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(newPwdAgain)) {
                mBtConfirm.setEnabled(false);
            } else {
                mBtConfirm.setEnabled(true);
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
            case R.id.bt_confirm:
                String oldPwd = mEtOldPassword.getText().toString().trim();
                String newPwd = mEtNewPassword.getText().toString().trim();
                String newPwdAgain = mEtConfirmPassword.getText().toString().trim();
                if (verifyPassword(oldPwd, newPwd, newPwdAgain)) {
                    modifyWalletInteract.modifyWalletPwd(WalletDaoUtils.getCurrent().getId(), WalletDaoUtils.getCurrent().getName(), oldPwd, newPwd).subscribe(this::modifyPwdSuccess);
                }
                break;
            default:
                break;
        }
    }

    private boolean verifyPassword(String oldPwd, String newPwd, String newPwdAgain) {
        if (!TextUtils.equals(Md5Utils.md5(oldPwd), WalletDaoUtils.getCurrent().getPassword())) {
            showToast(getString(R.string.old_password_error));
            return false;
        } else if (!TextUtils.equals(newPwd, newPwdAgain)) {
            showToast(getString(R.string.edit_input_wallet_password2));
            return false;
        }
        return true;
    }

    public void modifyPwdSuccess(ETHWallet ethWallet) {
        showToast(getString(R.string.modify_password_success));
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        ViewUtil.left2RightOut(this);
    }
}
