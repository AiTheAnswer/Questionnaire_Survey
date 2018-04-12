package com.allen.questionnaire.service.httputil;

import okhttp3.Response;

/**
 * Created by Renjy on 2018/1/30.
 * 请求接口的回调
 */

public interface IHttpCallBack {
    void onFailure(int errorCode, String message);

    void onResponse(Response response);
}
