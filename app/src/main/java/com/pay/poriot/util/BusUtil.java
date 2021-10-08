package com.pay.poriot.util;

import com.pay.poriot.bean.EventBusModel;

import org.greenrobot.eventbus.EventBus;

public class BusUtil {
    public static void register(Object object) {
        if (!EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().register(object);
        }
    }

    public static void unregister(Object object) {
        if (EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().unregister(object);
        }
    }

    public static void post(EventBusModel model) {
        if (null == model) {
            return;
        }
        EventBus.getDefault().post(model);
    }
}
