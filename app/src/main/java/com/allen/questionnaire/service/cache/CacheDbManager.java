package com.allen.questionnaire.service.cache;

import android.content.Context;


import com.allen.questionnaire.service.cache.db.bean.CacheEntity;
import com.allen.questionnaire.service.cache.db.utils.CacheUtil;
import com.allen.questionnaire.service.httputil.util.Util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import okhttp3.Request;
import okio.Buffer;

/**
 * Created by Renjy on 2018/3/13.
 * 缓存的管理类
 */

public class CacheDbManager {
    private Context mContext;
    private static volatile CacheDbManager dbManager;

    private CacheDbManager(Context context) {
        mContext = context;
    }

    public static CacheDbManager getInstance(Context context) {
        if (null == dbManager) {
            synchronized (CacheDbManager.class) {
                dbManager = new CacheDbManager(context);
            }
        }
        return dbManager;
    }

    /**
     * 获取缓存
     *
     * @param request
     * @return
     * @throws IOException
     */
    public CacheEntity getCache(Request request, long cacheTime) throws IOException {

        String url = request.url().toString(); //获取请求URL
        String params = Util.getParams(request, true);
        CacheUtil cacheUtil = CacheUtil.getInstance(mContext);
        CacheEntity cacheDate = cacheUtil.getCacheData(url, params, cacheTime);
        return cacheDate;
    }

    /**
     * 获取缓存的结果
     *
     * @param host      url地址
     * @param params    参数集合
     * @param cacheTime 缓存时长
     * @return 缓存的数据
     */
    public CacheEntity getCache(String host, Map<String, String> params, long cacheTime) {
        CacheUtil cacheUtil = CacheUtil.getInstance(mContext);
        String md5Params = Util.MD5Encryption(params);
        CacheEntity cacheDate = cacheUtil.getCacheData(host, md5Params, cacheTime);
        return cacheDate;
    }

    /**
     * 设置缓存
     *
     * @param request 请求体
     * @param result  请求结果
     */
    public void setCache(Request request, String result) {
        String url = request.url().toString(); //获取请求URL
        String params = Util.getParams(request, true);
        String action = getApiAction(request);
        String createTime = new StringBuilder().append(System.currentTimeMillis() / 1000).toString();
        CacheUtil instance = CacheUtil.getInstance(mContext);
        CacheEntity menuList = new CacheEntity(url, action, params, result, createTime);
        instance.addEntity(menuList);

    }

    /**
     * 添加缓存
     *
     * @param host        地址
     * @param params      参数集合
     * @param cacheResult 需要缓存的结果
     */
    public void addCache(String host, Map<String, String> params, String cacheResult) {
        String md5Params = Util.MD5Encryption(params);
        String action = params.get("action");
        String createTime = new StringBuilder().append(System.currentTimeMillis() / 1000).toString();
        CacheUtil instance = CacheUtil.getInstance(mContext);
        CacheEntity menuList = new CacheEntity(host, action, md5Params, cacheResult, createTime);
        instance.addEntity(menuList);
    }

    /**
     * 获取接口的action,通过action来判断是那个接口，是否有缓存
     *
     * @param request 请求体
     * @return 接口的action，也就是接口名称
     */
    public String getApiAction(Request request) {
        String params = "";
        Buffer buffer = new Buffer();
        try {
            request.body().writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        params = buffer.readString(Charset.forName("UTF-8")); //获取请求参数

        int indexOf = params.indexOf("action=");
        int i = params.indexOf("&", indexOf + 7);
        String action = "";
        if (i != -1) {
            action = params.substring(indexOf + 7, i);
        } else {
            action = params.substring(indexOf + 7);
        }
        return action;
    }


}
