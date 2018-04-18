package com.allen.questionnaire.service.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.allen.questionnaire.service.cache.CacheDbManager;
import com.allen.questionnaire.service.cache.db.bean.CacheEntity;
import com.allen.questionnaire.service.datatrasfer.AccessTokenManager;
import com.allen.questionnaire.service.datatrasfer.IDataCallBack;
import com.allen.questionnaire.service.datatrasfer.WinnerResponse;
import com.allen.questionnaire.service.exception.ERROR_MESSAGE;
import com.allen.questionnaire.service.exception.WinnerException;
import com.allen.questionnaire.service.httputil.BaseBuilder;
import com.allen.questionnaire.service.httputil.BaseCall;
import com.allen.questionnaire.service.httputil.Config;
import com.allen.questionnaire.service.httputil.ExecutorDelivery;
import com.allen.questionnaire.service.httputil.IHttpCallBack;
import com.allen.questionnaire.service.model.RespCategoryList;
import com.allen.questionnaire.service.model.RespQueDetail;
import com.allen.questionnaire.service.model.RespQuestionnaireList;
import com.allen.questionnaire.service.model.RespStudent;
import com.allen.questionnaire.service.model.Test;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Allen on 2018/1/30.
 * WinnerZhike的网络请求
 */

public class CommonRequest {
    private static CacheDbManager dbManager;
    private Context mContext;
    private static String mUrl;
    private Config mConfig;
    private static ExecutorDelivery delivery;
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    static {
        delivery = new ExecutorDelivery(mHandler);
    }

    private static CommonRequest mCommonRequest;

    public static CommonRequest getInstance() {
        if (null == mCommonRequest) {
            synchronized (CommonRequest.class) {
                if (null == mCommonRequest) {
                    mCommonRequest = new CommonRequest();
                }
            }
        }
        return mCommonRequest;
    }

    private CommonRequest() {
    }

    /**
     * 初始化网络框架的接口
     *
     * @param context 上下文
     * @param url     网络接口的URL
     */
    public void init(Context context, String url) {
        this.mUrl = url;
        AccessTokenManager.init(context, url);
        this.mContext = context.getApplicationContext();
        dbManager = CacheDbManager.getInstance(mContext);
    }

    private Context getApplication() throws WinnerException {
        if (this.mContext == null) {
            throw WinnerException.getExceptionByErrorMessage(ERROR_MESSAGE.COMMON_REQUEST_NOT_INIT);
        } else {
            return this.mContext.getApplicationContext();
        }
    }

    public void setHttpConfig(Config config) {
        this.mConfig = config;
        BaseCall.setHttpConfig(this.mConfig);
    }

    private interface IRequestCallBack<T> {
        T success(String responseStr) throws Exception;
    }

    public static Map<String, String> commonParams(Map<String, String> specificParams) throws WinnerException {
        Map<String, String> params = new HashMap<>();
        if (specificParams != null) {
            params.putAll(specificParams);
        }
        return params;
    }

    private static <T extends WinnerResponse> void baseGetRequest(String url, Map<String, String> specificParams, final IDataCallBack<T> callback, final IRequestCallBack<T> successCallBack) {
        final Request request;
        try {
            request = BaseBuilder.urlGet(url, commonParams(specificParams)).build();
        } catch (WinnerException exception) {
            callback.onError(exception.getErrorCode(), exception.getMessage());
            return;
        }

        BaseCall.doAsync(request, new IHttpCallBack() {
            public void onResponse(Response response) {
                String responseStr;
                try {
                    responseStr = response.body().string();
                    if (successCallBack != null) {
                        CommonRequest.delivery.postSuccess(callback, successCallBack.success(responseStr));
                    } else {
                        CommonRequest.delivery.postSuccess(callback, null);
                    }
                } catch (Exception exception) {
                    if (!TextUtils.isEmpty(exception.getMessage())) {
                        CommonRequest.delivery.postError(1007, exception.getMessage(), callback);
                    } else {
                        CommonRequest.delivery.postError(1007, "parse response json data error", callback);
                    }
                }
            }

            public void onFailure(int errorCode, String errorMessage) {
                CommonRequest.delivery.postError(errorCode, errorMessage, callback);
            }
        });
    }


    private static <T extends WinnerResponse> void basePostRequest(final Context context, String url, final long cacheTime, final Object tag, final Map<String, String> specificParams, final IDataCallBack<T> dataCallback, final IRequestCallBack<T> successCallBack) {
        //取缓存
        if (cacheTime > 0) {
            CacheEntity cache = getCache(cacheTime, specificParams);
            if (null != cache && null != cache.getResult() && !TextUtils.isEmpty(cache.getResult())) {
                try {
                    CommonRequest.delivery.postSuccess(dataCallback, successCallBack.success(cache.getResult()));
                } catch (Exception exception) {
                    if (!TextUtils.isEmpty(exception.getMessage())) {
                        CommonRequest.delivery.postError(1007, exception.getMessage(), dataCallback);
                    } else {
                        CommonRequest.delivery.postError(1007, "parse response json data error", dataCallback);
                    }
                }
                return;
            }
        }
        final Request request;
        try {
            Request.Builder builder = BaseBuilder.urlPostJSON(mUrl + url, commonParams(specificParams));
            builder.addHeader("Content-Type", "application/json");
            request = (null != tag) ? builder.tag(tag).build() : builder.build();
        } catch (WinnerException exception) {
            dataCallback.onError(exception.getErrorCode(), exception.getMessage());
            return;
        }
        IHttpCallBack httpCallBack = new IHttpCallBack() {
            public void onResponse(Response response) {
                String responseStr;
                try {
                    responseStr = response.body().string();
                    CommonRequest.delivery.postSuccess(dataCallback, successCallBack.success(responseStr));
                    if (cacheTime > 0) {
                        dbManager.addCache(mUrl, specificParams, responseStr);
                    }
                } catch (Exception exception) {
                    if (!TextUtils.isEmpty(exception.getMessage())) {
                        CommonRequest.delivery.postError(1007, exception.getMessage(), dataCallback);
                    } else {
                        CommonRequest.delivery.postError(1007, "parse response json data error", dataCallback);
                    }
                }
            }

            public void onFailure(int errorCode, String errorMessage) {
                CommonRequest.delivery.postError(errorCode, errorMessage, dataCallback);
            }
        };
        BaseCall.doAsync(request, httpCallBack);
    }

    /**
     * 先获取缓存
     *
     * @param cacheTime
     * @param specificParams
     */
    private static CacheEntity getCache(final long cacheTime, final Map<String, String> specificParams) {
        CacheEntity cache = dbManager.getCache(mUrl, specificParams, cacheTime);
        return cache;
    }


    /**
     * 登录
     *
     * @param context        上下文
     * @param url            url地址
     * @param tag            标识
     * @param specificParams 参数
     * @param callback       回调
     */
    public static void postLogin(Context context, String url, Object tag, Map<String, String> specificParams, final IDataCallBack<RespStudent> callback) {
        basePostRequest(context, url, 0, tag, specificParams, callback, new IRequestCallBack<RespStudent>() {
            @Override
            public RespStudent success(String responseStr) throws Exception {
                Type listType = new TypeToken<RespStudent>() {
                }.getType();
                Gson gson = new Gson();
                RespStudent respStudent = gson.fromJson(responseStr, listType);
                return respStudent;
            }
        });
    }

    /**
     * 获取问卷类别集合
     *
     * @param context        上下文
     * @param url            地址
     * @param tag            标识
     * @param specificParams 参数
     * @param callback       回调
     */
    public static void postCategoryList(Context context, String url, Object tag, Map<String, String> specificParams, final IDataCallBack<RespCategoryList> callback) {
        basePostRequest(context, url, 0, tag, specificParams, callback, new IRequestCallBack<RespCategoryList>() {
            @Override
            public RespCategoryList success(String responseStr) throws Exception {
                Type listType = new TypeToken<RespCategoryList>() {
                }.getType();
                Gson gson = new Gson();
                RespCategoryList categoryList = gson.fromJson(responseStr, listType);
                return categoryList;
            }
        });
    }

    /**
     * 获取问卷集合
     *
     * @param context        上下文
     * @param url            url地址
     * @param tag            标识
     * @param specificParams 参数
     * @param callback       回调
     */
    public static void postQuestionnaireList(Context context, String url, Object tag, Map<String, String> specificParams, final IDataCallBack<RespQuestionnaireList> callback) {
        basePostRequest(context, url, 0, tag, specificParams, callback, new IRequestCallBack<RespQuestionnaireList>() {
            @Override
            public RespQuestionnaireList success(String responseStr) throws Exception {
                Type listType = new TypeToken<RespQuestionnaireList>() {
                }.getType();
                Gson gson = new Gson();
                RespQuestionnaireList questionnaireList = gson.fromJson(responseStr, listType);
                return questionnaireList;
            }
        });
    }

    /**
     * 获取某一问卷的问题和选项
     *
     * @param context 上下文
     * @param url url地址
     * @param tag 标识
     * @param specificParams 参数集合
     * @param callback 回调
     */
    public static void getQuestionAddOptions(Context context, String url, Object tag, Map<String, String> specificParams, final IDataCallBack<Test> callback) {
        basePostRequest(context, url, 0, tag, specificParams, callback, new IRequestCallBack<Test>() {
            @Override
            public Test success(String responseStr) throws Exception {
                Type listType = new TypeToken<Test>() {
                }.getType();
                Gson gson = new Gson();
                Test questionnaireList = gson.fromJson(responseStr, listType);

                return questionnaireList;
            }
        });
    }

}
