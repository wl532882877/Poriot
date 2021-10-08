package com.pay.poriot.util.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class HtcHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
