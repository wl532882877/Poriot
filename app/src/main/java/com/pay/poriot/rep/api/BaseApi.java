package com.pay.poriot.rep.api;

import com.pay.poriot.BuildConfig;
import com.pay.poriot.base.BaseApplication;
import com.pay.poriot.config.ConfigServer;
import com.pay.poriot.rep.interceptor.HeaderInterceptor;
import com.pay.poriot.util.http.HtcHostnameVerifier;
import com.pay.poriot.util.EvtLog;
import com.pay.poriot.util.IoUtils;
import com.pay.poriot.util.StringUtil;
import org.json.JSONObject;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApi {
    private static final String TAG = "Retrofit";

    private static Retrofit mRetrofit;

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> EvtLog.d(TAG, message)).setLevel(HttpLoggingInterceptor.Level.BODY);

    public Retrofit getRetrofit() {
        return getRetrofit(ConfigServer.getApiUrl());
    }

    public static BaseApi getInstance() {
        return new BaseApi();
    }

    protected Retrofit getRetrofit(String url) {
        if (null == mRetrofit || !url.equals(mRetrofit.baseUrl().toString())) {
            OkHttpClient client = getOkHttpClient(url);

            mRetrofit = new Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(url)
                    .build();
        }
        return mRetrofit;
    }

    public OkHttpClient getOkHttpClient(String url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(BuildConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(loggingInterceptor)
                .connectTimeout(BuildConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(BuildConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        if (!StringUtil.isNullOrEmpty(url) && url.toLowerCase().contains("https:")) {
            try {
                // 忽略https证书校验
                builder.sslSocketFactory(TrustSSLContext.getSSLSocketFactory());
                builder.hostnameVerifier(new HtcHostnameVerifier());
            } catch (Exception e) {
                e.printStackTrace();
                EvtLog.e(TAG, e.getMessage());
            }
        }

        return builder.build();
    }


    private static class TrustSSLContext {

        private static SSLContext mSSLContext = null;

        static SSLSocketFactory getSSLSocketFactory() {

            try {
                mSSLContext = SSLContext.getInstance("TLS");
                mSSLContext.init(null, new TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
                }, new SecureRandom());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mSSLContext.getSocketFactory();
        }

    }

    protected SSLSocketFactory getSSLSocketFactory(int[] certificates) throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);

        for (int i = 0; i < certificates.length; i++) {
            InputStream certificate = BaseApplication.getInstance().getResources().openRawResource(certificates[i]);
            keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(certificate));
            IoUtils.closeStream(certificate);
        }
        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
        return sslContext.getSocketFactory();
    }


    public RequestBody getRequestBody(HashMap<String, Object> hashMap) {
        /*StringBuffer data = new StringBuffer();
        if (hashMap != null && hashMap.size() > 0) {
            Iterator iter = hashMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                data.append(key).append("=").append(val).append("&");
            }
        }
        String jso = data.substring(0, data.length() - 1);*/
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), new JSONObject(hashMap).toString());
        return requestBody;
    }
}
