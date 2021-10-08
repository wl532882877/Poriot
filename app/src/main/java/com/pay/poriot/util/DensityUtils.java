package com.pay.poriot.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DensityUtils {
    private static final Object mSync = new Object();
    private static final List<String> ACTION_LIST = new ArrayList<>();
    private static final int DEFAULT_COOLING_TIME = 500;
    public static int screenWidth = 1080;

    public static void measure(View view, int width, int height) {
        if (0 != width) {
            setViewWidth(view, getMeasureValue(width));
        }
        if (0 != height) {
            setViewHeight(view, getMeasureValue(height));
        }
    }

    public static void measure2(View view, int width, int height) {
        if (0 != width) {
            setViewWidth(view, width);
        }
        if (0 != height) {
            setViewHeight(view, height);
        }
    }

    public static void setViewHeight(View view, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (null == params) {
            return;
        }
        params.height = height;
        view.setLayoutParams(params);
    }

    public static void setViewWidth(View view, int width) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (null == params) {
            return;
        }
        params.width = width;
        view.setLayoutParams(params);
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        setSysMargins(v, getMeasureValue(l), getMeasureValue(t), getMeasureValue(r), getMeasureValue(b));
    }

    public static void setMargins2(View v, int l, int t, int r, int b) {
        setSysMargins(v, l, t, r, b);
    }

    public static void setSysMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.setLayoutParams(p);
        }
    }

    public static void setPadding(View v, int l, int t, int r, int b) {
        v.setPadding(getMeasureValue(l), getMeasureValue(t), getMeasureValue(r), getMeasureValue(b));
    }

    public static int[] getLocationOnScreen(View view) {
        int[] outLocation = new int[2];
        if (null == view) {
            return outLocation;
        }
        view.getLocationOnScreen(outLocation);
        return outLocation;
    }

    public static int[] getLocationInWindow(View view) {
        int[] outLocation = new int[2];
        if (null == view) {
            return outLocation;
        }
        view.getLocationInWindow(outLocation);
        return outLocation;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 尺寸dip
     * @return 像素值
     */
    public static int dip2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    public static int getScreenW() {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getMeasureValue(int px) {
        if (0 == px) {
            return 0;
        } else if (px > 0) {
            return Math.max(1, getScreenW() * px / screenWidth);
        } else {
            return Math.min(-1, getScreenW() * px / screenWidth);
        }
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public static int getScreenH() {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @return 状态栏的高度
     */
    public static int getStatusHeight() {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = Resources.getSystem().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static void limitReClick(final String id, ActionListener actionListener) {
        limitReClick(id, actionListener, DEFAULT_COOLING_TIME);
    }

    public static void limitReClick(final String id, ActionListener actionListener, int time) {
        if (StringUtil.isNullOrEmpty(id) || actionListener == null) {
            throw new NullPointerException();
        }

        synchronized (mSync) {
            if (ACTION_LIST.contains(id)) {
                return;
            } else {
                ACTION_LIST.add(id);

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        removeAction(id);
                    }
                }, time);
            }
        }
        actionListener.doAction();
    }

    public static void removeAction(String id) {
        synchronized (mSync) {
            ACTION_LIST.remove(id);
        }
    }

    public static Rect getViewAbsRect(View view, int parentX, int parentY) {
        int[] loc = new int[2];
        view.getLocationInWindow(loc);
        Rect rect = new Rect();
        rect.set(loc[0], loc[1], loc[0] + view.getMeasuredWidth(), loc[1] + view.getMeasuredHeight());
        rect.offset(-parentX, -parentY);
        return rect;
    }

    public interface ActionListener {
        /**
         * 限制点击冻结接触方法
         */
        void doAction();
    }


    /**
     * 判断导航栏是否显示
     *
     * @param context 上下文
     * @return 导航栏是否显示
     */
    public static boolean isShowDeviceHasNavigationBar(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (windowManager == null) {
                return false;
            }
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            return !menu && !back;
        }
    }

    /**
     * 判断导航栏高度
     *
     * @param context 上下文
     * @return 导航栏高度
     */
    public static int getNavigationHeight(@NonNull Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
