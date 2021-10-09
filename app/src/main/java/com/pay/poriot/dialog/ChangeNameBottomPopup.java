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
import com.pay.poriot.interact.ModifyWalletInteract;
import com.pay.poriot.ui.widget.CleanableEditText;
import com.pay.poriot.util.ToastUtil;
import com.pay.poriot.util.WalletDaoUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeNameBottomPopup extends BottomPopupView implements View.OnClickListener {
    @BindView(R.id.iv_back)
    LinearLayout mIvBack;
    @BindView(R.id.edt_name)
    CleanableEditText mEdtWalletName;
    @BindView(R.id.bt_determine)
    Button mBtDetermine;

    private ModifyWalletInteract modifyWalletInteract;

    public ChangeNameBottomPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.change_name_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
    }

    private void initView() {
        ButterKnife.bind(this, this);
        modifyWalletInteract = new ModifyWalletInteract();
        mIvBack.setOnClickListener(this);
        mBtDetermine.setOnClickListener(this);
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

    public void modifySuccess(boolean s) {
        if(s){
            ToastUtil.show(getContext(), R.string.success_modify_wallet_name);
            dismiss();
        }else {
            ToastUtil.show(getContext(), R.string.failed_modify_wallet_name);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                dismiss();
                break;
            case R.id.bt_determine:
                String walletName= WalletDaoUtils.getCurrent().getName();
                String name = mEdtWalletName.getText().toString().trim();
                if (TextUtils.equals(walletName, name)) {
                    dismiss();
                }else {
                    modifyWalletInteract.modifyWalletName(WalletDaoUtils.getCurrent().getId(), name).subscribe(this::modifySuccess);
                }
                break;
        }
    }
}
