package com.allen.questionnaire.service.httputil;

import android.text.TextUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Renjy on 2018/1/30.
 * 封装同步和异步的请求
 */

public class BaseCall {
    private static OkHttpClient mOkHttpClient = new OkHttpClient();
    public static final String NET_ERR_CONTENT = "网络请求失败";

    public BaseCall() {
    }

    public static void setHttpConfig(Config config) {
        OkHttpClient.Builder builder = mOkHttpClient.newBuilder();
        builder.connectTimeout(config.connectionTimeOut, TimeUnit.MILLISECONDS);
        builder.readTimeout(config.readTimeOut, TimeUnit.MILLISECONDS);
        mOkHttpClient = builder.build();
    }

    public static synchronized OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static synchronized void setOkHttpClient(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
    }

    /**
     * 添加应用拦截器
     *
     * @param interceptor
     */
    public static synchronized void addInterceptor(Interceptor interceptor) {
        OkHttpClient.Builder builder = getOkHttpClient().newBuilder();
        builder.addInterceptor(interceptor);
        setOkHttpClient(builder.build());
    }

    /**
     * 移除应用拦截器
     *
     * @param interceptor
     */
    public static synchronized void removeInterceptor(Interceptor interceptor) {
        OkHttpClient.Builder builder = getOkHttpClient().newBuilder();
        builder.interceptors().remove(interceptor);
        setOkHttpClient(builder.build());
    }

    /**
     * 发起一个异步请求
     *
     * @param request  请求体
     * @param callback 请求的回调
     */
    public static void doAsync(Request request, final IHttpCallBack callback) {
        try {
            getOkHttpClient().newCall(request).enqueue(new Callback() {
                public void onResponse(Call call, Response response) throws IOException {
                    if (callback == null) {
                        response.body().close();
                    } else {
                        if (response.isSuccessful()) {
                            callback.onResponse(response);
                        }
                    }
                }

                public void onFailure(Call call, IOException e) {
                    String errorMsg = NET_ERR_CONTENT;
                    if (callback != null) {
                        if (!TextUtils.isEmpty(e.getMessage()))
                            errorMsg = e.getMessage();
                        callback.onFailure(604, errorMsg);
                    }
                }
            });
        } catch (Exception var3) {
            if (callback != null) {
                callback.onFailure(604, NET_ERR_CONTENT);
            }
        }

    }

    /**
     * 根据请求的tag来取消已发起的的请求
     *
     * @param tag
     */
    public static synchronized void cancleTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            OkHttpClient okHttpient = getOkHttpClient();
            if (okHttpient != null) {
                Dispatcher dispatcher = okHttpient.dispatcher();
                if (dispatcher != null) {
                    Iterator var1 = dispatcher.runningCalls().iterator();
                    Call next;
                    Request request;
                    while (var1.hasNext()) {
                        next = (Call) var1.next();
                        request = next.request();
                        if (request != null && tag.equals(request.tag())) {
                            next.cancel();
                            return;
                        }
                    }
                    var1 = dispatcher.queuedCalls().iterator();
                    while (var1.hasNext()) {
                        next = (Call) var1.next();
                        request = next.request();
                        if (request != null && tag.equals(request.tag())) {
                            next.cancel();
                            return;
                        }
                    }
                }
            }
        }
    }
}
