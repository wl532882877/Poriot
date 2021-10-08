package com.pay.poriot.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.pay.poriot.base.BaseDialog;
import java.lang.reflect.Field;

/**
 * 统一的dialog管理类
 */
public class DialogManager extends DialogFragment {
    public static final String TAG = "DialogFragment";
    public static final String S_DIALOG_TYPE = "dialog_type";

    /**
     * 匹配队列弹出框
     */
    public final static int MATCHING_QUEUE_DIALOG = 1;


    private BaseDialog mBaseDialog;
    private int mDialogType;
    private View.OnClickListener mOnClickListener;

    private DialogManager setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
        return this;
    }

    public static DialogManager newInstance(int type, Bundle bundle, View.OnClickListener onClickListener) {
        return newInstance(type, bundle).setOnClickListener(onClickListener);
    }

    public static DialogManager newInstance(int type, @NonNull Bundle bundle) {
        DialogManager dialogManager = new DialogManager();
        bundle.putInt(S_DIALOG_TYPE, type);
        dialogManager.setArguments(bundle);
        return dialogManager;
    }

    public DialogManager() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogType = getArguments().getInt(S_DIALOG_TYPE, 0);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        switch (mDialogType) {
            case MATCHING_QUEUE_DIALOG:

                break;
            default:
                break;
        }
        return mBaseDialog == null ? super.onCreateDialog(savedInstanceState) :
                mBaseDialog.setOnClickListener(mOnClickListener);
    }


    public void show(FragmentManager fragmentManager) {
        if (getDialog() != null) {
            getDialog().setOnDismissListener(dialog -> dismiss());
        }
        Fragment dialogFragment = fragmentManager.findFragmentByTag(S_DIALOG_TYPE + mDialogType);
        if (null != dialogFragment) {
            fragmentManager.beginTransaction().remove(dialogFragment).commit();
        }
        Class clazz = this.getClass().getSuperclass();
        try {
            Field mDismissed = clazz.getDeclaredField("mDismissed");
            mDismissed.setAccessible(true);
            mDismissed.set(this, false);

            Field mShownByMe = clazz.getDeclaredField("mShownByMe");
            mShownByMe.setAccessible(true);
            mShownByMe.set(this, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(this, S_DIALOG_TYPE + mDialogType);
        ft.commitAllowingStateLoss();
    }


    @Override
    public void dismiss() {
        super.dismiss();
        mOnClickListener = null;
    }
}
