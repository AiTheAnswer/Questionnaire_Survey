package com.allen.questionnaire.service.cache.db.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.allen.questionnaire.service.cache.db.CacheEntityDao;
import com.allen.questionnaire.service.cache.db.DaoMaster;
import com.allen.questionnaire.service.cache.db.DaoSession;
import com.allen.questionnaire.service.cache.db.bean.CacheEntity;

import java.util.List;

/**
 * Created by renji on 2018/3/13.
 * 操作缓存数据库的工具类
 */

public class CacheUtil {
    private static volatile CacheUtil menuListUtil;
    private CacheEntityDao cacheEntityDao;

    public static CacheUtil getInstance(Context context) {
        if (null == menuListUtil) {
            menuListUtil = new CacheUtil(context);
        }
        return menuListUtil;
    }

    private CacheUtil(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "cache.db");
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        cacheEntityDao = daoSession.getCacheEntityDao();
    }

    /**
     * 获取该接口的缓存数据
     *
     * @param host      url地址
     * @param params    参数拼接后用MD5转换后
     * @param cacheTime 缓存时长
     * @return 查询的结果
     */
    public CacheEntity getCacheData(String host, String params, long cacheTime) {
        CacheEntity cacheEntity = null;
        List<CacheEntity> cacheEntities = cacheEntityDao.queryBuilder().where(CacheEntityDao.Properties.Host.eq(host), CacheEntityDao.Properties.Params.eq(params)
                , CacheEntityDao.Properties.CreateTime.ge(System.currentTimeMillis() / 1000 - cacheTime))
                .list();
        if (null != cacheEntities && cacheEntities.size() > 0) {
            cacheEntity = cacheEntities.get(cacheEntities.size() - 1);
        }
        return cacheEntity;

    }

    /**
     * 新增缓存数据
     *
     * @param cacheEntity 数据实体
     */
    public void addEntity(CacheEntity cacheEntity) {
        List<CacheEntity> cacheEntities = cacheEntityDao.queryBuilder().where(CacheEntityDao.Properties.Host.eq(cacheEntity.getHost())
                , CacheEntityDao.Properties.Params.eq(cacheEntity.getParams()))
                .list();
        if (null != cacheEntities && cacheEntities.size() > 0) {
            for (CacheEntity entity : cacheEntities) {
                cacheEntityDao.delete(entity);
            }
        }
        cacheEntityDao.insert(cacheEntity);
    }
}
