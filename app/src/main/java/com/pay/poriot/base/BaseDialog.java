package com.pay.poriot.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.pay.poriot.listener.DialogDismissListener;

public abstract class BaseDialog extends Dialog implements View.OnClickListener, DialogInterface.OnCancelListener{
    protected View.OnClickListener mOnClickListener;
    protected DialogDismissListener mDismissListener;
    protected Window mWindow;

    public BaseDialog setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
        return this;
    }

    public BaseDialog setDialogDismissListener(DialogDismissListener dismissListener) {
        this.mDismissListener = dismissListener;
        return this;
    }

    public BaseDialog(@NonNull Context context) {
        super(context);
        if (context instanceof Activity) {
            setOwnerActivity((Activity) context);
        }
    }

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        if (context instanceof Activity) {
            setOwnerActivity((Activity) context);
        }
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        if (context instanceof Activity) {
            setOwnerActivity((Activity) context);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(toGenerateLayoutId());
        mWindow = getWindow();
        if (null != mWindow) {
            onConfigDialogParams();
        }
        onInflateFinished();
        setOnCancelListener(this);
    }

    protected abstract int toGenerateLayoutId();

    protected abstract void onConfigDialogParams();

    protected abstract void onInflateFinished();

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (null != mDismissListener) {
            mDismissListener.onDialogDismiss();
        }
        mOnClickListener = null;
        mDismissListener = null;
    }
}
