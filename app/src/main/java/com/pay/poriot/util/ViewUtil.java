package com.pay.poriot.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.pay.poriot.R;

public class ViewUtil {

    public static void startActivity(Context context, Intent intent) {
        if (context == null || intent == null) {
            return;
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * 渐显动画
     *
     * @param context 上下文对象
     */
    public static void fadeIn(Context context) {
        if (!(context instanceof Activity)) {
            return;
        }
        ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.no_anim);
    }

    /**
     * 渐隐动画
     *
     * @param context 上下文对象
     */
    public static void fadeOut(Context context) {
        if (!(context instanceof Activity)) {
            return;
        }
        ((Activity) context).overridePendingTransition(R.anim.no_anim, R.anim.fade_out);
    }

    /**
     * 无动画
     *
     * @param context 上下文对象
     */
    public static void noAnim(Context context) {
        if (!(context instanceof Activity)) {
            return;
        }
        ((Activity) context).overridePendingTransition(0, 0);
    }

    /**
     * 进入 从下往上动画
     *
     * @param context 上下文对象
     */
    public static void bottom2TopIn(Context context) {
        if (!(context instanceof Activity)) {
            return;
        }
        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.no_anim);
    }

    /**
     * 出去 从下往上动画
     *
     * @param context 上下文对象
     */
    public static void bottom2TopOut(Context context) {
        if (!(context instanceof Activity)) {
            return;
        }
        ((Activity) context).overridePendingTransition(R.anim.no_anim, R.anim.abc_slide_out_top);
    }

    /**
     * 出去 从上往下动画
     *
     * @param context 上下文对象
     */
    public static void top2BottomOut(Context context) {
        if (!(context instanceof Activity)) {
            return;
        }
        ((Activity) context).overridePendingTransition(R.anim.no_anim, R.anim.abc_slide_out_bottom);
    }

    /**
     * 出去 从上往下动画
     *
     * @param context 上下文对象
     */
    public static void top2BottomIn(Context context) {
        if (!(context instanceof Activity)) {
            return;
        }
        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_top, R.anim.no_anim);
    }

    public static void right2LeftIn(Context context) {
        if (!(context instanceof Activity)) {
            return;
        }
        ((Activity) context).overridePendingTransition(R.anim.slide_in_from_right, R.anim.no_anim);
    }

    public static void left2RightOut(Context context) {
        if (!(context instanceof Activity)) {
            return;
        }
        ((Activity) context).overridePendingTransition(R.anim.no_anim, R.anim.slide_out_to_right);
    }

    public static void statusBarTranslucent(Activity activity) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * 移动光标到最后
     *
     * @param editText 输入框
     */
    public static void moveCursorToEnd(EditText editText) {
        if (editText == null) {
            return;
        }
        Editable text = editText.getText();
        if (text != null) {
            Selection.setSelection(text, text.length());
        }
    }

    public static void bgAlpha(View view) {
        Animation scaleAnimation = new AlphaAnimation(0.1f, 1.0f);
        scaleAnimation.setDuration(2000);
        view.startAnimation(scaleAnimation);
    }

    public static void viewLeft(View view, Animation.AnimationListener listener) {
        Animation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        translateAnimation.setDuration(500);
        translateAnimation.setAnimationListener(listener);
        view.startAnimation(translateAnimation);
    }

    public static void setTranslucentStatusBar(Activity activity, Toolbar toolbar, int statusBarHeight) {
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        activity.getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (toolbar != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
            layoutParams.setMargins(
                    layoutParams.leftMargin,
                    layoutParams.topMargin + statusBarHeight,
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin);
        }

    }
}
