package com.pay.poriot.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pay.poriot.R;
import com.pay.poriot.base.ActivityCollector;

public class ToastUtil {
    public static void show(Context context, CharSequence msg) {
        show(context, msg, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence msg, int duration) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (ActivityCollector.currentActivity() != null) {
            ImeUtil.hideSoftInput(ActivityCollector.currentActivity());
        }

        View toastView = View.inflate(context, R.layout.view_toast, null);
        TextView result = toastView.findViewById(R.id.tv_result);
        result.setText(msg);
        Toast toast = new Toast(context);

        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(toastView);
        toast.show();
    }

    public static void show(Context context, int resId) {
        show(context, context.getResources().getString(resId));
    }
}
