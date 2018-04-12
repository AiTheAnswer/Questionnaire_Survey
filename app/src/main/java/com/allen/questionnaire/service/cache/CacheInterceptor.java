package com.allen.questionnaire.service.cache;

import android.content.Context;


import com.allen.questionnaire.service.cache.db.bean.CacheEntity;
import com.allen.questionnaire.service.httputil.util.Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Renjy on 2018/3/12.
 * 应用拦截器
 * 作用： 拦截请求，添加自定义的数据库缓存
 */

public class CacheInterceptor implements Interceptor {
    private CacheDbManager dbManager;
    //保存要缓存接口的名称和缓存时间
    private Map<String, Long> mCacheApis;

    public CacheInterceptor(Context context) {
        init(context);
    }

    private void init(Context context) {
        if (null == dbManager) {
            synchronized (CacheInterceptor.class) {
                dbManager = CacheDbManager.getInstance(context);
            }
        }

    }

    /**
     * 添加缓存的action
     *
     * @param host
     * @param params
     */
    public void addCacheApi(String host, Map params, long cacheTime) {
        if (null == mCacheApis) {
            mCacheApis = new HashMap<>();
        }
        HashMap<String, String> hashParams = new HashMap<>();
        hashParams.putAll(params);
        String httpParams = Util.ConvertMap2HttpParams(params);
        StringBuffer buffer = new StringBuffer(host).append(httpParams);
        //http://192.168.2.33:8066/kl/api/v3action=getMenuList&fcimei=A0000059FF25FC&fcuid=dd65768b-935f-464c-b4f1-d1421e408c0d&from=android&lang=zh_CN&menuId=60022&token=8bfc7a2afda7861e8b7a9277734f8712&uid=dd65768b-935f-464c-b4f1-d1421e408c0d
        String md5Params = Util.MD5Encryption(buffer.toString());
        mCacheApis.put(md5Params, cacheTime);
    }

    /**
     * 移除缓存的action
     *
     * @param request
     */
    public void removeCacheApi(Request request) {
        if (null != mCacheApis) {
            String url = request.url().toString();
            String params = Util.getParams(request, false);
            StringBuffer buffer = new StringBuffer(url).append(params);
            boolean containsKey = mCacheApis.containsKey(buffer);
            if (containsKey) {
                mCacheApis.remove(buffer);
            }
        }
    }

    /**
     * 当前请求是否有缓存
     *
     * @param request 请求体
     * @return true 缓存  false 不缓存
     */
    private boolean isCache(Request request) {
        boolean contains = false;
        if (mCacheApis != null) {
            String url = request.url().toString();
            String params = Util.getParams(request, false);
            String[] splits = params.split("&");
            HashMap<String, String> hashParams = new HashMap<>();
            for (int i = 0; i < splits.length; i++) {
                String[] split = splits[i].split("=");
                hashParams.put(split[0], split[1]);
            }

            StringBuffer buffer = new StringBuffer(url).append(Util.ConvertMap2HttpParams(hashParams));
            String md5Params = Util.MD5Encryption(buffer.toString());
            contains = mCacheApis.containsKey(md5Params);
        }
        return contains;
    }

    /**
     * 获取该请求的缓存时间
     *
     * @param request
     * @return
     */
    private long getCacheTime(Request request) {
        long cacheTime = -1;
        if (isCache(request)) {
            String url = request.url().toString();
            String params = Util.getParams(request, false);
            String[] splits = params.split("&");
            HashMap<String, String> hashParams = new HashMap<>();
            for (int i = 0; i < splits.length; i++) {
                String[] split = splits[i].split("=");
                hashParams.put(split[0], split[1]);
            }
            StringBuffer buffer = new StringBuffer(url).append(Util.ConvertMap2HttpParams(hashParams));
            String md5Params = Util.MD5Encryption(buffer.toString());
            cacheTime = mCacheApis.get(md5Params);
        }
        return cacheTime;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        if (request.method().equals("POST")) {
            if (isCache(request)) {
                long cacheTime = getCacheTime(request);
                CacheEntity cacheEntity = dbManager.getCache(request, cacheTime);
                if (null != cacheEntity && cacheEntity.getId() > -1) {
                    response = setResponse(request, cacheEntity.getResult());
                } else {
                    response = chain.proceed(request);
                    String result = response.body().string();
                    response = setResponse(request, result);
                    dbManager.setCache(request, result);
                }
                removeCacheApi(request);
            } else {
                response = chain.proceed(request);
            }
        } else {
            response = chain.proceed(request);
        }
        return response;
    }

    /**
     * 通过数据库中取出的数据，构建response
     *
     * @param request 请求体
     * @param result  数据库中缓存的结果
     * @return 新建的Response
     */
    private Response setResponse(Request request, String result) {
        return new Response.Builder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, only-if-cached")
                .body(ResponseBody.create(MediaType.parse("application/json")
                        , result))
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .build();
    }


}
