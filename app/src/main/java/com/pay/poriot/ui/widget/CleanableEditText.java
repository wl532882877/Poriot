package com.pay.poriot.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CleanableEditText extends AppCompatEditText {
    private Drawable mRightDrawable;
    private boolean mDisableEmoji;
    private InputFilter mInputFilter = new InputFilter() {
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (mDisableEmoji) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
            }
            return null;
        }
    };

    public CleanableEditText(Context context) {
        super(context);
        init();
    }

    public CleanableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CleanableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Drawable[] drawables = getCompoundDrawables();
        mRightDrawable = drawables[2];
        setOnFocusChangeListener(new FocusChangeListenerImpl());
        addTextChangedListener(new TextWatcherImpl());
        setClearDrawableVisible(false);
    }

    public void disableEmoji(boolean disableEmoji, int maxLength) {
        mDisableEmoji = disableEmoji;
        InputFilter[] filterArray = new InputFilter[2];
        filterArray[0] = mInputFilter;
        filterArray[1] = new InputFilter.LengthFilter(maxLength > 0 ? maxLength : Integer.MAX_VALUE);
        setFilters(filterArray);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                boolean isClean = (event.getX() > (getWidth() - getTotalPaddingRight())) &&
                        (event.getX() < (getWidth() - getPaddingRight()));
                if (isClean) {
                    setText("");
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void setClearDrawableVisible(boolean isVisible) {
        if (null == mRightDrawable) {
            return;
        }
        Drawable rightDrawable;
        if (isVisible && isEnabled()) {
            rightDrawable = mRightDrawable;
            int height = getHeight();
            rightDrawable.setBounds(0, 0, height / 3, height / 3);
        } else {
            rightDrawable = null;
        }

        //???????????????????????????left, top, right, and bottom????????????
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                rightDrawable, getCompoundDrawables()[3]);
    }

    private class FocusChangeListenerImpl implements OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                boolean isVisible = getText().toString().length() >= 1;
                setClearDrawableVisible(isVisible);
            } else {
                setClearDrawableVisible(false);
            }
        }
    }

    private class TextWatcherImpl implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            boolean isVisible = getText().toString().length() >= 1;
            setClearDrawableVisible(isVisible);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

    }
}
