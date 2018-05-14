package com.allen.questionnaire.service;

import android.content.Context;

import com.allen.questionnaire.service.datatrasfer.IDataCallBack;
import com.allen.questionnaire.service.model.RespCategoryList;
import com.allen.questionnaire.service.model.RespQueDetailObject;
import com.allen.questionnaire.service.model.RespQuestionnaireList;
import com.allen.questionnaire.service.model.RespStudent;
import com.allen.questionnaire.service.net.CommonRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * 接口工具类
 *
 * @author Renjy
 */
public class ApiManager {

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
        CommonRequest.basePostRequest(context, url, 0, tag, specificParams, callback, new CommonRequest.IRequestCallBack<RespStudent>() {
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
        CommonRequest.basePostRequest(context, url, 0, tag, specificParams, callback, new CommonRequest.IRequestCallBack<RespCategoryList>() {
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
        CommonRequest.basePostRequest(context, url, 0, tag, specificParams, callback, new CommonRequest.IRequestCallBack<RespQuestionnaireList>() {
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
    public static void getQuestionAddOptions(Context context, String url, Object tag, Map<String, String> specificParams, final IDataCallBack<RespQueDetailObject> callback) {
        CommonRequest.basePostRequest(context, url, 0, tag, specificParams, callback, new CommonRequest.IRequestCallBack<RespQueDetailObject>() {
            @Override
            public RespQueDetailObject success(String responseStr)  {
                return parseObject(responseStr,RespQueDetailObject.class);
            }
        });
    }

    public static void addQuestionRecord(){

    }

}
