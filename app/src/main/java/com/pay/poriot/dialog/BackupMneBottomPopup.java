package com.pay.poriot.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.pay.poriot.R;
import com.pay.poriot.ui.activity.PrivateKeyInfoActivity;
import com.pay.poriot.ui.widget.CleanableEditText;
import com.pay.poriot.util.Md5Utils;
import com.pay.poriot.util.ToastUtil;
import com.pay.poriot.util.WalletDaoUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BackupMneBottomPopup extends BottomPopupView implements View.OnClickListener {
    @BindView(R.id.iv_back)
    LinearLayout mIvBack;
    @BindView(R.id.edt_password)
    CleanableEditText mEdtWalletPassword;
    @BindView(R.id.bt_confirm)
    Button mBtConfirm;
    private CustomDialog dialog;

    public BackupMneBottomPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.backup_mnemonics_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
    }

    private void initView() {
        ButterKnife.bind(this, this);
        mIvBack.setOnClickListener(this);
        mBtConfirm.setOnClickListener(this);
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


    private boolean verifyInfo(String walletPwd) {
        if (TextUtils.isEmpty(walletPwd)) {
            ToastUtil.show(getContext(), R.string.edit_input_wallet_password);
            return false;
        }
        return true;
    }

    public void showDialog(String progressTip) {
        getDialog().show();
        if (progressTip != null) {
            getDialog().setTvProgress(progressTip);
        }
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(getContext());
            dialog.setCancelable(true);
        }
        return dialog;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                dismiss();
                break;
            case R.id.bt_confirm:
                String walletPwd = mEdtWalletPassword.getText().toString().trim();
                boolean verifyWalletInfo = verifyInfo(walletPwd);
                if (verifyWalletInfo) {
                    showDialog("");
                    if (TextUtils.equals(Md5Utils.md5(walletPwd), WalletDaoUtils.getCurrent().getPassword())) {
                        dismissDialog();
                        dismiss();
                        PrivateKeyInfoActivity.startActivity(getContext(),walletPwd);
                    } else {
                        ToastUtil.show(getContext(), R.string.wallet_password_error);
                        dismissDialog();
                    }
                }
                break;
        }
    }
}
