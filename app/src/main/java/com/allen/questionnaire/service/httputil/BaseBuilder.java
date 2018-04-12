package com.allen.questionnaire.service.httputil;

import android.text.TextUtils;


import com.allen.questionnaire.service.exception.ERROR_MESSAGE;
import com.allen.questionnaire.service.exception.WinnerException;
import com.allen.questionnaire.service.httputil.util.Util;

import java.util.Iterator;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Renjy on 2018/1/30.
 * 对Okhttp的Request.Builder进行封装
 * <p>
 * 参数拼接，添加header
 */

public class BaseBuilder {
    /**
     * 对get请求的地址进行拼接
     *
     * @param url            基本地址
     * @param specificParams Map参数
     * @return request的构造者builder
     * @throws WinnerException 拼接时发生异常
     */
    public static Request.Builder urlGet(String url, Map<String, String> specificParams) throws WinnerException {
        if (TextUtils.isEmpty(url)) {
            throw WinnerException.getExceptionByErrorMessage(ERROR_MESSAGE.URL_EMPTY);
        } else {
            if (specificParams != null && !specificParams.isEmpty()) {
                url = url + "?" + Util.ConvertMap2HttpParams(Util.encoderName(specificParams));
            }
            Request.Builder builder;
            try {
                builder = (new Request.Builder()).url(url);
            } catch (Exception var4) {
                var4.printStackTrace();
                throw new WinnerException(1012, var4.getMessage());
            }
            return builder;
        }
    }

    /**
     * 对post 请求地址和参数进行封装
     *
     * @param url            请求地址
     * @param specificParams Map参数
     * @return Request的构造者builder
     * @throws WinnerException 拼接时发生异常
     */
    public static Request.Builder urlPost(String url, Map<String, String> specificParams) throws WinnerException {
        if (TextUtils.isEmpty(url)) {
            throw WinnerException.getExceptionByErrorMessage(ERROR_MESSAGE.URL_EMPTY);
        } else {
            HttpUrl parsed = HttpUrl.parse(url);
            if (parsed == null) {
                throw WinnerException.getExceptionByErrorMessage(ERROR_MESSAGE.URL_PARSE_ERROR);
            }
            okhttp3.FormBody.Builder builder = new okhttp3.FormBody.Builder();
            if (specificParams != null && specificParams.size() > 0) {
                Iterator iterator = specificParams.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    if (entry.getValue() != null) {
                        builder.add((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                return (new Request.Builder()).url(url).post(builder.build());
            } else {
                return (new Request.Builder()).url(url).post(builder.build());
            }
        }
    }

}
