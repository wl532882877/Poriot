package com.pay.poriot.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public class PhoneTextChange implements TextWatcher {
    private TextView mEditTexts;
    private IEditTextChangeListener mChangeListener;

    public PhoneTextChange(TextView editTexts) {
        mEditTexts = editTexts;
        mEditTexts.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mChangeListener.textChange(checkAllEdit());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 检查手机号标准
     */
    private boolean checkAllEdit() {
        String s = mEditTexts.getText().toString();

        if (StringUtil.isNullOrEmpty(s)) {
            return false;
        }

        if (s.length() == 11 && s.indexOf("1") == 0) {
            return true;
        }

        return false;
    }

    public void setChangeListener(IEditTextChangeListener changeListener) {
        mChangeListener = changeListener;
    }

    public interface IEditTextChangeListener {
        /**
         * 监听字符变化
         *
         * @param flag 是否可用
         */
        void textChange(boolean flag);
    }
}
