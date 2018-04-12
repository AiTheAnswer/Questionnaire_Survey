package com.allen.questionnaire.service.datatrasfer;

import android.content.Context;
import android.text.TextUtils;

import com.allen.questionnaire.service.exception.ERROR_MESSAGE;
import com.allen.questionnaire.service.exception.WinnerException;
import com.allen.questionnaire.service.httputil.BaseBuilder;
import com.allen.questionnaire.service.httputil.BaseCall;
import com.allen.questionnaire.service.httputil.IHttpCallBack;
import com.allen.questionnaire.service.httputil.util.Util;
import com.allen.questionnaire.service.model.RespToken;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Renjy on 2018/3/13.
 * token 验证
 */

public class AccessTokenManager {
    private static String mUrl;
    private static Context mContext;
    private static final String TOKEN_KEY = "app.token";
    private static final String TOKEN_EXP_KEY = "app.tokenInvalidTime";


    private AccessTokenManager() {

    }

    public static void init(Context context, String url) {
        mContext = context;
        mUrl = url;
    }

    /**
     * 验证Token是否过期
     *
     * @return true 未过期 false 已过期
     */
    public static boolean verifyToken() {
        long time = System.currentTimeMillis() / 1000;
        String invalidTime = getTokenExpire();
        if (Double.valueOf(invalidTime) - time > 60 * 5) {//未失效
            return true;
        } else if (Double.valueOf(invalidTime) - time <= 0) {//已失效
            return false;
        } else {//将要失效
            return false;
        }
    }

    /**
     * 获取当前Token的过期时间
     *
     * @return 当前Token的过期时间
     */
    private static String getTokenExpire() {
        if (null == mContext) {
            try {
                throw WinnerException.getExceptionByErrorMessage(ERROR_MESSAGE.TOKEN_MANAGER_NOT_INIT);
            } catch (WinnerException e) {
                e.printStackTrace();
            }
        }
        String expire = (String) Util.getPreference(mContext, TOKEN_EXP_KEY, "S");
        if (expire == null || "".equals(expire)) {
            expire = "0";
        }
        return expire;
    }

    /**
     * 获取新的Token值
     *
     * @param callback 获取结果的回调
     */
    public static void getToken(final IDataCallBack<RespToken> callback) {
        if (null == mUrl || TextUtils.isEmpty(mUrl)) {
            try {
                throw WinnerException.getExceptionByErrorMessage(ERROR_MESSAGE.URL_EMPTY);
            } catch (WinnerException e) {
                e.printStackTrace();
            }
        }
        Map<String, String> params = new HashMap<>();
        params.put("appid", "zky");
        params.put("action", "getToken");
        params.put("appsecret", "zky winner trafficanalytic system is very powerful and rich");

        try {
            Request request = BaseBuilder.urlPostJSON(mUrl, params).build();
            BaseCall.doAsync(request, new IHttpCallBack() {
                @Override
                public void onFailure(int errorCode, String message) {
                    callback.onError(errorCode, message);
                }

                @Override
                public void onResponse(Response response) {
                    if (response.isSuccessful()) {
                        Type listType = new TypeToken<RespToken>() {
                        }.getType();
                        Gson gson = new Gson();
                        try {
                            RespToken respToken = gson.fromJson(response.body().string(), listType);
                            //保存Token值和过期时间
                            setAccessToken(respToken.getToken(), respToken.getExp());
                            //结果回调
                            callback.onSuccess(respToken);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        callback.onError(response.code(), response.message());
                    }
                }
            });

        } catch (WinnerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存新的Token值和过期时间
     *
     * @param token
     * @param exp
     */
    private static void setAccessToken(String token, String exp) {
        Util.setPreference(mContext, TOKEN_KEY, token, "S");
        Util.setPreference(mContext, TOKEN_EXP_KEY, exp, "S");
    }

    /**
     * 获取Token值
     *
     * @return token值
     */
    public static String getAccessToken() {
        return (String) Util.getPreference(mContext, TOKEN_KEY, "S");
    }

}
