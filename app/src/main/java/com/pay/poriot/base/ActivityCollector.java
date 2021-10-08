package com.pay.poriot.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    private static List<Activity> sActivityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        if (activity != null) {
            sActivityList.add(activity);
        }
    }

    public static void removeActivity(Activity activity) {
        if (activity != null) {
            if (sActivityList.contains(activity)) {
                sActivityList.remove(activity);
            }
        }
    }

    public static Activity currentActivity() {
        if (sActivityList.isEmpty()) {
            return null;
        }
        return sActivityList.get(sActivityList.size() - 1);
    }


    public static void finishAll() {
        for (Activity activity : sActivityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static void finishCount(int count) {
        if (count <= 0 || count >= sActivityList.size()) {
            return;
        }
        for (int i = sActivityList.size() - 1; i >= sActivityList.size() - count; i--) {
            Activity activity = sActivityList.get(i);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
