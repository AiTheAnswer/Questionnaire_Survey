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


    public WinnerException(int errorCode, String errorMessage) {
        this.mErrorCode = errorCode;
        this.mErrorMessage = errorMessage;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public String getMessage() {
        return mErrorMessage;
    }

    public static final WinnerException getExceptionByErrorMessage(ERROR_MESSAGE errorMessage) {
        return new WinnerException(errorMessage.getErrorCode(), errorMessage.getErrorMessage());
    }
}
