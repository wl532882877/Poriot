package com.pay.poriot.util;

import android.view.View;


import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RxUtil {
    private static long LAST_CLICK_TIME;
    private static final int SECOND = 1000;

    /**
     * 统一线程切换方法
     *
     * @param <T> Observable
     * @return Transformer对象
     */
    public static <T> Observable.Transformer<T, T> ioMain() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void unsubscribe(Subscriber subscriber) {
        if (null != subscriber && !subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
    }

    public static void click(final View v, int second, Action1<View> action) {
        long time = System.currentTimeMillis();
        long timeD = time - LAST_CLICK_TIME;
        if (0 < timeD && timeD < second * SECOND) {
            return;
        }
        LAST_CLICK_TIME = time;
        action.call(v);
    }
}
