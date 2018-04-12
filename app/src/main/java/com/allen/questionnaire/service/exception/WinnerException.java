package com.allen.questionnaire.service.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Allen on 2018/1/30.
 */

public class WinnerException extends Exception {
    //错误码
    private final int mErrorCode;
    //错误信息描述
    private final String mErrorMessage;
    //请求的Url为空
    public static final int REQUEST_URL_EMPTY = 1001;
    //没有进行初始化
    public static final int NOT_INIT = 1004;
    //获取清单文件中注册的appKey
    public static final int GET_APPKEY_ERROR = 1005;
    //请求的Url的解析错误
    public static final int REQUEST_URL_PARSE_ERROR = 1012;

    private static final Map<Integer, String> ERR_MESSAGE_MAP = new HashMap() {
        {
            this.put(Integer.valueOf(1001), "request url is empty");
            this.put(Integer.valueOf(1004), "you must call #Winner.init");
            this.put(Integer.valueOf(1009), "parse data error");
            this.put(Integer.valueOf(1012), "request url parse error");
            this.put(Integer.valueOf(1005), "get appkey error from AndroidManifest.xml metaData");
            this.put(Integer.valueOf(1006), "you must call #AccessTokenManager.init");
        }
    };

    public int getErrorCode() {
        return mErrorCode;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public WinnerException(int errorCode, String errorMessage) {
        this.mErrorCode = errorCode;
        this.mErrorMessage = errorMessage;
    }

    public static final WinnerException getExceptionByCode(int code) {
        return new WinnerException(code, ERR_MESSAGE_MAP.get(Integer.valueOf(code)));
    }
}
