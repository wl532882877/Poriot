package com.pay.poriot.util.rx;

import android.app.Activity;

import rx.Subscriber;

public class RxSubscriber<T> extends Subscriber<ResultBean<T>> {
    private Activity mActivity;

    public RxSubscriber(Activity activity) {
        super();
        mActivity = activity;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        onErrorAction(ResultBean.RESULT_CODE_NET_ERROR, null);
        e.printStackTrace();
    }

    @Override
    public void onNext(ResultBean<T> t) {
        switch (t.getCode()) {
            case ResultBean.RESULT_CODE_SUCCESS:
                onNextAction(t.getData());
                break;
            default:
                onErrorAction(t.getCode(), t.getData());
                break;
        }
    }

    public void onNextAction(T t) {

    }

    public void onErrorAction(int code, T t) {
        onErrorAction(code);
    }

    public void onErrorAction(int code) {

    }
}
