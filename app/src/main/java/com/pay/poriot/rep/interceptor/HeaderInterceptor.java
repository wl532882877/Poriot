package com.pay.poriot.rep.interceptor;

import com.pay.poriot.dao.UserDao;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
              //  .addHeader("AC-X-TYPE", "1")
              //  .addHeader("AC-X-TOKEN", token)
             //     .addHeader("AC-X-VERSION", PackageUtil.getVersionName())
                  .addHeader("Content-Type", "application/json;charset=UTF-8")
           //     .removeHeader("User-Agent")
            //    .addHeader("User-Agent", "azoyaclub_android_" + PackageUtil.getVersionName())
                .build();
        return chain.proceed(newRequest);
    }
}
