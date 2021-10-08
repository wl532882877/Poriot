package com.pay.poriot.util.http;

public class StatusCode {
    /**
     * 账号或密码错误
     */
    public static final int ACCOUNT_OR_PASSWORD = 100002;
    /**
     * 验证码不正确
     */
    public static final int CODE_ERROE = 100004;
    /**
     * 验证码已失效
     */
    public static final int CODE_INVALID = 100005;
    /**
     * 账号已存在
     */
    public static final int CODE_ACCOUNT_ALREADY_EXISTS = 100007;
    /**
     * 账户不存在
     */
    public static final int ACCOUNT_NOT_EXISTS = 100009;
    /**
     * 账户已被限制登录
     */
    public static final int ACCOUNT_LOGIN_RESTRICTED = 1000010;
    /**
     * 助记词或密码错误
     */
    public static final int MNEMONIC_OR_PASSWORD_ERROR = 1000012;
    /**
     * 助记词已存在
     */
    public static final int MNEMONIC_ALREADY_EXISTS = 1000013;

    /**
     * 手机号已注册
     */
    public static final int CODE_MOBILE_REGISTERED = 1000014;
}
