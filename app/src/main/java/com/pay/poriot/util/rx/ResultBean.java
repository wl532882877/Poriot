package com.pay.poriot.util.rx;

public class ResultBean<T> {
    /**
     * 接口正常
     */
    public static final int RESULT_CODE_SUCCESS = 200;
    /**
     * 网络异常
     */
    public static final int RESULT_CODE_NET_ERROR = 111;
    /**
     * 用户被挤掉
     */
    public static final int RESULT_CODE_OTHER_LOGIN = 501;
    /**
     * 服务器错误
     */
    public static final int RESULT_CODE_SERVER_ERROR = 1001;
    /**
     * 服务器繁忙,请稍后再试
     */
    public static final int RESULT_CODE_SERVER_BUSY = 1002;
    /**
     * 数据库错误
     */
    public static final int RESULT_CODE_DATABASE_ERROR = 1003;
    /**
     * 参数绑定错误
     */
    public static final int RESULT_CODE_PARAMETER_BINDING_ERROR = 1004;
    /**
     * 参数格式错误
     */
    public static final int RESULT_CODE_PARAMETER_FORMAT_ERROR = 1005;
    /**
     * 保存失败
     */
    public static final int RESULT_CODE_SAVE_FAILED= 1006;
    /**
     * 更新失败
     */
    public static final int RESULT_CODE_UPDATE_FAILED= 1007;
    /**
     * 删除失败
     */
    public static final int RESULT_CODE_DELET_FAILED= 1008;
    /**
     * 加密失败
     */
    public static final int RESULT_CODE_ENCRYPTION_FAILED= 1009;
    /**
     * 认证失败
     */
    public static final int RESULT_CODE_AUTHENTICATION_FAILED= 1010;

    private int code;
    private String msg;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
