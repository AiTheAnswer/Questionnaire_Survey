package com.allen.questionnaire.service.exception;

/**
 * Created by Allen on 2018/4/12.
 */

public enum ERROR_MESSAGE {
    URL_EMPTY(1001,"Url 为空"),
    URL_PARSE_ERROR(1002,"Url 格式化发生错误"),
    DATA_PARSE_ERROR(1003,"数据格式化发生错误"),
    TOKEN_MANAGER_NOT_INIT(1004,"没有调用AccessTokenManager的init方法"),
    COMMON_REQUEST_NOT_INIT(1004,"没有调用CommonRequest的init方法"),
    ;
    private int errorCode;
    private String errorMessage;

    ERROR_MESSAGE(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
