package com.pay.poriot.config;

public class ConfigServer {
    /**
     * 获取接口地址
     *
     * @return String API的基本地址
     */

    private static String API_DEBUG_URL = "http://192.168.0.108:8888";
    private static String API_RELASE_URL = "";

    public static String getApiUrl() {
        return API_DEBUG_URL;
    }
}
