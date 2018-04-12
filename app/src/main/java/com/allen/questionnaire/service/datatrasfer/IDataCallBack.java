package com.allen.questionnaire.service.datatrasfer;


/**
 * 请求结果的回调
 *
 * 实体类
 */

public interface IDataCallBack<T extends WinnerResponse> {
    void onSuccess(T result);

    void onError(int errorCode, String errorMessage);
}
