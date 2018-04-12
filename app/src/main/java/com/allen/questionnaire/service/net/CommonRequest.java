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
import com.allen.questionnaire.service.exception.WinnerException;
import com.allen.questionnaire.service.httputil.BaseBuilder;
import com.allen.questionnaire.service.httputil.BaseCall;
import com.allen.questionnaire.service.httputil.Config;
import com.allen.questionnaire.service.httputil.ExecutorDelivery;
import com.allen.questionnaire.service.httputil.IHttpCallBack;
import com.allen.questionnaire.service.model.RespMenuList;
import com.allen.questionnaire.service.model.RespToken;
import com.allen.questionnaire.service.model.StoreList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
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
            throw WinnerException.getExceptionByCode(1004);
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
        Map<String, String> params = new HashMap<>();
        params.put("fcuid", "dd65768b-935f-464c-b4f1-d1421e408c0d");
        params.put("fcimei", "99000645401857");
        params.put("from", "android");
        specificParams.putAll(params);
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

    private static <T extends WinnerResponse> void verifyToken(final Context context, final long cacheTime, final Object tag, final Map<String, String> specificParams, final IDataCallBack<T> dataCallback, final IRequestCallBack<T> successCallBack) {
        //验证Token是否过期
        if (!AccessTokenManager.verifyToken()) {
            AccessTokenManager.getToken(new IDataCallBack<RespToken>() {
                @Override
                public void onSuccess(RespToken result) {
                    basePostRequest(context, cacheTime, tag, specificParams, dataCallback, successCallBack);
                }

                @Override
                public void onError(int errorCode, String errorMessage) {
                    CommonRequest.delivery.postError(errorCode, errorMessage, dataCallback);
                }
            });
        }else{
            basePostRequest(context, cacheTime, tag, specificParams, dataCallback, successCallBack);
        }
    }

    private static <T extends WinnerResponse> void basePostRequest(final Context context, final long cacheTime, final Object tag, final Map<String, String> specificParams, final IDataCallBack<T> dataCallback, final IRequestCallBack<T> successCallBack) {
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
            Request.Builder builder = BaseBuilder.urlPost(mUrl, commonParams(specificParams));
            //在Header中添加Token
            builder.addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("WinnerAuth", AccessTokenManager.getAccessToken());
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
     * 上传文件
     *
     * @param actionUrl 上传文件的url
     * @param filePath  上传文件的路径
     * @param callback  上传结果的回调
     */
    public static void uploadFile(String actionUrl, String filePath, IDataCallBack<WinnerResponse> callback) throws Exception {
        //补全请求地址
        String requestUrl = mUrl + actionUrl;
        //创建File
        File file = new File(filePath);
        if (file.exists()) {
            throw new Exception("文件不存在");
        }


    }

    /**
     * get 方式获取场所列表
     *
     * @param specificParams
     * @param callback
     */
    public static void getUserSiteListByTypeForTodayInspected(Map<String, String> specificParams, IDataCallBack<StoreList> callback) {

        baseGetRequest(mUrl, specificParams, callback, new CommonRequest.IRequestCallBack<StoreList>() {
            public StoreList success(String responseStr) throws Exception {
                Type listType = new TypeToken<StoreList>() {
                }.getType();
                Gson gson = new Gson();
                StoreList storeList;
                storeList = gson.fromJson(responseStr, listType);
                return storeList;
            }
        });
    }

    /**
     * post 方式获取场所列表
     *
     * @param specificParams
     * @param callback
     */
    public static void postUserSiteListByTypeForTodayInspected(Context context, Object tag, Map<String, String> specificParams, final IDataCallBack<StoreList> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("fcuid", "dd65768b-935f-464c-b4f1-d1421e408c0d");
        params.put("fcimei", "99000645401857");
        params.put("from", "android");
        params.put("action", "getUserSiteListByTypeForTodayInspected");
        specificParams.putAll(params);
        verifyToken(context, 0, tag, specificParams, callback, new IRequestCallBack<StoreList>() {
            @Override
            public StoreList success(String responseStr) throws Exception {
                Type listType = new TypeToken<StoreList>() {
                }.getType();
                Gson gson = new Gson();
                StoreList storeList = gson.fromJson(responseStr, listType);
                return storeList;
            }
        });
    }

    /**
     * 获取菜单列表
     *
     * @param context        上下文
     * @param tag            标识，用于cancel
     * @param specificParams 参数集合
     * @param callback       结果的回调
     */
    public static void postMenuList(Context context, Object tag, Map<String, String> specificParams, final IDataCallBack<RespMenuList> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("fcuid", "dd65768b-935f-464c-b4f1-d1421e408c0d");
        params.put("fcimei", "A0000059FF25FC");
        params.put("from", "android");
        params.put("action", "getMenuList");
        specificParams.putAll(params);
        verifyToken(context, 0, tag, specificParams, callback, new IRequestCallBack<RespMenuList>() {
            @Override
            public RespMenuList success(String responseStr) throws Exception {
                Type listType = new TypeToken<RespMenuList>() {
                }.getType();
                Gson gson = new Gson();
                RespMenuList menuList = gson.fromJson(responseStr, listType);
                return menuList;
            }
        });
    }


}
